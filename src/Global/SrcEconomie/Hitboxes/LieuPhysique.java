package Global.SrcEconomie.Hitboxes;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.JourListener;
import Global.SrcVirus.Fonctions;
import Global.SrcVirus.Lieu;
import PathFinding.InfoChemin;
import PathFinding.PathFinder;
import PathFinding.Place;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class LieuPhysique extends Lieu implements JourListener {
    List<LieuPhysique> adjacents;
    Place place;
    Hitbox hitbox;
    InfoChemin infoChemin;
    double tempsTraversee;

    public LieuPhysique(Hitbox hitbox, double tempsTraversee, double x, double y, double modificateurTransmission) {
        super(modificateurTransmission, hitbox.getSurface(), ConstantesEco.temperatureMouvement);
        this.hitbox = hitbox;
        this.tempsTraversee = tempsTraversee;
        place = new Place("place"+Fonctions.getUID(),x,y);
        this.hitbox = hitbox;
        adjacents = new LinkedList<>();
        infoChemin = null;
    }


    public void connecter(LieuPhysique autre)
    {
        if(!adjacents.contains(autre))
        {
            adjacents.add(autre);
        }
        if(!autre.getAdjacents().contains(autre))
        {
            autre.getAdjacents().add(autre);
        }
    }

    public List<LieuPhysique> getAdjacents() {
        return adjacents;
    }

    public Point2D getPoint()
    {
        return new Point2D.Double(getX(),getY());
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

    public Hitbox getHitbox() {
        return hitbox;
    }
}
