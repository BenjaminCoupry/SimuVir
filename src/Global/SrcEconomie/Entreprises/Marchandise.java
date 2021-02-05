package Global.SrcEconomie.Entreprises;

import Global.SrcEconomie.TypeMarchandise;

public class Marchandise {
    TypeMarchandise typeMarchandise;

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
