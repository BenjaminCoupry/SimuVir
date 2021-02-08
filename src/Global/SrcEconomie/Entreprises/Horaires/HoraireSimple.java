package Global.SrcEconomie.Entreprises.Horaires;

import Global.Monde;

public class HoraireSimple implements HoraireTravail{
    double debut;
    double fin;

    public HoraireSimple(double debut, double fin) {
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public boolean doitTravailler() {
        double h = Monde.getHeure();
        if(debut<fin)
        {

            return h>debut && h<fin;
        }else
        {
            return h>debut || h<fin;
        }
    }
}
