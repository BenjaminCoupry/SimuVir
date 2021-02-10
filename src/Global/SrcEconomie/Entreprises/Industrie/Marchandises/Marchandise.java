package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.List;

public abstract class Marchandise {
    public abstract boolean user(double dt);
    public abstract List<FamillesMarchandises> getFamilles();
    public abstract double getDurabilite();
    public  boolean correspond(Marchandise m)
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
