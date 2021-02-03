public class Infection {
    Virus famille;
    Immunite immunite;
    double chargeVirale;
    Individu hote;

    public Virus getFamille() {
        return famille;
    }

    public void setFamille(Virus famille) {
        this.famille = famille;
    }

    public Individu getHote() {
        return hote;
    }

    public void setHote(Individu hote) {
        this.hote = hote;
    }

    public Immunite getImmunite() {
        return immunite;
    }

    public void setImmunite(Immunite immunite) {
        this.immunite = immunite;
    }

    public double getChargeVirale() {
        return chargeVirale;
    }

    public void setChargeVirale(double chargeVirale) {
        this.chargeVirale = chargeVirale;
    }

    public void Update(Immunite i, double dt)
    {
        double delta = famille.getReproduction()*chargeVirale-famille.getFragilite()*i.getActivite();
        chargeVirale = Math.max(0,chargeVirale+delta*dt);
    }
    public double getProbaInfection(double distance,double protection)
    {
        return (1.0-Math.exp(-distance*chargeVirale*famille.getContagiosite()))*(1.0-protection);
    }
    public double getProbaDeces(double soins)
    {
        double age = hote.getAge();
        double leth = famille.getLethalite().apply(age);
       return (1.0-Math.exp(-chargeVirale*leth))*(1.0-soins);
    }


}
