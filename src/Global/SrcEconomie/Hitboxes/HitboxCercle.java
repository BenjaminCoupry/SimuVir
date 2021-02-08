package Global.SrcEconomie.Hitboxes;

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
}
