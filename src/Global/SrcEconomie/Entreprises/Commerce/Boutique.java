package Global.SrcEconomie.Entreprises.Commerce;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.DtListener;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Industrie.UsageMarchandise;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.JourListener;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcVirus.Fonctions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Boutique extends Entreprise implements Stockage, DtListener, JourListener {
    List<Marchandise> stock;
    List<UsageMarchandise> catalogue;

    public Boutique(Hitbox hitbox, double tempsTraversee, double x, double y,List<UsageMarchandise> catalogue) {
        super(hitbox, tempsTraversee, x, y);
        stock = new LinkedList<>();
        this.catalogue = catalogue;
    }

    public boolean peutVendre(Marchandise tm)
    {

        for(Marchandise m : stock)
        {
            if(m.accepte(tm))
            {
                return getEfficacite()>0;
            }
        }
        return false;
    }
    @Override
    public double getPrix(Marchandise tm)
    {
        double marge = 1+ (ConstantesEco.margeMax-1)*getEfficacite();
        return tm.getPrixFournisseur()*marge;
    }
    public void vendre(Marchandise tm, Habitant hab)
    {
        if(peutVendre(tm))
        {
            double prix = getPrix(tm);
            getCompteBancaire().prelever(hab.getCompteBancaire(),prix,"Achat "+getNom());
            Marchandise achat=null;
            for(Marchandise m : stock)
            {
                if(m.accepte(tm))
                {
                    achat = m;
                    break;
                }
            }
            stock.remove(achat);
            hab.getInventaire().donner(achat);
        }
    }

    @Override
    public Marchandise fournir(Marchandise tm) {
        List<Marchandise> mt = stock.stream()
                .filter(march -> march.accepte(tm))
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
    public boolean disponible(Marchandise tm) {
        List<Marchandise> mt = stock.stream()
                .filter(march -> march.accepte(tm))
                .collect(Collectors.toList());
        return mt.size()>0;
    }
    @Override
    public void passerCommandes()
    {
        if(Monde.getTransporteurs().size() >0) {
            for (UsageMarchandise um : catalogue) {
                List<Marchandise> mt = stock.stream()
                        .filter(march -> march.accepte(um.getTypeMarchandise()))
                        .collect(Collectors.toList());
                int delta = um.getNbUsage() - mt.size();
                for (int i = 0; i < delta; i++) {
                    EntrepriseTransport et = Monde.getTransporteurs().get(Fonctions.r.nextInt(Monde.getTransporteurs().size()));
                    et.passerCommande(this, um.getTypeMarchandise());
                }
            }
        }
    }


    @Override
    public void jourPasse(double dt)
    {
        super.jourPasse(dt);
        passerCommandes();
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

    public List<UsageMarchandise> getCatalogue() {
        return catalogue;
    }
}
