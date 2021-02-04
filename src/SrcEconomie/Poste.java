package SrcEconomie;

import java.util.List;

public class Poste {
    double salaire;
    double importance;
    List<Connaissance> connaissancesRequises;
    Habitant occupant;

    public boolean estApte(Habitant hab)
    {
        return Connaissance.estApte(hab,connaissancesRequises);
    }
    public double importanceFournie()
    {
        if(occupant != null)
        {
            return importance;
        }
        else
        {
            return 0;
        }
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public double getImportance() {
        return importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;
    }

    public List<Connaissance> getConnaissancesRequises() {
        return connaissancesRequises;
    }

    public void setConnaissancesRequises(List<Connaissance> connaissancesRequises) {
        this.connaissancesRequises = connaissancesRequises;
    }

    public Habitant getOccupant() {
        return occupant;
    }

    public void setOccupant(Habitant occupant) {
        this.occupant = occupant;
    }
}
