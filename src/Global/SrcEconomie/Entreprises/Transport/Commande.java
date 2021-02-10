package Global.SrcEconomie.Entreprises.Transport;

import Global.SrcEconomie.Entreprises.Finance.CompteBancaire;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;


public class Commande {
    Stockage destination;
    Marchandise typeMarchandise;
    CompteBancaire paiement;

    public Commande(Stockage destination, Marchandise typeMarchandise, CompteBancaire paiement) {
        this.destination = destination;
        this.typeMarchandise = typeMarchandise;
        this.paiement = paiement;
    }

    public Stockage getDestination() {
        return destination;
    }

    public void setDestination(Stockage destination) {
        this.destination = destination;
    }

    public Marchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(Marchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }

    public CompteBancaire getPaiement() {
        return paiement;
    }

    public void setPaiement(CompteBancaire paiement) {
        this.paiement = paiement;
    }
}
