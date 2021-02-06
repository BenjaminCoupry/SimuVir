package Global.SrcEconomie.Entreprises.Transport;

import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.TypeMarchandise;

public interface Stockage {
    Marchandise fournir(TypeMarchandise tm);
    void stocker(Marchandise m);
    boolean disponible(TypeMarchandise tm);
    double getPrix(TypeMarchandise tm);
    void passerCommandes();
}
