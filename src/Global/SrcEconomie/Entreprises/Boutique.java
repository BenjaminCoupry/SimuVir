package Global.SrcEconomie.Entreprises;

import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Habitant;
import Global.SrcEconomie.TypeMarchandise;

import java.util.List;

public class Boutique extends Entreprise{
    List<Marchandise> stock;
    List<TypeMarchandise> catalogue;
    //TODO commandes aupres des usines pour remplir le catalogue
    //TODO pour les livraisons, garder un hashmap pour les livreurs indiquant la marchandise transportee, le lieu de depart et d'arrivee
    public boolean peutVendre(TypeMarchandise tm)
    {
        for(Marchandise m : stock)
        {
            if(m.getTypeMarchandise().equals(tm))
            {
                return true;
            }
        }
        return false;
    }
    public double getPrix(TypeMarchandise tm)
    {
        double marge = 1+ (ConstantesEco.margeMax-1)*getEfficacite();
        return tm.getPrixFournisseur()*marge;
    }
    public void vendre(TypeMarchandise tm, Habitant hab)
    {
        if(peutVendre(tm))
        {
            double prix = getPrix(tm);
            compteBancaire.prelever(hab.getCompteBancaire(),prix,"Achat "+tm.getName() + " "+nom);
            Marchandise achat=null;
            for(Marchandise m : stock)
            {
                if(m.getTypeMarchandise().equals(tm))
                {
                    achat = m;
                    break;
                }
            }
            stock.remove(achat);
            hab.getInventaire().add(achat);
        }
    }
}
