package Global.SrcEconomie.Entreprises;

import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Vie.Habitant;

import java.util.List;

public class Poste {
    double salaire;
    double importance;
    List<Connaissance> connaissancesRequises;
    Habitant occupant;
    Entreprise entreprise;
    HoraireTravail horaires;

    public Poste(double salaire, double importance, List<Connaissance> connaissancesRequises, Habitant occupant, Entreprise entreprise, HoraireTravail horaires) {
        this.salaire = salaire;
        this.importance = importance;
        this.connaissancesRequises = connaissancesRequises;
        this.occupant = occupant;
        this.entreprise = entreprise;
        this.horaires = horaires;
    }

    public Poste(double salaire, double importance, List<Connaissance> connaissancesRequises, Habitant occupant, HoraireTravail horaires) {
        this.salaire = salaire;
        this.importance = importance;
        this.connaissancesRequises = connaissancesRequises;
        this.occupant = occupant;
        this.horaires = horaires;
    }

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
            occupant = hab;
        }
    }
    public void renvoyer()
    {
        if(occupant !=null)
        {
            occupant.setPoste(null);
            occupant = null;
        }
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public HoraireTravail getHoraires() {
        return horaires;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}
