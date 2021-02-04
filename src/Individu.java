import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Individu {
    HashMap<String,Immunite> immunites;
    HashMap<String,Infection> infections;
    Function<Double,Double> probaMortNaturelle;
    double age;
    boolean mort;
    double soins;
    double protectionEmission;

    public double getSoins() {
        return soins;
    }

    public void setSoins(double soins) {
        this.soins = soins;
    }

    public double getProtectionEmission() {
        return protectionEmission;
    }

    public void setProtectionEmission(double protectionEmission) {
        this.protectionEmission = protectionEmission;
    }

    public HashMap<String, Immunite> getImmunites() {
        return immunites;
    }

    public void setImmunites(HashMap<String, Immunite> immunites) {
        this.immunites = immunites;
    }

    public HashMap<String,Infection> getInfections() {
        return infections;
    }

    public void setInfections(HashMap<String,Infection> infections) {
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
            for (Infection inf : infections.values()) {
                Immunite imu = immunites.get(inf.getVirus().getNom());
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
        for(Virus cible : vaccin.getCibles()) {
            Immunite imu = immunites.get(cible.getNom());
            imu.Stimuler(vaccin);
        }
    }
    public List<Virus> getVirusConnus(String famille)
    {
        List<Virus> res = new ArrayList<>();
        for (Infection inf : infections.values()) {
            Virus v = inf.getVirus();
            if(v.getFamille().equals(famille))
            {
                if(!res.contains(v))
                {
                    res.add(v);
                }
            }
        }
        for (Immunite immunite : immunites.values()) {
            Virus v = immunite.getVirus();
            if(v.getFamille().equals(famille))
            {
                if(!res.contains(v))
                {
                    res.add(v);
                }
            }
        }
        return res;
    }

    public boolean Tester(TestVirus tv)
    {
        List<Virus> virs = getVirusConnus(tv.getFamilleCible());
        double val=0;
        for(Virus vir : virs) {
            if (tv.getType().equals(TypeTest.IMMUNITE)) {
                if(immunites.containsKey(vir.getNom())) {
                    Immunite imu = immunites.get(vir.getNom());
                    val += imu.getActivite();
                }
            } else {
                if(infections.containsKey(vir.getNom())) {
                    Infection inf = infections.get(vir.getNom());
                    val += inf.getChargeVirale();
                }
            }
        }
        return tv.tester(val);

    }

    public void transmettreInfections(Individu cible, double modificateurTransmission, double distance)
    {
        for (Infection inf : infections.values()) {
            double p = modificateurTransmission * inf.getPotentielTransmission(distance);
            if (Fonctions.r.nextDouble() < p) {
                cible.Infecter(inf.getVirus().muter());
            }
        }
    }

    public void Infecter(Virus v)
    {
        Monde.referencerVirus(v);
        Immunite imnew = new Immunite(0,0,this,v);
        immunites.putIfAbsent(v.getNom(),imnew);
        Infection infnew = new Infection(v,immunites.get(v.getNom()),Constantes.chargeViraleInitaile,this);
        infections.putIfAbsent(v.getNom(),infnew);
    }


}
