package Global.SrcEconomie;

import Global.Monde;
import Global.SrcVirus.Lieu;
import PathFinding.InfoChemin;
import PathFinding.PathFinder;
import PathFinding.Place;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LieuPhysique extends Lieu {
    List<LieuPhysique> adjacents;
    Place place;
    InfoChemin infoChemin;

    public List<LieuPhysique> getAdjacents() {
        return adjacents;
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

}