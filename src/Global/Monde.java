package Global;

import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.Entreprises.Industrie.Usine;
import Global.SrcEconomie.Habitant;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcEconomie.TypeMarchandise;
import Global.SrcVirus.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Monde {
    static List<Virus> virusExistants;
    static List<Stockage> stockages;
    static List<EntrepriseTransport> transporteurs;
    static List<Boutique> boutiques;
    static List<Residence> residences;
    static List<Universite> universites;
    static List<Usine> usines;
    static List<Poste> postes;
    static double heure;
    static int jour;
    static double dt;

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
        for(Stockage sto : stockages)
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
        return stockages;
    }

    public static void setStockages(List<Stockage> stockages) {
        Monde.stockages = stockages;
    }

    public static List<EntrepriseTransport> getTransporteurs() {
        return transporteurs;
    }

    public static void setTransporteurs(List<EntrepriseTransport> transporteurs) {
        Monde.transporteurs = transporteurs;
    }

    public static List<Boutique> getBoutiques() {
        return boutiques;
    }

    public static void setBoutiques(List<Boutique> boutiques) {
        Monde.boutiques = boutiques;
    }

    public static List<Poste> getPostes() {
        return postes;
    }

    public static void setPostes(List<Poste> postes) {
        Monde.postes = postes;
    }

    public static List<Universite> getUniversites() {
        return universites;
    }

    public static void setUniversites(List<Universite> universites) {
        Monde.universites = universites;
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
        return residences;
    }

    public static void setResidences(List<Residence> residences) {
        Monde.residences = residences;
    }

    public static List<Usine> getUsines() {
        return usines;
    }

    public static void setUsines(List<Usine> usines) {
        Monde.usines = usines;
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
    //TODO correspondaces lieux/pathfinding
}
