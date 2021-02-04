package Global.SrcEconomie;

import java.util.List;

public class CompteBancaire {
    double somme;
    List<String> historiqueMotifs;
    List<Double> historiqueSolde;
    List<Double> historiqueMouvements;

    public void payer(CompteBancaire recepteur , double valeur, String motif)
    {
        debiter(valeur,motif);
        recepteur.crediter(valeur,motif);

    }
    public void prelever(CompteBancaire payeur, double valeur, String motif)
    {
        crediter(valeur,motif);
        payeur.debiter(valeur,motif);

    }

    public void crediter(double valeur, String motif)
    {
        somme += valeur;
        historiqueMotifs.add(motif);
        historiqueSolde.add(somme);
        historiqueMouvements.add(valeur);
    }
    public void debiter(double valeur, String motif)
    {
        somme -= valeur;
        historiqueMotifs.add(motif);
        historiqueSolde.add(somme);
        historiqueMouvements.add(-valeur);
    }

}
