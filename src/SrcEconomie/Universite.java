package SrcEconomie;

import SrcVirus.Individu;

import java.util.HashMap;

public class Universite extends Entreprise {
    Formation formation;
    HashMap<Habitant,Double> tempsRestantAvantObtention;
    int places;

    public void Update(double dt)
    {
        super.Update(dt);
        for(Individu i : getVisiteurs())
        {
            if(i instanceof Habitant)
            {
                Habitant hab = (Habitant) i;
                if(tempsRestantAvantObtention.containsKey(hab))
                {
                    double tnew = tempsRestantAvantObtention.get(hab)-dt*getEfficacite();
                    tempsRestantAvantObtention.put(hab,tnew);
                    if(tnew<0)
                    {
                        delivrerDiplome(hab);
                    }
                }
            }
        }
    }
    public void delivrerDiplome(Habitant hab)
    {
        tempsRestantAvantObtention.remove(hab);
        formation.donner(hab);
        if(hab.getUniversite() == this)
        {
            hab.setUniversite(null);
        }
    }
    public boolean peutPostuler(Habitant hab)
    {
        return places>tempsRestantAvantObtention.values().size() && hab.getUniversite() != this && formation.estApte(hab);
    }
    public void inscrire(Habitant hab)
    {
        if(peutPostuler(hab)) {
            hab.setUniversite(this);
            tempsRestantAvantObtention.putIfAbsent(hab,formation.getTempsObtention());
        }
    }
}
