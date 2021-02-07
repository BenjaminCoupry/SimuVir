package Global.SrcEconomie;

import Global.Monde;

import java.util.List;

public class Etat implements Monetaire{
    double taxeVente;
    double taxeEpargne;
    double PIB;
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
                double montant = cible.getSomme()*getTaxeEpargne();
                finances.prelever(cible,montant , "Impot Epargne");
            }
        }
    }

    @Override
    public CompteBancaire getCompteBancaire() {
        return finances;
    }


    public double getTaxeVente() {
        return taxeVente;
    }

    public double getTaxeEpargne() {
        return taxeEpargne;
    }

    public double getPIB() {
        return PIB;
    }

    public CompteBancaire getFinances() {
        return finances;
    }
}
