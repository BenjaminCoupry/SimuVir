import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Individu {
    HashMap<String,Immunite> immunites;
    List<Infection> infections;
    Function<Double,Double> probaMortNaturelle;
    double age;
    boolean mort;
    double soins;
    double protections;

    public double getSoins() {
        return soins;
    }

    public void setSoins(double soins) {
        this.soins = soins;
    }

    public double getProtections() {
        return protections;
    }

    public void setProtections(double protections) {
        this.protections = protections;
    }

    public HashMap<String, Immunite> getImmunites() {
        return immunites;
    }

    public void setImmunites(HashMap<String, Immunite> immunites) {
        this.immunites = immunites;
    }

    public List<Infection> getInfections() {
        return infections;
    }

    public void setInfections(List<Infection> infections) {
        this.infections = infections;
    }

    public boolean isMort() {
        return mort;
    }

    public void setMort(boolean mort) {
        this.mort = mort;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public Function<Double, Double> getProbaMortNaturelle() {
        return probaMortNaturelle;
    }

    public void setProbaMortNaturelle(Function<Double, Double> probaMortNaturelle) {
        this.probaMortNaturelle = probaMortNaturelle;
    }


    public void UpdateInfections(double dt)
    {
        if(!mort) {
            double pmortnat = probaMortNaturelle.apply(age);
            if (Fonctions.r.nextDouble() < pmortnat) {
                mort = true;
            }
        }
        if(!mort) {
            for (Infection inf : infections) {
                Immunite imu = immunites.get(inf.getFamille().getNom());
                inf.Update(imu, dt);
                imu.Update(inf, dt);
                double pmort = inf.getProbaDeces(soins);
                if (Fonctions.r.nextDouble() < pmort) {
                    mort = true;
                    break;
                }
            }
        }
    }



    public void Vacciner(Vaccin vaccin)
    {
        Immunite imu = immunites.get(vaccin.getCible().getNom());
        imu.Stimuler(vaccin);
    }


}
