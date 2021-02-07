package Global;

import Global.SrcEconomie.*;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.Entreprises.Industrie.Usine;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcVirus.Virus;
import PathFinding.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Monde {
    static List<Virus> virusExistants;
    static List<LieuPhysique> lieuxPhysiques;
    static HashMap<Place,LieuPhysique> lieuPlace;
    static List<Habitant> habitants;
    static double heure;
    static int jour;
    static double dt;
    static Etat etat;

    public static List<Virus> getVirusExistants() {
        return virusExistants;
    }

    public static void setVirusExistants(List<Virus> virusExistantsNew) {
        virusExistants = virusExistantsNew;
    }
    public static void referencerVirus(Virus virus) {
        if(!virusExistants.contains(virus)) {
            virusExistants.add(virus);
        }
    }

    public static List<Stockage> trouverDisponibilites(TypeMarchandise tm, TypeDisponibilite dispo)
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

    public static List<Entreprise> getEntreprises() {
        List<Entreprise> mt = getLieuxPhysiques().stream()
                .filter(res -> res instanceof Entreprise)
                .map(l -> (Entreprise)l)
                .collect(Collectors.toList());
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

    public void Update()
    {
        heure += dt;
        UpdateElements();
        if(heure >1.0)
        {
            jour +=1;
            heure -= 1.0;
            UpdateJournaliere();
        }
    }
    public void UpdateJournaliere()
    {
        PasserCommandesStockages();
    }
    public void UpdateElements()
    {
        //TODO update elements chaque dt
    }
    public void PasserCommandesStockages()
    {
        for(Stockage st : getStockages())
        {
            st.passerCommandes();
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
}
