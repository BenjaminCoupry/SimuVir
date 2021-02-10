package Global;

import Global.SrcEconomie.*;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Finance.Banque;
import Global.SrcEconomie.Entreprises.Finance.Monetaire;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.FamillesMarchandises;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.Entreprises.Industrie.Usine;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcEconomie.Voierie.Route;
import Global.SrcVirus.Virus;
import PathFinding.*;


import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

public class Monde {
    static HashMap<String,Virus> virusExistants;
    static List<LieuPhysique> lieuxPhysiques;
    static HashMap<Place,LieuPhysique> lieuPlace;
    static List<Habitant> habitants;
    static double heure;
    static int jour;
    static double dt;
    static Etat etat;
    //TODO enregistrer les listeners
    static List<JourListener> jourListeners;
    static List<DtListener> dtListeners;



    public static void referencerVirus(Virus virus) {
        if(!virusExistants.containsKey(virus.getNom())) {
            virusExistants.put(virus.getNom(),virus);
        }
    }
    public static Virus getVirus(String nom)
    {
        return virusExistants.get(nom);
    }
    public static void addJourListener(JourListener jl)
    {
        jourListeners.add(jl);
    }
    public static void addDtListener(DtListener dl)
    {
        dtListeners.add(dl);
    }

    public static List<Stockage> trouverDisponibilites(Marchandise tm, TypeDisponibilite dispo)
    {
        List<Stockage> res = new ArrayList<>();
        for(Stockage sto : getStockages())
        {
            if(dispo.equals(TypeDisponibilite.MAGASIN))
            {
                if(sto instanceof Boutique)
                {
                    if(sto.disponible(tm))
                    {
                        res.add(sto);
                    }
                }
            }else if(dispo.equals(TypeDisponibilite.USINE))
            {
                if(sto instanceof Usine)
                {
                    if(sto.disponible(tm))
                    {
                        res.add(sto);
                    }
                }
            }
        }
        return res;
    }
    public static List<Universite> trouverUniversitesPossibles(Habitant hab)
    {
        List<Universite> mt = getUniversites().stream()
                .filter(univ -> univ.peutPostuler(hab))
                .collect(Collectors.toList());
        return mt;
    }
    public static List<Banque> trouverBanquesPossibles(Monetaire mon)
    {
        List<Banque> mt = getBanques().stream()
                .filter(b -> b.peutSinscrire(mon))
                .collect(Collectors.toList());
        return mt;
    }


    public static void ajouterLieu(LieuPhysique l)
    {
        lieuxPhysiques.add(l);
        lieuPlace.put(l.getPlace(),l);
        if(l instanceof JourListener)
        {
            addJourListener((JourListener) l);
        }
        if(l instanceof DtListener)
        {
            addDtListener((DtListener) l);
        }

    }
    public static void ajouterHabitant(Habitant hab)
    {
        habitants.add(hab);
    }

    public static List<Poste> trouverPostesPossibles(Habitant hab)
    {
        List<Poste> mt = getPostes().stream()
                .filter(poste -> poste.peutPostuler(hab))
                .collect(Collectors.toList());
        return mt;
    }
    public static List<Residence> trouverResidencesPossibles(Habitant hab)
    {
        List<Residence> mt = getResidences().stream()
                .filter(res -> res.peutPostuler(hab))
                .collect(Collectors.toList());
        return mt;
    }

    public static List<Stockage> getStockages() {
        List<Stockage> mt = getLieuxPhysiques().stream()
                .filter(res -> res instanceof Stockage)
                .map(l -> (Stockage)l)
                .collect(Collectors.toList());
        return mt;
    }
    public static List<Universite> getUniversites() {
        List<Universite> mt = getEntreprises().stream()
                .filter(res -> res instanceof Universite)
                .map(l -> (Universite)l)
                .collect(Collectors.toList());
        return mt;
    }

    public static List<Banque> getBanques() {
        List<Banque> mt = getEntreprises().stream()
                .filter(res -> res instanceof Banque)
                .map(l -> (Banque)l)
                .collect(Collectors.toList());
        return mt;
    }

    public static List<Entreprise> getEntreprises() {
        List<Entreprise> mt = getLieuxPhysiques().stream()
                .filter(res -> res instanceof Entreprise)
                .map(l -> (Entreprise)l)
                .collect(Collectors.toList());
        return mt;
    }

    public static List<Monetaire> getMonetaires()
    {
        List<Monetaire> mt = getEntreprises().stream()
                .filter(res -> res instanceof Monetaire)
                .collect(Collectors.toList());
        mt.addAll(getHabitants());
        mt.add(getEtat());
        return mt;
    }

    public static List<EntrepriseTransport> getTransporteurs() {
        List<EntrepriseTransport> mt = getEntreprises().stream()
                .filter(res -> res instanceof EntrepriseTransport)
                .map(l -> (EntrepriseTransport)l)
                .collect(Collectors.toList());
        return mt;
    }


    public static List<Boutique> getBoutiques() {

        List<Boutique> mt = getEntreprises().stream()
                .filter(res -> res instanceof Boutique)
                .map(l -> (Boutique)l)
                .collect(Collectors.toList());
        return mt;
    }


