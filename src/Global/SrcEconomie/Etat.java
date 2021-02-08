package Global.SrcEconomie;

import Global.Monde;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;

import java.util.List;
import java.util.function.Function;

public class Etat implements Monetaire,JourListener{
    Function<Double,Double> taxeVente;
    Function<Double,Double> taxeEpargne;
    double PIB;
    double budgetUniversites;
    CompteBancaire finances;

    public void incrementerPIB(double valeur)
    {
        PIB += valeur;
    }

    public void taxerComptes()
    {
        List<Monetaire> imposables = Monde.getImposables();
        for(Monetaire m : imposables)
        {
            CompteBancaire cible = m.getCompteBancaire();
            if(cible.getSomme()>0) {
                double montant = cible.getSomme()*getTaxeEpargne().apply(cible.getSomme());
                finances.prelever(cible,montant , "Impot Epargne");
            }
        }
    }

    @Override
    public CompteBancaire getCompteBancaire() {
        return finances;
    }



    public void financerUniversites()
    {
        List<Universite> univs = Monde.getUniversites();
        for(Universite u : univs)
        {
            CompteBancaire cptUniv =  u.getCompteBancaire();
            finances.payer(cptUniv,budgetUniversites,"Financement universite "+u.getNom());
        }
    }

    public double getPIB() {
        return PIB;
    }

    public CompteBancaire getFinances() {
        return finances;
    }


    //TODO allocations
    @Override
    public void jourPasse(double dt) {
        getTaxeEpargne();
        getTaxeVente();
        financerUniversites();
    }

    public Function<Double, Double> getTaxeVente() {
        return taxeVente;
    }

    public Function<Double, Double> getTaxeEpargne() {
        return taxeEpargne;
    }

    public double getBudgetUniversites() {
        return budgetUniversites;
    }
}
