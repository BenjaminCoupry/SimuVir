import java.util.List;

public class Vaccin {

    List<Virus> cibles;
    double qteInitiale;

    public List<Virus> getCibles() {
        return cibles;
    }

    public void setCibles(List<Virus> cibles) {
        this.cibles = cibles;
    }

    public double getQteInitiale() {
        return qteInitiale;
    }

    public void setQteInitiale(double qteInitiale) {
        this.qteInitiale = qteInitiale;
    }
}
