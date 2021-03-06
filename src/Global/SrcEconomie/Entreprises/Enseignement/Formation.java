package Global.SrcEconomie.Entreprises.Enseignement;

import Global.SrcEconomie.Vie.Habitant;

import java.util.List;

public class Formation {
    List<Connaissance> connaissancesFournies;
    List<Connaissance> connaissancesRequises;
    double tempsObtention;

    public Formation(List<Connaissance> connaissancesFournies, List<Connaissance> connaissancesRequises, double tempsObtention) {
        this.connaissancesFournies = connaissancesFournies;
        this.connaissancesRequises = connaissancesRequises;
        this.tempsObtention = tempsObtention;
    }

    public void donner(Habitant hab)
    {
        for(Connaissance c: connaissancesFournies)
        {
            hab.apprendre(c);
        }
    }
    public boolean estApte(Habitant hab)
    {
        return Connaissance.estApte(hab,connaissancesRequises);
    }

    public List<Connaissance> getConnaissancesFournies() {
        return connaissancesFournies;
    }

    public void setConnaissancesFournies(List<Connaissance> connaissancesFournies) {
        this.connaissancesFournies = connaissancesFournies;
    }

    public List<Connaissance> getConnaissancesRequises() {
        return connaissancesRequises;
    }

    public void setConnaissancesRequises(List<Connaissance> connaissancesRequises) {
        this.connaissancesRequises = connaissancesRequises;
    }

    public double getTempsObtention() {
        return tempsObtention;
    }

    public void setTempsObtention(double tempsObtention) {
        this.tempsObtention = tempsObtention;
    }
}
