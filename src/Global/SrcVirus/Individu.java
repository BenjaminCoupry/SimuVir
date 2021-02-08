package Global.SrcVirus;

import Global.Monde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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


    public double getChargeViraleTotale()
    {
        return infections.values().stream().map(i->i.getChargeVirale()).collect(Collectors.summingDouble(i->i));
    }
    public double getImmuniteTotale()
    {
        return immunites.values().stream().map(i->i.getActivite()).collect(Collectors.summingDouble(i->i));
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
                inf.Update(dt);
                imu.Update(inf, dt);
                double pmort = inf.getProbaDeces(soins);
                if (Fonctions.r.nextDouble() < pmort) {
                    mort = true;
                    break;
                }
            }
        }
    }
    public void Update(double dt)
    {
        UpdateInfections(dt);
        age += dt;
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

    public double calculerImmuniteNative(Virus v)
    {
        List<Virus> virsFamille = getVirusConnus(v.getFamille());
        Immunite imnew;
        if (virsFamille.size() == 0) {
            return 0;
        } else {
            double immNative = 0;
            for (Virus vcommun : virsFamille) {
                Immunite immuProche = immunites.get(vcommun.getNom());
                immNative = Math.max(immuProche.getActivite(), immNative);
            }
            immNative = immNative * ConstantesVirus.partageImmuniteFamille;
           return immNative;
        }
    }

    public void Infecter(Virus v)
    {
        Monde.referencerVirus(v);
        double immNative = calculerImmuniteNative(v);
        if(!immunites.containsKey(v.getNom())) {
            Immunite imnew = new Immunite(immNative, 0, this, v);;
            immunites.put(v.getNom(), imnew);
            Infection infnew = new Infection(v, imnew, ConstantesVirus.chargeViraleInitaile, this);
            infections.put(v.getNom(), infnew);
        }
        else
        {
            Infection infActuelle = infections.get(v.getNom());
            infActuelle.setChargeVirale(Math.max(infActuelle.getChargeVirale(), ConstantesVirus.chargeViraleInitaile));
            Immunite immuniteActuelle = immunites.get(v.getNom());
            immuniteActuelle.setActivite(Math.max(immuniteActuelle.getActivite(),immNative));
        }

    }


}
