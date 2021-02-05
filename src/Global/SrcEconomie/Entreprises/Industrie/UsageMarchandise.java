package Global.SrcEconomie.Entreprises.Industrie;

import Global.SrcEconomie.TypeMarchandise;

public class UsageMarchandise {
    TypeMarchandise typeMarchandise;
    int nbUsage;

    public TypeMarchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(TypeMarchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }

    public int getNbUsage() {
        return nbUsage;
    }

    public void setNbUsage(int nbUsage) {
        this.nbUsage = nbUsage;
    }
}
