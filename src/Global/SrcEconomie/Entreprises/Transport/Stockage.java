package Global.SrcEconomie.Entreprises.Transport;

import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;

public interface Stockage {
    Marchandise fournir(Marchandise tm);
    void stocker(Marchandise m);
    boolean disponible(Marchandise tm);
    double getPrix(Marchandise tm);
    void passerCommandes();
}
