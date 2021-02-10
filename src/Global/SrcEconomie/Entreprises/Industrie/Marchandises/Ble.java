package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.LinkedList;
import java.util.List;

public class Ble extends MarchandiseADurabilite{
    public Ble() {
        super(200);
    }

    @Override
    public double getPrixFournisseur() {
        return 1;
    }

    @Override
    public Marchandise creerInstance() {
        return new Ble();
    }

    @Override
    public List<FamillesMarchandises> getFamilles() {
        List<FamillesMarchandises> fm= super.getFamilles();
        fm.add(FamillesMarchandises.AGRICOLE);
        return fm;
    }
}
