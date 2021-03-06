package Global.SrcEconomie.Entreprises.Finance;

import Global.Monde;

import java.util.LinkedList;
import java.util.List;

public class CompteBancaire {
    double somme;
    List<String> historiqueMotifs;
    List<Double> historiqueSolde;
    List<Double> historiqueMouvements;

    public CompteBancaire(double somme) {
        this.somme = somme;
        historiqueMotifs = new LinkedList<>();
        historiqueSolde = new LinkedList<>();
        historiqueMouvements = new LinkedList<>();
    }

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
        double valeurImpot = valeur * Monde.getEtat().getTaxeVente().apply(valeur);
        double valeurReelle = valeur - valeurImpot;
        somme += valeurReelle ;
        historiqueMotifs.add(motif);
        historiqueSolde.add(somme);
        historiqueMouvements.add(valeurReelle);
        Monde.getEtat().incrementerPIB(valeur);
        if(this != Monde.getEtat().getCompteBancaire()) {
            Monde.getEtat().getCompteBancaire().crediter(valeurImpot, "TVA " + motif);
        }
    }
    public void debiter(double valeur, String motif)
    {
        somme -= valeur;
        historiqueMotifs.add(motif);
        historiqueSolde.add(somme);
        historiqueMouvements.add(-valeur);
    }

    public double getSomme() {
        return somme;
    }

    public List<String> getHistoriqueMotifs() {
        return historiqueMotifs;
    }

    public List<Double> getHistoriqueSolde() {
        return historiqueSolde;
    }

    public List<Double> getHistoriqueMouvements() {
        return historiqueMouvements;
    }
}
