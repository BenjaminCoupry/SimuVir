package Global.SrcEconomie.Vie;

import Global.Monde;
import Global.SrcEconomie.*;
import Global.SrcEconomie.Entreprises.*;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Finance.Banque;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcVirus.Fonctions;
import Global.SrcVirus.Individu;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Habitant extends Individu implements Monetaire, JourListener,DtListener {
    String prenom;
    String nomFamille;
    Residence residence;
    Universite universite;
    Banque banque;
    List<Connaissance> connaissances;
    List<Marchandise> inventaire;
    List<Marchandise> inventaireEquipe;
    Poste poste;
    CompteBancaire compteBancaire;
    LieuPhysique position;
    LieuPhysique objectif;
    TypeMarchandise volonteAchat;
    ModeActivite modeActiviteReel;
    ModeActivite modeActiviteVoulu;
    double avancementLieu;
    BesoinsHabitant besoins;

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return getPrenom()+" "+getNomFamille();
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

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public List<Marchandise> getInventaire() {
        return inventaire;
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


    public ModeActivite choisirComportement()
    {
        //Besoin vital
        if(besoins.affame())
        {

            if (peutEquiper(FamillesMarchandises.ALIMENTAIRE) || estEquipe(FamillesMarchandises.ALIMENTAIRE)) {
                return ModeActivite.MANGER;
            }
            else
            {
                if (volonteAchat == null || !(volonteAchat.getFamilles().contains(FamillesMarchandises.ALIMENTAIRE))) {
                    List<TypeMarchandise> nourriture = TypeMarchandise.getParFamille(FamillesMarchandises.ALIMENTAIRE);
                    TypeMarchandise tm = nourriture.get(Fonctions.r.nextInt(nourriture.size()));
                    volonteAchat = tm;
                }
                return ModeActivite.ACHETER;
            }
        }
        if(besoins.fatigue())
        {
            return ModeActivite.REPOS;
        }
        if(besoins.triste())
        {
            return ModeActivite.VISITER;
        }

        //besoins vitaux satisfaits

        if(getPoste()!= null && getPoste().getHoraires().doitTravailler())
        {
            return ModeActivite.TRAVAILLER;
        }else
        {
            return ModeActivite.ETUDIER;
        }
    }
    public void comportement()
    {
        switch (modeActiviteVoulu)
        {
            case REPOS:
                rentrerDomicile();
                break;
            case MANGER:
                manger();
                break;
            case ATTENDRE:
                attendre();
                break;
            case VISITER:
                partirVisiter();
                break;
            case TRAVAILLER:
                partirTravailler();
                break;
            case ETUDIER:
                partirEtudier();
                break;
            case ACHETER:
                partirAcheter();
                break;
        }
    }

    public void partirTravailler()
    {

        Entreprise travail = getTravail();
        if(travail != null && getPoste().getHoraires().doitTravailler()) {
            modeActiviteReel = ModeActivite.TRAVAILLER;
            if(travail instanceof EntrepriseTransport)
            {
                objectif = ((EntrepriseTransport) travail).getObjectif(this);
            }
            else {
                objectif = travail;
            }
        }else
        {
            attendre();
        }

    }
    public void rentrerDomicile()
    {
        if(residence != null) {
            modeActiviteReel = ModeActivite.REPOS;
            objectif = residence;
        }else
        {
            attendre();
        }
    }
    public void partirEtudier()
    {
        if(universite != null) {
            modeActiviteReel = ModeActivite.ETUDIER;
            objectif = universite;
        }else
        {
            attendre();
        }
    }
    public void partirAcheter()
    {

        if(volonteAchat != null)
        {
            modeActiviteReel = ModeActivite.ACHETER;
            List<Stockage> dispo = Monde.trouverDisponibilites(volonteAchat, TypeDisponibilite.MAGASIN);
            if(dispo.contains(position))
            {
                //Dans le magasin
                objectif = position;
                Boutique bt = (Boutique) position;
                bt.vendre(volonteAchat,this);
                volonteAchat = null;
            }
            else if(dispo.size()>0)
            {
                //Il existe un magasin
                if(!dispo.contains(objectif)) {
                    //trouver la destination
                    Boutique bt = (Boutique) dispo.get(Fonctions.r.nextInt(dispo.size()));
                    objectif = bt;
                }
            }
            else
            {
                //Pas de magasin
                attendre();
                volonteAchat = null;
            }
        }
        else
        {
            partirVisiter();
        }
    }
    public void partirVisiter()
    {
        List<LieuPhysique> lieuxPhysiques = Monde.getLieuxPhysiques();
        LieuPhysique lp = lieuxPhysiques.get(Fonctions.r.nextInt(lieuxPhysiques.size()));
        objectif = lp;
        modeActiviteReel = ModeActivite.VISITER;
    }
    public void attendre()
    {
        objectif = position;
        modeActiviteReel = ModeActivite.ATTENDRE;
    }
    public void manger()
    {
        if(!estEquipe(FamillesMarchandises.ALIMENTAIRE)) {
            equiper(FamillesMarchandises.ALIMENTAIRE);
        }
        if(estEquipe(FamillesMarchandises.ALIMENTAIRE)) {
            if (residence != null) {
                modeActiviteReel = ModeActivite.MANGER;
                objectif = residence;
            } else {
                modeActiviteReel = ModeActivite.MANGER;
                objectif = position;
            }
        }
        else
        {
            attendre();
        }
    }

    public void equiper(TypeMarchandise tm)
    {
        List<Marchandise> possib = inventaire.stream().filter(m->m.getTypeMarchandise().equals(tm)).collect(Collectors.toList());
        if(possib.size()>0) {
            Marchandise choix = possib.get(Fonctions.r.nextInt(possib.size()));
            inventaire.remove(choix);
            inventaireEquipe.add(choix);
        }
    }
    public void equiper(FamillesMarchandises fm)
    {
        List<Marchandise> possib = inventaire.stream().filter(m->m.getTypeMarchandise().getFamilles().contains(fm))
                .collect(Collectors.toList());
        if(possib.size()>0) {
            Marchandise choix = possib.get(Fonctions.r.nextInt(possib.size()));
            inventaire.remove(choix);
            inventaireEquipe.add(choix);
        }
    }
    public boolean estEquipe(TypeMarchandise tm)
    {
        long possib = inventaireEquipe.stream().filter(m->m.getTypeMarchandise().equals(tm)).collect(Collectors.counting());
        return possib >0;
    }
    public boolean estEquipe(FamillesMarchandises fm)
    {
        long possib = inventaireEquipe.stream().filter(m->m.getTypeMarchandise().getFamilles().contains(fm))
                .collect(Collectors.counting());
        return possib >0;
    }
    public boolean peutEquiper(TypeMarchandise tm)
    {
        long possib = inventaire.stream().filter(m->m.getTypeMarchandise().equals(tm)).collect(Collectors.counting());
        return possib >0;
    }
    public boolean peutEquiper(FamillesMarchandises fm)
    {
        long possib = inventaire.stream().filter(m->m.getTypeMarchandise().getFamilles().contains(fm))
                .collect(Collectors.counting());
        return possib >0;
    }
    public void deEquiper(TypeMarchandise tm)
    {
        List<Marchandise> possib = inventaireEquipe.stream().filter(m->m.getTypeMarchandise().equals(tm)).collect(Collectors.toList());
        Marchandise choix = possib.get(Fonctions.r.nextInt(possib.size()));
        inventaireEquipe.remove(choix);
        inventaire.add(choix);
    }



    @Override
    public void Update(double dt)
    {
        super.Update(dt);
        modeActiviteVoulu = choisirComportement();
        comportement();
        userEquipementPorte(dt);
        deplacer(dt);
        besoins.update(dt,modeActiviteReel);
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
        if(banque==null)
        {
            List<Banque> res = Monde.trouverBanquesPossibles(this);
            Banque chosie = res.get(Fonctions.r.nextInt(res.size()));
            chosie.sInscrire(this);
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
        if(banque != null)
        {
            banque.oublier(this);
        }
    }

    @Override
    public void jourPasse(double dt) {
        trouverAffectations();
    }

    public String getNomFamille() {
        return nomFamille;
    }
}


//TODO Entreprises de services
