package Global.Editor;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Entreprises.Enseignement.Formation;
import Global.SrcEconomie.Entreprises.Enseignement.TypeConnaissance;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Finance.Banque;
import Global.SrcEconomie.Entreprises.Horaires.HoraireCompose;
import Global.SrcEconomie.Entreprises.Horaires.HoraireSimple;
import Global.SrcEconomie.Entreprises.Horaires.HoraireTravail;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Ble;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Pain;
import Global.SrcEconomie.Entreprises.Industrie.RecetteIndustrie;
import Global.SrcEconomie.Entreprises.Industrie.UsageMarchandise;
import Global.SrcEconomie.Entreprises.Industrie.Usine;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Etat;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.Hitboxes.HitboxCercle;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcEconomie.Voierie.Route;
import Global.SrcVirus.Fonctions;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Architecte {

    public static EditMode editmod = EditMode.ROUTE;
    public static EditMode editmod_ = EditMode.ROUTE;
    public static LieuPhysique aPoser = null;
    public static Habitant aPoserH= null;
    public static Selectionnable selectionne = null;
    public static Point2D ptA = null;
    public static Point2D ptB = null;
    private static int uval =0;
    private static int ubat =0;

    public static void passerMode(EditMode mode)
    {
        switch (mode)
        {
            case PLACER_BATIMENT:
                Lanceur.pause();
                aPoser = getBatiment();
                editmod_ = EditMode.PLACER_BATIMENT;
                break;
            case ROUTE:
                Lanceur.pause();
                ptA = null;
                ptB = null;
                editmod_ = EditMode.ROUTE;
                break;
            case SUPPRIMER:
                Lanceur.pause();
                editmod_ = EditMode.SUPPRIMER;
                break;
            case VIE:
                Monde.majListeners();
                clcPath();
                editmod_ = EditMode.VIE;
                Lanceur.resume();
                break;
            case PLACER_HABITANT:
                Lanceur.pause();
                aPoserH = null;
                editmod_ = EditMode.PLACER_HABITANT;
                break;
        }
        updateEditmod_();
    }
    public static void clique(double x, double y)
    {
        if(editmod.equals(EditMode.PLACER_HABITANT))
        {
            Selectionnable lp = Monde.selectionner(x,y);
            if(lp != null)
            {
                if(lp instanceof LieuPhysique) {
                    aPoserH = getHabitant((LieuPhysique) lp);
                   aPoserH.entrerLieu((LieuPhysique) lp);
                   Monde.ajouterHabitant(aPoserH);
                }
            }
        }
        else if(editmod.equals(EditMode.PLACER_BATIMENT))
        {
            if(aPoser!= null) {
                aPoser.setX(x);
                aPoser.setY(y);
                poserSurRoute(aPoser);
                aPoser = getBatiment();
            }
        }
        else if(editmod.equals(EditMode.SUPPRIMER))
        {
            Selectionnable lp = Monde.selectionner(x,y);
            if(lp != null)
            {
                if(lp instanceof LieuPhysique) {
                   Monde.supprimerLieu((LieuPhysique) lp);
                }else if (lp instanceof Habitant)
                {
                    Monde.supprimerHabitant((Habitant) lp);
                }
            }

        }
        else if(editmod.equals(EditMode.VIE))
        {
            selectionne = Monde.selectionner(x,y);
        }
        else if(editmod.equals(EditMode.ROUTE))
        {
            if(ptA == null)
            {
                ptA = new Point2D.Double(x,y);
            }else
            {
                if(ptB==null)
                {
                    ptB = new Point2D.Double(x,y);
                    LieuPhysique la = Monde.selectionnerLieu(ptA.getX(),ptA.getY());
                    LieuPhysique lb = Monde.selectionnerLieu(x,y);
                    if(la != null && lb != null)
                    {
                        tirerRoute(la,lb);
                    }else if(la == null && lb != null)
                    {
                        tirerRoute(ptA,lb);
                    }
                    else if(la != null && lb == null)
                    {
                        tirerRoute(la,ptB);
                    }
                    else if(la == null && lb == null)
                    {
                        tirerRoute(ptA,ptB);
                    }
                    ptB =null;
                    ptA = null;
                }
            }
        }
        Lanceur.getMr().repaint();
    }

    public static void clcPath()
    {
        Monde.calculerInfoChemins();
    }

    public static Route poserRoute(double x, double y)
    {
        Route nouv = new Route(new HitboxCercle(x,y,ConstantesEco.taille_route),
                ConstantesEco.taille_route/ConstantesEco.vitesse_marche,x,y);
        if(poser(nouv)) {
            return nouv;
        }else
        {
            return null;
        }
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
                if(nv != null) {
                    route.add(nv);

                    if (last != null) {
                        nv.connecter(last);
                    }
                    last = nv;
                }else
                {
                    last = null;
                }
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
            if(pp.getPoint().distance(l.getPoint())<=ConstantesEco.dist_connexion_route) {
                pp.connecter(l);
            }
        }
        poser(l);
    }
    public static boolean poser(LieuPhysique l)
    {
        if(Monde.selectionnerLieu(l.getX(),l.getY()) ==null) {
            Monde.ajouterLieu(l);
            return true;
        }
        return false;
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

    public static Etat getEtatBasique()
    {
        return new Etat(Fonctions.getConstante(0.2),Fonctions.getConstante(0.01),100,Fonctions.getConstante(5),Fonctions.getConstante(15));
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
        tm.add(new UsageMarchandise(1,new Ble()));
        return new RecetteIndustrie(new LinkedList<>(),tm,ConstantesEco.temps_prod_agricole);
    }
    public static RecetteIndustrie getRecetteAlimentaire()
    {
        List<UsageMarchandise> tm = new LinkedList<>();
        tm.add(new UsageMarchandise(2,new Ble()));
        List<UsageMarchandise> tm2 = new LinkedList<>();
        tm.add(new UsageMarchandise(1,new Pain()));
        return new RecetteIndustrie(tm,tm2,ConstantesEco.temps_prod_aliment);
    }

    public static Universite getUniversite(TypeConnaissance tc, int level)
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
    public static List<UsageMarchandise> getCatalogueBasique()
    {
        List<UsageMarchandise> um = new LinkedList<>();
        um.add(new UsageMarchandise(5,new Pain()));
        return um;
    }
    public static Boutique getBoutique()
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleBoutique);
        double trav = ConstantesEco.tailleBoutique/ConstantesEco.vitesse_marche;
        Boutique b = new Boutique(h,trav,0,0,getCatalogueBasique());
        getPosteVendeur().attribuer(b);
        return b;
    }
    public static Banque getBanque()
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleBanque);
        double trav = ConstantesEco.tailleBanque/ConstantesEco.vitesse_marche;
        Banque b = new Banque(h,trav,0,0,0.1,5);
        getPosteBanquier().attribuer(b);
        getPosteManager().attribuer(b);
        return b;
    }
    public static Usine getFerme()
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleFerme);
        double trav = ConstantesEco.tailleFerme/ConstantesEco.vitesse_marche;
        Usine b = new Usine(h,trav,0,0,getRecetteAgricole());
        for(int i=0;i<3;i++) {
            getPosteAgriculteur().attribuer(b);
        }
        return b;
    }
    public static Usine getBoulangerie()
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleBoulangerie);
        double trav = ConstantesEco.tailleBoulangerie/ConstantesEco.vitesse_marche;
        Usine b = new Usine(h,trav,0,0,getRecetteAlimentaire());
        for(int i=0;i<3;i++) {
            getPosteOuvrierJour().attribuer(b);
        }
        for(int i=0;i<2;i++) {
            getPosteOuvrierNuit().attribuer(b);
        }
        return b;
    }
    public static EntrepriseTransport getEntrepriseTransport()
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleEntrepriseTransport);
        double trav = ConstantesEco.tailleEntrepriseTransport/ConstantesEco.vitesse_marche;
        EntrepriseTransport b = new EntrepriseTransport(h,trav,0,0,5);
        getPosteVendeur().attribuer(b);
        for(int i=0;i<3;i++) {
            getPosteOuvrierJour().attribuer(b);
        }
        for(int i=0;i<2;i++) {
            getPosteOuvrierNuit().attribuer(b);
        }
        return b;
    }
    public static Residence getMaison()
    {
        Hitbox h = new HitboxCercle(0,0,ConstantesEco.tailleMaison);
        double trav = ConstantesEco.tailleMaison/ConstantesEco.vitesse_marche;
        return new Residence(h,trav,0,0,5,5,Fonctions.getUID(),Monde.getEtat());
    }
    public static Habitant getHabitant(LieuPhysique lp)
    {
        return new Habitant(Fonctions.getSigmoide(100,0.5,1),0,Fonctions.getUID(),Fonctions.getUID(),lp);
    }
    public static Universite getUniversite()
    {
        TypeConnaissance tc = TypeConnaissance.values()[uval];
        uval = (uval+1)%TypeConnaissance.values().length;
        return getUniversite(tc,0);
    }
    public static LieuPhysique getBatiment()
    {
        ubat = (ubat+1)%6;
        switch (ubat)
        {
            case 0:
                return getBoutique();
            case 1:
                return getUniversite();
            case 2:
                return getBanque();
            case 3:
                return getFerme();
            case 4:
                return getBoulangerie();
            case 5 :
                return getEntrepriseTransport();
            default:
                return null;
        }
    }

    public static EditMode getEditmod() {
        return editmod;
    }

    public static void updateEditmod_() {
        editmod = editmod_;
    }
    //TODO finaliser

}
