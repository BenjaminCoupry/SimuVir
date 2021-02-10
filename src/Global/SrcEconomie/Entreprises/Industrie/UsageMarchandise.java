package Global.SrcEconomie.Entreprises.Industrie;

import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;

public class UsageMarchandise {
    Marchandise typeMarchandise;
    int nbUsage;

    public UsageMarchandise(int nbUsage, Marchandise m) {
        this.nbUsage = nbUsage;
        typeMarchandise = m;
    }

    public Marchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(Marchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }

    public int getNbUsage() {
        return nbUsage;
    }

    public void setNbUsage(int nbUsage) {
        this.nbUsage = nbUsage;
    }
}
