package Global.SrcEconomie;

import Global.Monde;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Finance.CompteBancaire;
import Global.SrcEconomie.Entreprises.Finance.Monetaire;
import Global.SrcEconomie.Vie.Habitant;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Etat implements Monetaire,JourListener{
    Function<Double,Double> taxeVente;
    Function<Double,Double> taxeEpargne;
    Function<Double,Double> allocationsIndividus;
    Function<Double,Double> aidesEntreprises;
    double PIB;
    List<Double> historiquePIB;
    double budgetUniversites;
    CompteBancaire finances;

    public Etat(Function<Double, Double> taxeVente, Function<Double, Double> taxeEpargne, double budgetUniversites,
                Function<Double,Double> allocationsIndividus,
            Function<Double,Double> aidesEntreprises) {
        this.taxeVente = taxeVente;
        this.taxeEpargne = taxeEpargne;
        this.budgetUniversites = budgetUniversites;
        PIB=0;
        historiquePIB=new LinkedList<>();
        finances = new CompteBancaire(0);
        this.allocationsIndividus = allocationsIndividus;
        this.aidesEntreprises = aidesEntreprises;
    }

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

    public void distribuerAllocationsHabitants()
    {
        List<Habitant> eligibles = Monde.getHabitants();
        for(Habitant h : eligibles)
        {
            CompteBancaire cible = h.getCompteBancaire();
            double montant = allocationsIndividus.apply(cible.getSomme());
            finances.payer(cible,montant , "Aides individus Etat");
        }
    }
    public void distribuerAidesEntreprises()
    {
        List<Entreprise> eligibles = Monde.getEntreprises();
        for(Entreprise e : eligibles)
        {
            CompteBancaire cible = e.getCompteBancaire();
            double montant = aidesEntreprises.apply(cible.getSomme());
            finances.payer(cible,montant , "Aides entreprises Etat");
        }
    }
    public void distribuerAides()
    {
        distribuerAidesEntreprises();
        distribuerAllocationsHabitants();
    }

    @Override
    public void jourPasse(double dt) {
        taxerComptes();
        distribuerAides();
        financerUniversites();
        historiquePIB.add(PIB);
        PIB =0;
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
