package Global.SrcEconomie.Entreprises.Industrie;

public class Marchandise {
    TypeMarchandise typeMarchandise;
    double durabilite;

    public Marchandise(TypeMarchandise typeMarchandise, double durabilite) {
        this.typeMarchandise = typeMarchandise;
        this.durabilite = durabilite;
    }

    public boolean user(double dt)
    {
        durabilite -= dt;
        return durabilite>0;
    }
    public Marchandise(TypeMarchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }

    public TypeMarchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(TypeMarchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }
}
