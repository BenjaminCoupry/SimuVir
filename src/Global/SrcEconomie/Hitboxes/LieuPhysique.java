package Global.SrcEconomie.Hitboxes;

import Global.Monde;
import Global.SrcEconomie.JourListener;
import Global.SrcVirus.Lieu;
import PathFinding.InfoChemin;
import PathFinding.PathFinder;
import PathFinding.Place;

import java.util.LinkedList;
import java.util.List;

public class LieuPhysique extends Lieu implements JourListener {
    List<LieuPhysique> adjacents;
    Place place;
    Hitbox hitbox;
    InfoChemin infoChemin;
    double tempsTraversee;

    public List<LieuPhysique> getAdjacents() {
        return adjacents;
    }

    public double getX()
    {
        return place.getX();
    }
    public double getY()
    {
        return place.getY();
    }
    public void setAdjacents(List<LieuPhysique> adjacents) {
        this.adjacents = adjacents;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public InfoChemin getInfoChemin() {
        return infoChemin;
    }

    public void setInfoChemin(InfoChemin infoChemin) {
        this.infoChemin = infoChemin;
    }

    public List<LieuPhysique> getChemin(LieuPhysique objectif)
    {
        List<Place> route = PathFinder.cheminPlusCourt(infoChemin,this.getPlace(), objectif.getPlace());
        List<LieuPhysique> retour = new LinkedList<>();
        for(Place pl : route)
        {
            retour.add(Monde.getLieuPlace().get(pl));
        }
        return retour;
    }
    public void supprimer()
    {
        Monde.getLieuxPhysiques().remove(this);
        for(LieuPhysique adj : getAdjacents())
        {
            adj.getAdjacents().remove(this);
        }
    }

    public double getTempsTraversee() {
        return tempsTraversee;
    }

    @Override
    public void jourPasse(double dt) {
        super.transmettre(dt);
    }
}
