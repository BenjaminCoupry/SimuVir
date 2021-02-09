package Global.SrcEconomie.Logement;

import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Entreprises.Finance.CompteBancaire;
import Global.SrcEconomie.Entreprises.Finance.Monetaire;
import Global.SrcEconomie.Entreprises.Industrie.Marchandise;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.JourListener;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcEconomie.Hitboxes.LieuPhysique;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Residence extends LieuPhysique implements JourListener {
    String adresse;
    int places;
    double loyer;
    List<Marchandise> inventaire;
    CompteBancaire compteProprietaire;

    public Residence(Hitbox hitbox, double tempsTraversee, double x, double y, int places, double loyer,
                     String adresse, Monetaire proprietaire) {
        super(hitbox, tempsTraversee, x, y, ConstantesEco.coeffTransmissionLogement);
        inventaire = new LinkedList<>();
        this.places = places;
        this.loyer = loyer;
        this.adresse = adresse;
        this.compteProprietaire = proprietaire.getCompteBancaire();
    }

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

    @Override
    public void jourPasse(double dt) {
        payerLoyers();
    }
}
