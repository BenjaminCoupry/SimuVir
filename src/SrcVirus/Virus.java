package SrcVirus;

import java.util.function.Function;

public class Virus {
    String nom;
    String famille;
    Function<Double,Double> lethalite;
    double mutabilite;
    double contagiosite;
    Function<Double,Double> gainImmunite;
    Function<Double,Double> perteImmunite;
    double fragilite;
    double reproduction;

    public Virus(String nom, String famille, Function<Double, Double> lethalite, double mutabilite, double contagiosite, Function<Double, Double> gainImmunite, Function<Double, Double> perteImmunite, double fragilite, double reproduction) {
        this.nom = nom;
        this.famille = famille;
        this.lethalite = lethalite;
        this.mutabilite = mutabilite;
        this.contagiosite = contagiosite;
        this.gainImmunite = gainImmunite;
        this.perteImmunite = perteImmunite;
        this.fragilite = fragilite;
        this.reproduction = reproduction;
    }

    public double getReproduction() {
        return reproduction;
    }

    public void setReproduction(double reproduction) {
        this.reproduction = reproduction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Function<Double,Double> getLethalite() {
        return lethalite;
    }

    public void setLethalite(Function<Double,Double> lethalite) {
        this.lethalite = lethalite;
    }

    public double getMutabilite() {
        return mutabilite;
    }

    public void setMutabilite(double mutabilite) {
        this.mutabilite = mutabilite;
    }

    public double getContagiosite() {
        return contagiosite;
    }

    public void setContagiosite(double contagiosite) {
        this.contagiosite = contagiosite;
    }

    public Function<Double,Double> getGainImmunite() {
        return gainImmunite;
    }

    public void setGainImmunite(Function<Double,Double> gainImmunite) {
        this.gainImmunite = gainImmunite;
    }

    public Function<Double,Double> getPerteImmunite() {
        return perteImmunite;
    }

    public void setPerteImmunite(Function<Double,Double> perteImmunite) {
        this.perteImmunite = perteImmunite;
    }

    public double getFragilite() {
        return fragilite;
    }

    public void setFragilite(double fragilite) {
        this.fragilite = fragilite;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }
    public Virus muter()
    {
        //TODO mutation
        if(Fonctions.r.nextDouble()<mutabilite)
        {
            String nouvNom = nom+"_mutation"+Fonctions.getUID();
            return new Virus(nouvNom,famille,lethalite,mutabilite,contagiosite,gainImmunite,perteImmunite,fragilite,reproduction);
        }
        else
        {
            return this;
        }
    }
}
