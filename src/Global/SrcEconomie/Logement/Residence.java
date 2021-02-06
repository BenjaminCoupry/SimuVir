package Global.SrcEconomie.Logement;

import Global.SrcEconomie.CompteBancaire;
import Global.SrcEconomie.Habitant;
import Global.SrcEconomie.LieuPhysique;
import Global.SrcVirus.Lieu;

import java.util.List;

public class Residence extends LieuPhysique {
    String adresse;
    List<Habitant> habitants;
    int places;
    double loyer;
    CompteBancaire compteProprietaire;
    public boolean peutPostuler(Habitant hab)
    {
        return places>habitants.size();
    }
    public void emmenager(Habitant hab)
    {
        if(peutPostuler(hab))
        {
            habitants.add(hab);
            hab.setResidence(this);
        }
    }
    public void payerLoyers()
    {
        if(habitants.size()>0)
        {
            double loyerIndiv = loyer/habitants.size();
            for(Habitant hab : habitants)
            {
                compteProprietaire.prelever(hab.getCompteBancaire(),loyerIndiv,"Loyer "+adresse + " "+hab.getNom());
            }
        }
    }

}
