public class Infection {
    Virus virus;
    Immunite immunite;
    double chargeVirale;
    Individu hote;

    public Infection(Virus virus, Immunite immunite, double chargeVirale, Individu hote) {
        this.virus = virus;
        this.immunite = immunite;
        this.chargeVirale = chargeVirale;
        this.hote = hote;
    }

    public Virus getVirus() {
        return virus;
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
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

    public void Update( double dt)
    {
        double delta = virus.getReproduction()*chargeVirale- virus.getFragilite()*immunite.getActivite();
        chargeVirale = Math.max(0,chargeVirale+delta*dt);
    }
    public double getPotentielTransmission(double distance )
    {
        double protectionEmissions = hote.getProtectionEmission();
        double k_dist = 1.0-Math.exp(-(1/Math.pow(distance*protectionEmissions,2)));
        double k_vir = 1.0-Math.exp(-(chargeVirale* virus.getContagiosite()));
        return k_dist*k_vir;
    }
    public double getProbaDeces(double soins)
    {
        double age = hote.getAge();
        double leth = virus.getLethalite().apply(age);
       return (1.0-Math.exp(-chargeVirale*leth))*(1.0-soins);
    }


}
