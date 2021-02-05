package Global.SrcEconomie.Entreprises.Transport;

import Global.Monde;
import Global.SrcEconomie.CompteBancaire;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Habitant;
import Global.SrcEconomie.TypeMarchandise;
import Global.SrcVirus.Fonctions;

import java.util.HashMap;
import java.util.List;

public class EntrepriseTransport extends Entreprise {

    List<Commande> commandes;
    HashMap<Habitant, Marchandise> inventairesLivreurs;
    HashMap<Habitant,OrdreTransport> livraisons;
    double tarif;

    public void traiterCommandes()
    {
        for(Poste p : getPostes())
        {
            if((!livraisons.containsKey(p.getOccupant())) || livraisons.get(p.getOccupant()) == null)
            {
                if(commandes.size()>0)
                {
                    Commande c = commandes.get(0);
                    List<Stockage> dispo = Monde.trouverDisponibilites(c.getTypeMarchandise(),TypeDisponibilite.USINE);
                    if(dispo.size()>0)
                    {
                        int choix = Fonctions.r.nextInt(dispo.size());
                        Stockage choisi = dispo.get(choix);
                        Marchandise march = choisi.fournir(c.typeMarchandise);
                        inventairesLivreurs.put(p.getOccupant(),march);
                        OrdreTransport ot = new OrdreTransport(choisi,c.getDestination(),c.getTypeMarchandise(),StatutLivraison.RECUPERATION);
                        livraisons.put(p.getOccupant(),ot);
                        commandes.remove(c);
                        if(choisi instanceof Entreprise) {
                            Entreprise source = (Entreprise) choisi;
                            c.getPaiement().payer(source.getCompteBancaire(), choisi.getPrix(c.getTypeMarchandise()),"Achat via "+getNom());
                        }
                    }
                }
            }
            if(livraisons.containsKey(p.getOccupant()))
            {
                OrdreTransport ot = livraisons.get(p.getOccupant());
                if(ot.getStatut().equals(StatutLivraison.FINIE))
                {
                    Marchandise m = inventairesLivreurs.get(p.getOccupant());
                    ot.getArrivee().stocker(m);
                    inventairesLivreurs.put(p.getOccupant(),null);
                    livraisons.put(p.getOccupant(),null);
                }
            }
        }
    }
    public void passerCommande(Stockage client, TypeMarchandise tm)
    {
        if(client instanceof Entreprise) {
            Entreprise eClient = (Entreprise)client;
            getCompteBancaire().prelever(eClient.getCompteBancaire(), tarif, "Livraison par " + getNom());
            Commande cmd = new Commande(client, tm, eClient.getCompteBancaire());
            commandes.add(cmd);
        }
    }


}
