package Global.SrcEconomie.Hitboxes;

import Global.Editor.Selectionnable;
import Global.Monde;
import Global.Render.Texturable;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.DtListener;
import Global.SrcEconomie.JourListener;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcVirus.Fonctions;
import Global.SrcVirus.Lieu;
import PathFinding.InfoChemin;
import PathFinding.PathFinder;
import PathFinding.Place;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class LieuPhysique extends Lieu implements DtListener, Selectionnable, Texturable {
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
        if(!autre.getAdjacents().contains(this))
        {
            autre.getAdjacents().add(this);
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

    public void setX(double x)
    {
        place.setX(x);
        hitbox.setX(x);
    }
    public void setY(double y)
    {
        place.setY(y);
        hitbox.setY(y);
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
        for(LieuPhysique adj : getAdjacents())
        {
            adj.getAdjacents().remove(this);
        }
        for(Habitant h : Monde.getHabitants())
        {
            h.oublier(this);
        }
    }

    public double getTempsTraversee() {
        return tempsTraversee;
    }


    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void Update(double dt) {
        super.transmettre(dt);
    }
}
