package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.LinkedList;
import java.util.List;

public abstract class MarchandiseInfinie extends Marchandise{

    @Override
    public boolean user(double dt) {
        return true;
    }

    @Override
    public List<FamillesMarchandises> getFamilles() {
        List<FamillesMarchandises> fm= super.getFamilles();
        fm.add(FamillesMarchandises.DURABLE);
        return fm;
    }

    @Override
    public double getDurabilite() {
        return 0;
    }

}
