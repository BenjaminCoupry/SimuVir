public class Immunite {
    double activite;
    double qteVaccin;

    public double getActivite() {
        return activite;
    }

    public double getQteVaccin() {
        return qteVaccin;
    }

    public void setQteVaccin(double qteVaccin) {
        this.qteVaccin = qteVaccin;
    }

    public void setActivite(double activite) {
        this.activite = activite;
    }
    public void Update(Infection i, double dt)
    {
        Virus v = i.getFamille();
        double delta = Math.sqrt(v.getGainImmunite()*(i.getChargeVirale()+qteVaccin)) - v.getPerteImmunite();
        double deltaVaccin = -v.getGainImmunite()*qteVaccin;
        activite = Math.max(0,activite+delta*dt);
        qteVaccin = Math.max(0,qteVaccin+deltaVaccin*dt);
    }
    public void Stimuler(Vaccin v)
    {
        qteVaccin = v.getQteInitiale();
    }


}
