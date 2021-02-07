package Global.SrcEconomie.Entreprises.Commerce;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Industrie.UsageMarchandise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcEconomie.TypeMarchandise;
import Global.SrcVirus.Fonctions;

import java.util.List;
import java.util.stream.Collectors;

public class Boutique extends Entreprise implements Stockage {
    List<Marchandise> stock;
    List<UsageMarchandise> catalogue;
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
    @Override
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
            getCompteBancaire().prelever(hab.getCompteBancaire(),prix,"Achat "+tm.getName() + " "+getNom());
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

    @Override
    public Marchandise fournir(TypeMarchandise tm) {
        List<Marchandise> mt = stock.stream()
                .filter(march -> march.getTypeMarchandise().equals(tm))
                .collect(Collectors.toList());
        if(mt.size()>0)
        {
            Marchandise select = mt.get(0);
            stock.remove(select);
            return select;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void stocker(Marchandise m) {
        stock.add(m);
    }

    @Override
    public boolean disponible(TypeMarchandise tm) {
        List<Marchandise> mt = stock.stream()
                .filter(march -> march.getTypeMarchandise().equals(tm))
                .collect(Collectors.toList());
        return mt.size()>0;
    }
    @Override
    public void passerCommandes()
    {
        if(Monde.getTransporteurs().size() >0) {
            for (UsageMarchandise um : catalogue) {
                List<Marchandise> mt = stock.stream()
                        .filter(march -> march.getTypeMarchandise().equals(um.getTypeMarchandise()))
                        .collect(Collectors.toList());
                int delta = um.getNbUsage() - mt.size();
                for (int i = 0; i < delta; i++) {
                    EntrepriseTransport et = Monde.getTransporteurs().get(Fonctions.r.nextInt(Monde.getTransporteurs().size()));
                    et.passerCommande(this, um.getTypeMarchandise());
                }
            }
        }
    }
    public void supprimer()
    {
        super.supprimer();
        List<EntrepriseTransport> et = Monde.getTransporteurs();
        for(EntrepriseTransport e : et)
        {
            e.oublier(this);
        }
    }
}
