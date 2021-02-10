package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.LinkedList;
import java.util.List;

public class Pain extends MarchandiseADurabilite{
    public Pain() {
        super(3);
    }

    @Override
    public double getPrixFournisseur() {
        return 5;
    }

    @Override
    public Marchandise creerInstance() {
        return null;
    }

    @Override
    public List<FamillesMarchandises> getFamilles() {
        List<FamillesMarchandises> fm= super.getFamilles();
        fm.add(FamillesMarchandises.ALIMENTAIRE);
        return fm;
    }
}
