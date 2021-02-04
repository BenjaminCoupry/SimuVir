package SrcEconomie;

import SrcVirus.Individu;

import java.util.List;

public class Habitant extends Individu {
    String prenom;
    String nom;

    Residence residence;
    Entreprise travail;
    Universite universite;
    List<Connaissance> connaissances;
    Poste poste;
    double fondsMonetaires;

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

    public double getFondsMonetaires() {
        return fondsMonetaires;
    }

    public void setFondsMonetaires(double fondsMonetaires) {
        this.fondsMonetaires = fondsMonetaires;
    }
}
