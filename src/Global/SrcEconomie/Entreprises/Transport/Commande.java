package Global.SrcEconomie.Entreprises.Transport;

import Global.SrcEconomie.Entreprises.Finance.CompteBancaire;
import Global.SrcEconomie.Entreprises.Industrie.TypeMarchandise;

public class Commande {
    Stockage destination;
    TypeMarchandise typeMarchandise;
    CompteBancaire paiement;

    public Commande(Stockage destination, TypeMarchandise typeMarchandise, CompteBancaire paiement) {
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

    public TypeMarchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(TypeMarchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }

    public CompteBancaire getPaiement() {
        return paiement;
    }

    public void setPaiement(CompteBancaire paiement) {
        this.paiement = paiement;
    }
}
