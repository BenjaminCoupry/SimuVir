package SrcVirus;

import java.util.List;

public class Lieu {
    double modificateurTransmission;
    double surface;
    double temperatureMouvement;
    List<Individu> visiteurs;

    public double getModificateurTransmission() {
        return modificateurTransmission;
    }

    public void setModificateurTransmission(double modificateurTransmission) {
        this.modificateurTransmission = modificateurTransmission;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public double getTemperatureMouvement() {
        return temperatureMouvement;
    }

    public void setTemperatureMouvement(double temperatureMouvement) {
        this.temperatureMouvement = temperatureMouvement;
    }

    public List<Individu> getVisiteurs() {
        return visiteurs;
    }

    public void setVisiteurs(List<Individu> visiteurs) {
        this.visiteurs = visiteurs;
    }

    public double getFrequenceContact()
    {
        return getDensiteVisiteurs()*Math.sqrt(temperatureMouvement);
    }

    public int getNbVisiteurs()
    {
        return visiteurs.size();
    }
    public double getDensiteVisiteurs()
    {
        return getNbVisiteurs()/surface;
    }
    public double getDistanceMoyenneVisiteurs()
    {
        return Math.sqrt(surface/getNbVisiteurs())/2.0;
    }
    public int getNbContacts(double dt)
    {
        double freq = getFrequenceContact();
        double lambda = freq*dt;
        return Fonctions.poisson(lambda);
    }
    public void transmettre(double dt)
    {
        int nbContact = getNbContacts(dt);
        double dist = getDistanceMoyenneVisiteurs();
        for(int k =0;k<nbContact;k++) {
            int i0 = Fonctions.r.nextInt(getNbVisiteurs());
            int i1 = Fonctions.r.nextInt(getNbVisiteurs());
            if(i0!=i1) {
                Individu in0 = visiteurs.get(i0);
                Individu in1 = visiteurs.get(i1);
                in0.transmettreInfections(in1,modificateurTransmission,dist);
            }
        }

    }

}
