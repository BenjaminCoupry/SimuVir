package Global.SrcEconomie;

import Global.SrcEconomie.Entreprises.FamillesMarchandises;
import Global.SrcEconomie.Entreprises.Marchandises;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TypeMarchandise {
    double prixFournisseur;
    String name;
    List<FamillesMarchandises> familles;
    public static HashMap<Marchandises,TypeMarchandise> mtypesMarchandises;

    public static TypeMarchandise get(Marchandises name)
    {
        return mtypesMarchandises.get(name);
    }
    public static List<TypeMarchandise> getParFamille(FamillesMarchandises fam)
    {
        return mtypesMarchandises.values().stream().filter(m ->m.familles.contains(fam)).collect(Collectors.toList());
    }

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
