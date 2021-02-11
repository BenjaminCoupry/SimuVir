package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.LinkedList;
import java.util.List;

public abstract class Protection extends MarchandiseADurabilite{

    public Protection(double durabilite) {
        super(durabilite);
    }

    @Override
    public List<FamillesMarchandises> getFamilles() {
        List<FamillesMarchandises> fm= super.getFamilles();
        fm.add(FamillesMarchandises.PROTECTION);
        return fm;
    }
    public abstract double getProtectionEmission();
    public abstract double getProtectionReception();
}
