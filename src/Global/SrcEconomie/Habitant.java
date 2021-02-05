package Global.SrcEconomie;

import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Universite;
import Global.SrcVirus.Individu;

import java.util.List;

public class Habitant extends Individu {
    String prenom;
    String nom;

    Residence residence;
    Entreprise travail;
    Universite universite;
    List<Connaissance> connaissances;
    List<Marchandise> inventaire;
    Poste poste;
    CompteBancaire compteBancaire;

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
}
