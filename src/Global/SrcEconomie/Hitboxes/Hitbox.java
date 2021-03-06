package Global.SrcEconomie.Hitboxes;

import java.awt.*;
import java.awt.geom.Point2D;

public interface Hitbox {
    boolean contact(double x, double y);
    double getSurface();
    Point2D getRandomPoint();
    Point2D getPoint();
    double getLongueur();
    void setX(double x);
    void setY(double y);
    double getX();
    double getY();
}
