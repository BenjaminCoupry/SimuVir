package Global.SrcEconomie.Entreprises.Enseignement;

import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Habitant;
import Global.SrcVirus.Individu;

import java.util.HashMap;
import java.util.List;

public class Universite extends Entreprise {
    Formation formation;
    HashMap<Habitant,Double> tempsRestantAvantObtention;
    List<Habitant> etudiants;
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
        etudiants.remove(hab);
        formation.donner(hab);
        if(hab.getUniversite() == this)
        {
            hab.setUniversite(null);
        }
    }
    public boolean peutPostuler(Habitant hab)
    {
        return places>etudiants.size() && hab.getUniversite() ==null && formation.estApte(hab);
    }
    public void inscrire(Habitant hab)
    {
        if(peutPostuler(hab)) {
            hab.setUniversite(this);
            tempsRestantAvantObtention.putIfAbsent(hab,formation.getTempsObtention());
            if(!etudiants.contains(hab))
            {
                etudiants.add(hab);
            }
        }
    }
}
