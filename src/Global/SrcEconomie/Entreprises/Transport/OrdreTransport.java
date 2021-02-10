package Global.SrcEconomie.Entreprises.Transport;

import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;

public class OrdreTransport {
    Stockage depart;
    Stockage arrivee;
    Marchandise typeMarchandise;
    StatutLivraison statut;

    public OrdreTransport(Stockage depart, Stockage arrivee, Marchandise typeMarchandise, StatutLivraison statut) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.typeMarchandise = typeMarchandise;
        this.statut = statut;
    }

    public Stockage getDepart() {
        return depart;
    }

    public void setDepart(Stockage depart) {
        this.depart = depart;
    }

    public Stockage getArrivee() {
        return arrivee;
    }

    public void setArrivee(Stockage arrivee) {
        this.arrivee = arrivee;
    }

    public Marchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(Marchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }

    public StatutLivraison getStatut() {
        return statut;
    }

    public void setStatut(StatutLivraison statut) {
        this.statut = statut;
    }
}
