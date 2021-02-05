package Global.SrcEconomie.Entreprises.Transport;

import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.TypeMarchandise;

public class OrdreTransport {
    Stockage depart;
    Stockage arrivee;
    TypeMarchandise typeMarchandise;

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

    public TypeMarchandise getTypeMarchandise() {
        return typeMarchandise;
    }

    public void setTypeMarchandise(TypeMarchandise typeMarchandise) {
        this.typeMarchandise = typeMarchandise;
    }
}
