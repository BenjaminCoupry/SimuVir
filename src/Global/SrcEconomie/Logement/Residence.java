package Global.SrcEconomie.Logement;

import Global.Monde;
import Global.SrcEconomie.CompteBancaire;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Habitant;
import Global.SrcEconomie.LieuPhysique;
import Global.SrcVirus.Lieu;

import java.util.List;
import java.util.stream.Collectors;

public class Residence extends LieuPhysique {
    String adresse;
    int places;
    double loyer;
    CompteBancaire compteProprietaire;
    public boolean peutPostuler(Habitant hab)
    {
        List<Habitant> habitants = getHabitants();
        return places>habitants.size();
    }
    public void emmenager(Habitant hab)
    {
        if(peutPostuler(hab))
        {
            getHabitants().add(hab);
            hab.setResidence(this);
        }
    }
    public void payerLoyers()
    {
        List<Habitant> habitants = getHabitants();
        if(habitants.size()>0)
        {
            double loyerIndiv = loyer/habitants.size();
            for(Habitant hab : habitants)
            {
                compteProprietaire.prelever(hab.getCompteBancaire(),loyerIndiv,"Loyer "+adresse + " "+hab.getNom());
            }
        }
    }
    public void supprimer()
    {
        super.supprimer();
        List<Habitant> habitants = getHabitants();
        for(Habitant hab : habitants)
        {
            hab.setResidence(null);
        }
    }

    public List<Habitant> getHabitants() {
        List<Habitant> mt = Monde.getHabitants().stream()
                .filter(res -> res.getResidence() == this)
                .collect(Collectors.toList());
        return mt;
    }
}