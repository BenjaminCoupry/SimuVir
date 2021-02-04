package SrcVirus;

public class TestVirus {
    TypeTest type;
    String familleCible;
    double sensibilite;
    double probaFauxPositif;
    double probaFauxNegatif;

    public TypeTest getType() {
        return type;
    }

    public void setType(TypeTest type) {
        this.type = type;
    }

    public String getFamilleCible() {
        return familleCible;
    }

    public void setFamilleCible(String familleCible) {
        this.familleCible = familleCible;
    }

    public double getSensibilite() {
        return sensibilite;
    }

    public void setSensibilite(double sensibilite) {
        this.sensibilite = sensibilite;
    }

    public double getProbaFauxPositif() {
        return probaFauxPositif;
    }

    public void setProbaFauxPositif(double probaFauxPositif) {
        this.probaFauxPositif = probaFauxPositif;
    }

    public double getProbaFauxNegatif() {
        return probaFauxNegatif;
    }

    public void setProbaFauxNegatif(double probaFauxNegatif) {
        this.probaFauxNegatif = probaFauxNegatif;
    }
    public boolean tester(double mesure)
    {
        boolean activation = mesure>getSensibilite();
        if(activation && Fonctions.r.nextDouble()<getProbaFauxNegatif())
        {
            activation = false;
        }
        else if((!activation) && Fonctions.r.nextDouble()<getProbaFauxPositif())
        {
            activation = true;
        }
        return activation;
    }
}
