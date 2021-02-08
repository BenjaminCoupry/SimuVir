package Global.SrcEconomie.Entreprises.Industrie;

import Global.SrcEconomie.Entreprises.Marchandises;
import Global.SrcEconomie.Entreprises.TypeMarchandise;

public class UsageMarchandise {
    TypeMarchandise typeMarchandise;
    int nbUsage;

    public UsageMarchandise(int nbUsage, Marchandises m) {
        this.nbUsage = nbUsage;
        typeMarchandise = TypeMarchandise.get(m);
    }

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
