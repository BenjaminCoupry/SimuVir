package Global;

import Global.SrcEconomie.Entreprises.Commerce.Boutique;
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
}
