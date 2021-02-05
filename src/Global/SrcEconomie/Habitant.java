package Global.SrcEconomie;

import Global.Monde;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcVirus.Fonctions;
import Global.SrcVirus.Individu;
import Global.SrcVirus.Lieu;

import java.util.List;

public class Habitant extends Individu{
    String prenom;
    String nom;

    Residence residence;
    Entreprise travail;
    Universite universite;
    List<Connaissance> connaissances;
    List<Marchandise> inventaire;
    Poste poste;
    CompteBancaire compteBancaire;
    Lieu position;
    Lieu objectif;
    TypeMarchandise volonteAchat;

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
        return travail;
    }

    public void setTravail(Entreprise travail) {
        this.travail = travail;
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


    public void partirTravailler()
    {
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
}
//TODO trouver poste, universite etc
//TODO trouver les envies, les objectifs, se deplacer etc (fonction de l'heure)