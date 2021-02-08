package Global.SrcEconomie.Voierie;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Hitboxes.HitboxCercle;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcVirus.Lieu;
import PathFinding.Place;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Architecte {
    public static Route poserRoute(double x, double y)
    {
        Route nouv = new Route(new HitboxCercle(x,y,ConstantesEco.taille_route),
                ConstantesEco.taille_route/ConstantesEco.vitesse_marche,x,y);
        Monde.ajouterLieu(nouv);
        return nouv;
    }
    public static List<LieuPhysique> tirerRoute(Point2D A, Point2D B)
    {
        List<LieuPhysique> route = new LinkedList<>();
        int N = (int)(A.distance(B)/ ConstantesEco.taille_route);
        if(N>0)
        {
            Route last = null;
            for(int i=0;i<N;i++)
            {
                double k = i/(double)N;
                double x = A.getX()*(1-k)+B.getX()*k;
                double y = A.getY()*(1-k)+B.getY()*k;
                Route nv = poserRoute(x,y);
                route.add(nv);
                if(last!= null)
                {
                    nv.connecter(last);
                }
                last = nv;
            }
        }
        return route;
    }
    public static void tirerRoute(LieuPhysique l, Point2D B)
    {
        List<LieuPhysique> r = tirerRoute(l.getPoint(),B);
        if(r.size()>0)
        {
            r.get(0).connecter(l);
        }
    }
    public static void tirerRoute( Point2D A, LieuPhysique l)
    {
        List<LieuPhysique> r = tirerRoute(A,l.getPoint());
        if(r.size()>0)
        {
            r.get(r.size()-1).connecter(l);
        }
    }
    public static void tirerRoute(LieuPhysique l1, LieuPhysique l2)
    {
        List<LieuPhysique> r = tirerRoute(l1.getPoint(),l2.getPoint());
        if(r.size()>0)
        {
            r.get(0).connecter(l1);
            r.get(r.size()-1).connecter(l2);
        }
    }

    public static void poserSurRoute(LieuPhysique l)
    {
        Route pp = Monde.getRoutePlusProche(l.getPoint());
        if(pp!=null)
        {
            pp.connecter(l);
        }
        Monde.ajouterLieu(l);
    }
}
