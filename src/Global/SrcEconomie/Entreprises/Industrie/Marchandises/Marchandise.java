package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import Global.Monde;

import java.util.LinkedList;
import java.util.List;

public abstract class Marchandise {
    public abstract boolean user(double dt);
    public List<FamillesMarchandises> getFamilles()
    {
        List<FamillesMarchandises> f = new LinkedList<>();
        if(Monde.getObligatoires().stream().anyMatch(m->m.accepte(this)))
        {
            f.add(FamillesMarchandises.OBLIGATOIRE);
        }
        return f;
    }
    public abstract double getDurabilite();
    public  boolean accepte(Marchandise m)
    {
        Class c = this.getClass();
        return m.getClass().isAssignableFrom(c);
    }
    public boolean identique(Marchandise m)
    {
        Class c = this.getClass();
        return (m.getClass().equals(c));
    }
    public abstract double getPrixFournisseur();
    public abstract Marchandise creerInstance();
}
