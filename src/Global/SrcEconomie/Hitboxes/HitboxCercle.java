package Global.SrcEconomie.Hitboxes;

import Global.SrcVirus.Fonctions;
import java.awt.geom.Point2D;

public class HitboxCercle implements Hitbox{
    double x0;
    double y0;
    double r;

    public HitboxCercle(double x0, double y0, double r) {
        this.x0 = x0;
        this.y0 = y0;
        this.r = r;
    }

    @Override
    public boolean contact(double x, double y) {
        return Math.sqrt(Math.pow(x-x0,2)+Math.pow(y-y0,2))<r;
    }

    @Override
    public double getSurface() {
        return Math.PI*r*r;
    }

    @Override
    public Point2D getRandomPoint() {
        double ray = r* Fonctions.r.nextDouble();
        double teta = Math.PI*2.0*Fonctions.r.nextDouble();
        return new Point2D.Double(x0+ray*Math.cos(teta),y0+ray*Math.sin(teta));
    }
}
