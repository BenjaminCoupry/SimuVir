package Global.SrcEconomie.Entreprises.Industrie.Marchandises;

import java.util.LinkedList;
import java.util.List;

public abstract class MarchandiseADurabilite extends Marchandise{
    double durabilite;

    public MarchandiseADurabilite(double durabilite) {
        this.durabilite = durabilite;
    }

    @Override
    public boolean user(double dt) {
        durabilite-=dt;
        return durabilite>0;
    }

    @Override
    public List<FamillesMarchandises> getFamilles() {
        List<FamillesMarchandises> fm= super.getFamilles();
        fm.add(FamillesMarchandises.USABLE);
        return fm;
    }

    @Override
    public double getDurabilite() {
        return durabilite;
    }
}
