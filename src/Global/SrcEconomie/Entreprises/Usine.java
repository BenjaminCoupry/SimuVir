package Global.SrcEconomie.Entreprises;

import Global.SrcEconomie.TypeMarchandise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usine extends Entreprise{
    List<Marchandise> entree;
    List<Marchandise> sortie;
    RecetteIndustrie recette;
    double tempsRestantAvantCompletion;
    boolean enFabrication;

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
}
