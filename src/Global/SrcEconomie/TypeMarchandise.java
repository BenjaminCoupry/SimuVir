package Global.SrcEconomie;

import java.util.Objects;

public class TypeMarchandise {
    double prixFournisseur;
    String name;

    public double getPrixFournisseur() {
        return prixFournisseur;
    }

    public void setPrixFournisseur(double prixFournisseur) {
        this.prixFournisseur = prixFournisseur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeMarchandise that = (TypeMarchandise) o;
        return Double.compare(that.getPrixFournisseur(), getPrixFournisseur()) == 0 && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrixFournisseur(), getName());
    }
}
