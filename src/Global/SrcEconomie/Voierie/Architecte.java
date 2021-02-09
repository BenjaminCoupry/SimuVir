package Global.SrcEconomie.Voierie;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Entreprises.Enseignement.Formation;
import Global.SrcEconomie.Entreprises.Enseignement.TypeConnaissance;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Horaires.HoraireCompose;
import Global.SrcEconomie.Entreprises.Horaires.HoraireSimple;
import Global.SrcEconomie.Entreprises.Horaires.HoraireTravail;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises;
import Global.SrcEconomie.Entreprises.Industrie.RecetteIndustrie;
import Global.SrcEconomie.Entreprises.Industrie.TypeMarchandise;
import Global.SrcEconomie.Entreprises.Industrie.UsageMarchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.Hitboxes.HitboxCercle;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcVirus.Lieu;
import PathFinding.Place;
import sun.misc.FormattedFloatingDecimal;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
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
        poser(l);
    }
    public static void poser(LieuPhysique l)
    {
        Monde.ajouterLieu(l);
    }

    public static HoraireTravail getMatinale()
    {
        return new HoraireSimple(8,11);
    }
    public static HoraireTravail getApresMidi()
    {
        return new HoraireSimple(14,18);
    }
    public static HoraireTravail getHoraireMarchand()
    {
        return new HoraireSimple(17,23);
    }
    public static HoraireTravail getHorairesBureaux()
    {
        List<HoraireTravail> t = new ArrayList<>();
        t.add(getMatinale());
        t.add(getApresMidi());
        return new HoraireCompose(t);
    }
    public static HoraireTravail getTravailNuit()
    {
        return new HoraireSimple(22,7);
    }

    public static Formation getFormationBasique(TypeConnaissance tc, int level)
    {
        List<Connaissance> fourni = new ArrayList<>();
        List<Connaissance> requis = new ArrayList<>();
        fourni.add(new Connaissance(tc,level));
        if(level>0)
        {
            requis.add(new Connaissance(tc,level-1));
        }
        return new Formation(fourni,requis,level*ConstantesEco.difficulte_formation);
    }

    public static Poste getPosteDirecteur(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.MANAGMENT,1));
        return new Poste(100,10,req,getMatinale());
    }
    public static Poste getPosteManager(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.MANAGMENT,0));
        return new Poste(25,5,req,getHorairesBureaux());
    }
    public static Poste getPosteOuvrierJour(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.INDUSTRIE,0));
        return new Poste(10,3,req,getHorairesBureaux());
    }
    public static Poste getPosteOuvrierNuit(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.INDUSTRIE,0));
        return new Poste(15,3,req,getTravailNuit());
    }
    public static Poste getPosteEnseignant(int level){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.ENSEIGNEMENT,level));
        return new Poste(25,5,req,getHorairesBureaux());
    }
    public static Poste getPosteAgriculteur(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.AGRICULTURE,0));
        List<HoraireTravail> hc = new ArrayList<>();
        hc.add(getTravailNuit());
        hc.add(getHorairesBureaux());
        return new Poste(5,10,req,new HoraireCompose(hc));
    }
    public static Poste getPosteVendeur(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.COMMERCE,0));
        return new Poste(15,5,req,getHoraireMarchand());
    }
    public static Poste getPosteBanquier(){
        List<Connaissance> req = new ArrayList<>();
        req.add(new Connaissance(TypeConnaissance.FINANCE,0));
        return new Poste(30,5,req,getMatinale());
    }

    public static RecetteIndustrie getRecetteAgricole()
    {
        List<UsageMarchandise> tm = new LinkedList<>();
        tm.add(new UsageMarchandise(1,Marchandises.ALIMENTS_CRUS));
        return new RecetteIndustrie(new LinkedList<>(),tm,ConstantesEco.temps_prod_agricole);
    }
    public static RecetteIndustrie getRecetteAlimentaire()
    {
        List<UsageMarchandise> tm = new LinkedList<>();
        tm.add(new UsageMarchandise(2,Marchandises.ALIMENTS_CRUS));
        List<UsageMarchandise> tm2 = new LinkedList<>();
        tm.add(new UsageMarchandise(1,Marchandises.REPAS));
        return new RecetteIndustrie(tm,tm2,ConstantesEco.temps_prod_aliment);
    }

    public static Universite getUniv(TypeConnaissance tc, int level)
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleUniversite);
        double trav = ConstantesEco.tailleUniversite/ConstantesEco.vitesse_marche;
        HoraireTravail ouv = getHorairesBureaux();
        Formation f = getFormationBasique(tc,level);
        int places =(int)(ConstantesEco.place_par_taille*ConstantesEco.tailleUniversite);
        Universite u = new Universite(h,trav,0,0,places,f,ouv);
        getPosteDirecteur().attribuer(u);
        for(int i=0;i<(2*level+2);i++)
        {
            getPosteEnseignant(level).attribuer(u);
        }
        return u;
    }

}