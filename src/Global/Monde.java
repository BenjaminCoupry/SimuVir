package Global;

import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.Entreprises.Industrie.Usine;
import Global.SrcEconomie.TypeMarchandise;
import Global.SrcVirus.Virus;

import java.util.ArrayList;
import java.util.List;

public class Monde {
    static List<Virus> virusExistants;
    static List<Stockage> stockages;
    static List<EntrepriseTransport> transporteurs;
    static List<Boutique> boutiques;
    static List<Poste> postes;

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
}
