package Global.SrcEconomie.Entreprises.Transport;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.DtListener;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Industrie.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.JourListener;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Entreprises.Industrie.TypeMarchandise;
import Global.SrcVirus.Fonctions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EntrepriseTransport extends Entreprise implements DtListener, JourListener {

    List<Commande> commandes;
    HashMap<Habitant, Marchandise> inventairesLivreurs;
    HashMap<Habitant,OrdreTransport> livraisons;
    double tarif;

    public EntrepriseTransport(Hitbox hitbox, double tempsTraversee, double x, double y, double tarif) {
        super(hitbox, tempsTraversee, x, y);
        commandes = new LinkedList<>();
        inventairesLivreurs = new HashMap<>();
        livraisons = new HashMap<>();
        this.tarif = tarif;
    }

    public void traiterCommandes()
    {
        for(Poste p : getPostes())
        {
            if(p.getOccupant()!= null && ((!livraisons.containsKey(p.getOccupant())) || livraisons.get(p.getOccupant()) == null))
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
                    else
                    {
                        //Delayer la commande
                        commandes.remove(0);
                        commandes.add(c);
                    }
                }
            }
        }
    }

    public void majLivraisons()
    {
        for(Poste p : getPostes())
        {
            if(p.getOccupant()!= null && livraisons.containsKey(p.getOccupant()))
            {
                OrdreTransport ot = livraisons.get(p.getOccupant());
                if(ot!=null) {
                    if (ot.getStatut().equals(StatutLivraison.FINIE)) {
                        Marchandise m = inventairesLivreurs.get(p.getOccupant());
                        ot.getArrivee().stocker(m);
                        inventairesLivreurs.put(p.getOccupant(), null);
                        livraisons.put(p.getOccupant(), null);
                    }
                    if (ot.getStatut().equals(StatutLivraison.LIVRAISON)) {
                        if (((LieuPhysique) ot.getArrivee()).getVisiteurs().contains(p.getOccupant())) {
                            ot.setStatut(StatutLivraison.FINIE);
                        }
                    }
                    if (ot.getStatut().equals(StatutLivraison.RECUPERATION)) {
                        if (((LieuPhysique) ot.getDepart()).getVisiteurs().contains(p.getOccupant())) {
                            ot.setStatut(StatutLivraison.LIVRAISON);
                        }
                    }
                }
            }
        }
    }
    public void passerCommande(Stockage client, TypeMarchandise tm)
    {
        if(client instanceof Entreprise) {
            Entreprise eClient = (Entreprise)client;
            double marge = tarif*(ConstantesEco.margeMax*getEfficacite());
            getCompteBancaire().prelever(eClient.getCompteBancaire(), tarif+marge, "Livraison par " + getNom());
            Commande cmd = new Commande(client, tm, eClient.getCompteBancaire());
            commandes.add(cmd);
        }
    }
    public LieuPhysique getObjectif(Habitant hab)
    {
        OrdreTransport ot = livraisons.get(hab);
        if(ot != null)
        {
            if(ot.getStatut().equals(StatutLivraison.RECUPERATION))
            {
                return (LieuPhysique)ot.getDepart();
            }
            if(ot.getStatut().equals(StatutLivraison.LIVRAISON))
            {
                return (LieuPhysique)ot.getArrivee();
            }
            if(ot.getStatut().equals(StatutLivraison.FINIE))
            {
                return this;
            }
        }
        return this;
    }
    public void Update(double dt)
    {
        super.Update(dt);
        traiterCommandes();
        majLivraisons();
    }
    public void oublier(Stockage stockage)
    {
        commandes = commandes.stream()
                .filter(c -> ! c.getDestination().equals(stockage))
                .collect(Collectors.toList());
        List<Habitant> toRemove = new LinkedList<>();
        for(Habitant h : livraisons.keySet())
        {
            OrdreTransport ot = livraisons.get(h);
            if(ot.getDepart().equals(stockage) || ot.getArrivee().equals(stockage))
            {
                toRemove.add(h);
            }
        }
        for(Habitant h: toRemove)
        {
            livraisons.remove(h);
            inventairesLivreurs.put(h,null);
        }
    }
    public void oublier(Habitant hab)
    {
        super.oublier(hab);
        inventairesLivreurs.remove(hab);
        livraisons.remove(hab);
    }


}
