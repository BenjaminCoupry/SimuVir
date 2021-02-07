package Global.SrcEconomie.Vie;

import Global.Monde;
import Global.SrcEconomie.CompteBancaire;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Marchandises;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.LieuPhysique;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcEconomie.Monetaire;
import Global.SrcEconomie.TypeMarchandise;
import Global.SrcVirus.Fonctions;
import Global.SrcVirus.Individu;
import Global.SrcVirus.Lieu;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Habitant extends Individu implements Monetaire {
    String prenom;
    String nom;
    //TODO user l equipement porté
    Residence residence;
    Universite universite;
    List<Connaissance> connaissances;
    List<Marchandise> inventaire;
    List<Marchandise> inventaireEquipe;
    Poste poste;
    CompteBancaire compteBancaire;
    LieuPhysique position;
    LieuPhysique objectif;
    TypeMarchandise volonteAchat;
    ModeActivite modeActivite;
    double avancementLieu;

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public Entreprise getTravail() {
        if(poste == null)
        {
            return null;
        }else
        {
            return poste.getEntreprise();
        }
    }

    public Universite getUniversite() {
        return universite;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
    }

    public List<Connaissance> getConnaissances() {
        return connaissances;
    }

    public void setConnaissances(List<Connaissance> connaissances) {
        this.connaissances = connaissances;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    public List<Marchandise> getInventaire() {
        return inventaire;
    }

    public void setInventaire(List<Marchandise> inventaire) {
        this.inventaire = inventaire;
    }

    public void deplacer(double dt)
    {
        avancementLieu+= dt;
        if(avancementLieu> position.getTempsTraversee())
        {
            avancementLieu=0;
            if(objectif != null && objectif != position) {
                List<LieuPhysique> next = position.getChemin(objectif);
                if (next.size() > 1) {
                    position = next.get(1);
                }
            }
        }
    }

    //TODO gerer les comportements et maj des ModeActivite en consequence
    public void choisirComportement()
    {
        //TODO trouver les envies, les objectifs, se deplacer etc (fonction de l'heure)
    }
    public void comportement()
    {
        //TODO actions en coséquences du ModeActivite choisi
    }

    public void partirTravailler()
    {
        Entreprise travail = getTravail();
        if(travail != null) {
            if(travail instanceof EntrepriseTransport)
            {
                objectif = ((EntrepriseTransport) travail).getObjectif(this);
            }
            else {
                objectif = travail;
            }
        }else
        {
            objectif = null;
        }

    }
    public void rentrerDomicile()
    {
        if(residence != null) {
            objectif = residence;
        }else
        {
            objectif = null;
        }
    }
    public void partirEtudier()
    {
        if(universite != null) {
            objectif = universite;
        }else
        {
            objectif = null;
        }
    }
    public void partirAcheter()
    {

        if(volonteAchat != null)
        {
            List<Stockage> dispo = Monde.trouverDisponibilites(volonteAchat, TypeDisponibilite.MAGASIN);
            if(dispo.contains(position))
            {
                objectif = position;
                Boutique bt = (Boutique) position;
                bt.vendre(volonteAchat,this);
                volonteAchat = null;
            }
            else if(dispo.size()>0)
            {
                Boutique bt = (Boutique) dispo.get(Fonctions.r.nextInt(dispo.size()));
                objectif = bt;
            }
            else
            {
                objectif = null;
            }
        }
        else
        {
            objectif = null;
        }
    }

    public void equiper(TypeMarchandise tm)
    {
        List<Marchandise> possib = inventaire.stream().filter(m->m.getTypeMarchandise().equals(tm)).collect(Collectors.toList());
        Marchandise choix = possib.get(Fonctions.r.nextInt(possib.size()));
        inventaire.remove(choix);
        inventaireEquipe.add(choix);
    }


    public void Update(double dt)
    {
        trouverAffectations();
        choisirComportement();
        comportement();
        userEquipementPorte(dt);
        deplacer(dt);
    }

    public void userEquipementPorte(double dt)
    {
        List<Marchandise> detruit = new LinkedList<>();
        for(Marchandise m : inventaireEquipe)
        {
            if(!m.user(dt))
            {
                detruit.add(m);
            }
        }
        for(Marchandise m : detruit)
        {
            inventaireEquipe.remove(m);
        }
    }
    public void trouverAffectations()
    {
        if(residence==null)
        {
            List<Residence> res = Monde.trouverResidencesPossibles(this);
            Residence chosie = res.get(Fonctions.r.nextInt(res.size()));
            chosie.emmenager(this);
        }
        if(poste==null || getTravail() == null)
        {
            List<Poste> res = Monde.trouverPostesPossibles(this);
            Poste chosi = res.get(Fonctions.r.nextInt(res.size()));
            chosi.recruter(this);
        }
        if(universite==null)
        {
            List<Universite> res = Monde.trouverUniversitesPossibles(this);
            Universite chosie = res.get(Fonctions.r.nextInt(res.size()));
            chosie.inscrire(this);
        }
    }
    public void supprimer()
    {
        if(poste != null)
        {
            Entreprise e = poste.getEntreprise();
            if(e!=null)
            {
                e.oublier(this);
            }
        }
        if(universite != null)
        {
            universite.oublier(this);
        }
    }
}


//TODO Entreprises de services
