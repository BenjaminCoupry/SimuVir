package Global.SrcEconomie.Entreprises.Enseignement;

import Global.SrcEconomie.DtListener;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Horaires.HoraireTravail;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcVirus.Individu;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Universite extends Entreprise implements DtListener {
    Formation formation;
    HashMap<Habitant,Double> tempsRestantAvantObtention;
    int places;
    HoraireTravail ouvertureEtudiants;

    public Universite(Hitbox hitbox, double tempsTraversee, double x, double y, int places, Formation formation,HoraireTravail ouvertureEtudiants) {
        super(hitbox, tempsTraversee, x, y);
        tempsRestantAvantObtention = new HashMap<>();
        this.formation = formation;
        this.places = places;
        this.ouvertureEtudiants = ouvertureEtudiants;

    }

    public boolean ouverte()
    {
        return ouvertureEtudiants.doitTravailler();
    }
    public void Update(double dt)
    {
        super.Update(dt);
        if(ouverte()) {
            for (Individu i : getVisiteurs()) {
                if (i instanceof Habitant) {
                    Habitant hab = (Habitant) i;
                    if (tempsRestantAvantObtention.containsKey(hab)) {
                        double tnew = tempsRestantAvantObtention.get(hab) - dt * getEfficacite();
                        tempsRestantAvantObtention.put(hab, tnew);
                        if (tnew < 0) {
                            delivrerDiplome(hab);
                        }
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
        List<Habitant> etudiants = getEtudiants();

        return (!etudiants.contains(hab))&&places>etudiants.size() && hab.getUniversite() ==null && formation.estApte(hab);
    }
    public void inscrire(Habitant hab)
    {
        if(peutPostuler(hab)) {
            hab.setUniversite(this);
            tempsRestantAvantObtention.putIfAbsent(hab,formation.getTempsObtention());
        }
    }

    public void supprimer()
    {
        super.supprimer();
        for(Habitant hab : getEtudiants())
        {
            hab.setUniversite(null);
        }
    }

    public void oublier(Habitant hab)
    {
        super.oublier(hab);
        tempsRestantAvantObtention.remove(hab);
    }
    public List<Habitant> getEtudiants() {
        return new LinkedList<>(tempsRestantAvantObtention.keySet());
    }
}
