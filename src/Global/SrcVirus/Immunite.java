package Global.SrcVirus;

public class Immunite {
    double activite;
    double qteVaccin;
    Individu hote;
    Virus virus;

    public Immunite(double activite, double qteVaccin, Individu hote, Virus virus) {
        this.activite = activite;
        this.qteVaccin = qteVaccin;
        this.hote = hote;
        this.virus = virus;
    }

    public Virus getVirus() {
        return virus;
    }

    public void setVirus(Virus virus) {
        this.virus = virus;
    }
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


    public Individu getHote() {
        return hote;
    }

    public void setHote(Individu hote) {
        this.hote = hote;
    }

    public void Update(Infection i, double dt)
    {
        Virus v = i.getVirus();
        double gainImmu = v.getGainImmunite().apply(hote.getAge());
        double perteImmu = v.getPerteImmunite().apply(hote.getAge());
        double delta = Math.sqrt(gainImmu*(i.getChargeVirale()+qteVaccin)) - perteImmu;
        double deltaVaccin = -gainImmu*qteVaccin;
        activite = Math.max(0,activite+delta*dt);
        qteVaccin = Math.max(0,qteVaccin+deltaVaccin*dt);
    }
    public void Stimuler(Vaccin v)
    {
        qteVaccin += v.getQteInitiale();
    }



}
