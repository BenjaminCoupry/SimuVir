import java.util.function.Function;

public class Virus {
    String nom;
    Function<Double,Double> lethalite;
    double mutabilite;
    double contagiosite;
    double gainImmunite;
    double perteImmunite;
    double fragilite;
    double reproduction;

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

    public double getGainImmunite() {
        return gainImmunite;
    }

    public void setGainImmunite(double gainImmunite) {
        this.gainImmunite = gainImmunite;
    }

    public double getPerteImmunite() {
        return perteImmunite;
    }

    public void setPerteImmunite(double perteImmunite) {
        this.perteImmunite = perteImmunite;
    }

    public double getFragilite() {
        return fragilite;
    }

    public void setFragilite(double fragilite) {
        this.fragilite = fragilite;
    }
}
