package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.LinkedList;
import java.util.List;

public class Masque extends Protection {

    public Masque() {
        super(5);
    }
    @Override
    public List<FamillesMarchandises> getFamilles() {
        List<FamillesMarchandises> fm= super.getFamilles();
        fm.add(FamillesMarchandises.EQUIPEMENT_FACIAL);
        return fm;
    }

    @Override
    public double getProtectionEmission() {
        return 0.7;
    }

    @Override
    public double getProtectionReception() {
        return 0.2;
    }

    @Override
    public double getPrixFournisseur() {
        return 4;
    }

    @Override
    public Marchandise creerInstance() {
        return new Masque();
    }
}
