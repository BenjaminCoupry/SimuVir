package Global.SrcEconomie.Entreprises.Industrie;

import Global.Monde;
import Global.SrcEconomie.DtListener;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.JourListener;
import Global.SrcEconomie.Entreprises.TypeMarchandise;
import Global.SrcVirus.Fonctions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Usine extends Entreprise implements Stockage, DtListener, JourListener {
    List<Marchandise> entree;
    List<Marchandise> sortie;
    RecetteIndustrie recette;
    double tempsRestantAvantCompletion;
    boolean enFabrication;

    public Usine(Hitbox hitbox, double tempsTraversee, double x, double y, RecetteIndustrie recette) {
        super(hitbox, tempsTraversee, x, y);
        entree = new LinkedList<>();
        sortie = new LinkedList<>();
        this.recette = recette;
        tempsRestantAvantCompletion = recette.getTempsFabrication();
        enFabrication=false;

    }

    public List<TypeMarchandise> trouverElementsManquants()
    {
        List<UsageMarchandise> usages = recette.getConsommation();
        List<TypeMarchandise> necessaires = new ArrayList<>();
        for(UsageMarchandise um : usages)
        {
            int restant = um.getNbUsage();
            List<Marchandise> mt = entree.stream()
                    .filter(march -> march.getTypeMarchandise().equals(um.getTypeMarchandise()))
                    .collect(Collectors.toList());
            for(int i=0;i<restant;i++)
            {
                if(mt.size()>0)
                {
                    mt.remove(0);
                    restant -=1;
                }
                else
                {
                    for(int j=0;j<restant;j++)
                    {
                        necessaires.add(um.getTypeMarchandise());
                    }
                    break;
                }
            }
        }
        return necessaires;
    }

    public void consommerRecette()
    {
        List<UsageMarchandise> usages = recette.getConsommation();
        for(UsageMarchandise um : usages)
        {
            int restant = um.getNbUsage();
            List<Marchandise> mt = entree.stream()
                    .filter(march -> march.getTypeMarchandise().equals(um.getTypeMarchandise()))
                    .collect(Collectors.toList());
            for(int i=0;i<restant;i++)
            {
                entree.remove(mt.get(0));
                mt.remove(0);
            }
        }
    }

    public void lancerProductionSiPossible()
    {
        if((! enFabrication) && trouverElementsManquants().size() ==0)
        {
            enFabrication = true;
            consommerRecette();
            tempsRestantAvantCompletion = recette.getTempsFabrication();
        }
    }

    public void finaliserProduction()
    {
        enFabrication = false;
        for(UsageMarchandise um : recette.getProduction())
        {
            int restant = um.getNbUsage();
            for(int i=0;i<restant;i++)
            {
                sortie.add(new Marchandise(um.getTypeMarchandise()));
            }
        }
    }

    public void Update(double dt)
    {
        super.Update(dt);
        lancerProductionSiPossible();
        tempsRestantAvantCompletion -= dt*getEfficacite();
        if(enFabrication && tempsRestantAvantCompletion <0)
        {
            finaliserProduction();
        }
    }

    @Override
    public Marchandise fournir(TypeMarchandise tm) {
        List<Marchandise> mt = sortie.stream()
                .filter(march -> march.getTypeMarchandise().equals(tm))
                .collect(Collectors.toList());
        if(mt.size()>0)
        {
            Marchandise select = mt.get(0);
            sortie.remove(select);
            return select;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void stocker(Marchandise m) {
        entree.add(m);
    }

    @Override
    public boolean disponible(TypeMarchandise tm) {
        List<Marchandise> mt = sortie.stream()
                .filter(march -> march.getTypeMarchandise().equals(tm))
                .collect(Collectors.toList());
        return mt.size()>0;
    }

    @Override
    public double getPrix(TypeMarchandise tm) {
        return tm.getPrixFournisseur();
    }

    @Override
    public void passerCommandes()
    {
        if(Monde.getTransporteurs().size() >0) {
            for (UsageMarchandise um : recette.getConsommation()) {
                List<Marchandise> mt = entree.stream()
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
}
