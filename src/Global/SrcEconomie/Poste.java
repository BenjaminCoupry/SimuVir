package Global.SrcEconomie;

import java.util.List;

public class Poste {
    double salaire;
    double importance;
    List<Connaissance> connaissancesRequises;
    Habitant occupant;
    Entreprise entreprise;

    public boolean peutPostuler(Habitant hab)
    {
        return occupant == null && Connaissance.estApte(hab,connaissancesRequises);
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

    public void recruter(Habitant hab)
    {
        if(peutPostuler(hab)) {
            hab.setPoste(this);
            hab.setTravail(entreprise);
            occupant = hab;
            if(! entreprise.getEmployes().contains(hab))
            {
                entreprise.getEmployes().add(hab);
            }
        }
    }
}