    public static List<Poste> getPostes() {
        List<Entreprise> mt = getEntreprises();
        List<Poste> postes = new LinkedList<>();
        for(Entreprise e : mt)
        {
            postes.addAll(e.getPostes());
        }
        return postes;
    }

    public static List<Monetaire> getImposables()
    {
        List<Monetaire> mon = new LinkedList<>();
        mon.addAll(getEntreprises());
        mon.addAll(getHabitants());
        return mon;
    }

    public static List<Route> getRoutes()
    {
        List<Route> mt = getLieuxPhysiques().stream()
                .filter(res -> res instanceof Route)
                .map(l -> (Route)l)
                .collect(Collectors.toList());
        return mt;
    }

    public static Route getRoutePlusProche(Point2D comparaison)
    {
        List<Route> routes = getRoutes();
        if(routes.size()>0) {
            return routes.stream().min(
                    Comparator.comparingDouble(x -> comparaison.distance(x.getPoint()))
            ).get();
        }
        else
        {
            return null;
        }
    }



    public static List<LieuPhysique> getLieuxPhysiques() {
        return lieuxPhysiques;
    }

    public static  HashMap<Place, LieuPhysique> getLieuPlace() {
        return lieuPlace;
    }

    public static List<Habitant> getHabitants() {
        return habitants;
    }

    public static Etat getEtat() {
        return etat;
    }

    public static List<Marchandise> getDansMagasinParFamille(FamillesMarchandises famille) {
        List<Marchandise> retour = new LinkedList<>();
        for(Boutique b : getBoutiques())
        {
            List<Marchandise> offre = b.getCatalogue().stream().map(um->um.getTypeMarchandise())
                    .filter(m->m.getFamilles().contains(famille)).collect(Collectors.toList());
            for(Marchandise m : offre)
            {
                if(retour.stream().filter(mr->m.identique(mr)).count()==0)
                {
                    retour.add(m);
                }
            }
        }
        return retour;
    }

    public void Update()
    {
        heure += dt;
        UpdateDt();
        if(heure >24.0)
        {
            jour +=1;
            heure =0;
            UpdateJournaliere();
        }
    }
    public void UpdateJournaliere()
    {
        for(JourListener jl : jourListeners)
        {
            jl.jourPasse(dt);
        }
    }
    public void UpdateDt()
    {
        for(DtListener jl : dtListeners)
        {
            jl.Update(dt);
        }
    }


    public static List<Residence> getResidences() {
        List<Residence> mt = getLieuxPhysiques().stream()
                .filter(res -> res instanceof Residence)
                .map(l -> (Residence)l)
                .collect(Collectors.toList());
        return mt;
    }



    public static List<Usine> getUsines() {
        List<Usine> mt = getEntreprises().stream()
                .filter(res -> res instanceof Usine)
                .map(l -> (Usine)l)
                .collect(Collectors.toList());
        return mt;
    }


    public static double getHeure() {
        return heure;
    }

    public static void setHeure(double heure) {
        Monde.heure = heure;
    }

    public static int getJour() {
        return jour;
    }

    public static void setJour(int jour) {
        Monde.jour = jour;
    }

    public static double getDt() {
        return dt;
    }

    public static void setDt(double dt) {
        Monde.dt = dt;
    }

    //penser a appeler apres l'ajout d'un lieu
    public static void calculerInfoChemins()
    {
        List<Place> placelieu = new LinkedList<>();
        HashMap<Place,LieuPhysique> lieuPlace_ = new HashMap<>();
        List<ArcPhysique> arcs = new LinkedList<>();
        for(LieuPhysique l : lieuxPhysiques)
        {
            placelieu.add(l.getPlace());
            lieuPlace_.put(l.getPlace(),l);
            for(LieuPhysique l2 : l.getAdjacents()) {
                ArcPhysique arc = new ArcPhysique(l.getPlace(),l2.getPlace() , 1);
                arcs.add(arc);
            }
        }
        for(LieuPhysique l : lieuxPhysiques)
        {
            InfoChemin ic = PathFinder.calculerDistances(arcs,l.getPlace());
            l.setInfoChemin(ic);
        }
        lieuPlace_ = lieuPlace_;
    }

    public static LieuPhysique selectionnerLieu(double x, double y)
    {
        List<LieuPhysique> contact = getLieuxPhysiques().stream().filter(lp->lp.getHitbox().contact(x,y))
                .collect(Collectors.toList());
        if(contact.size()>0)
        {
            return contact.stream().min(Comparator.comparingDouble(l->new Point2D.Double(x,y)
                    .distance(new Point2D.Double(l.getX(),l.getY())))).get();
        }
        else
        {
            return null;
        }
    }
    public static Habitant selectionnerHabitant(double x, double y)
    {
        List<Habitant> contact = getHabitants().stream().filter(lp->lp.getHitbox().contact(x,y))
                .collect(Collectors.toList());
        if(contact.size()>0)
        {
            return contact.stream().min(Comparator.comparingDouble(l->new Point2D.Double(x,y)
                    .distance(l.getPositionActuele()))).get();
        }
        else
        {
            return null;
        }
    }
}
