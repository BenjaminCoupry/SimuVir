package Global.SrcEconomie.Entreprises.Finance;

import Global.Monde;
import Global.SrcEconomie.*;
import Global.SrcEconomie.Entreprises.Entreprise;
import Global.SrcEconomie.Entreprises.Marchandise;
import Global.SrcEconomie.Entreprises.Poste;
import Global.SrcEconomie.Entreprises.Transport.*;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcVirus.Fonctions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Banque extends Entreprise implements DtListener, JourListener {


    HashMap<Monetaire, CompteBancaire> comptes;
    double tauxInteret;
    double frais;

    @Override
    public void Update(double dt)
    {
        super.Update(dt);
    }
    @Override
    public void jourPasse(double dt)
    {
        super.jourPasse(dt);

    }

    public void preleverFrais()
    {
        CompteBancaire compteBanque = getCompteBancaire();
        for(CompteBancaire cb : comptes.values())
        {
            compteBanque.prelever(cb,getFrais(),"Frais banquaires "+getNom());
        }
    }
    public void verserInterets()
    {
        for(CompteBancaire cb : comptes.values())
        {
            double montant = cb.getSomme();
            double interets = getEfficacite()*getTauxInteret()*montant;
            if(interets>0) {
                cb.crediter(interets,"Interets "+getNom());
            }
        }
    }

    public boolean peutSinscrire(Monetaire mon)
    {
        return !comptes.containsKey(mon);
    }
    public void sInscrire(Monetaire mon)
    {
        if(peutSinscrire(mon))
        {
            comptes.put(mon,mon.getCompteBancaire());
        }
    }

    @Override
    public void oublier(Habitant hab)
    {
        super.oublier(hab);
        comptes.remove(hab);
    }


    public double getFrais() {
        return frais;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }
}
