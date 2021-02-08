package Global.SrcEconomie.Entreprises;
import Global.SrcEconomie.*;
import Global.SrcEconomie.Entreprises.Finance.CompteBancaire;
import Global.SrcEconomie.Entreprises.Finance.Monetaire;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Vie.Habitant;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Entreprise extends LieuPhysique implements Monetaire, DtListener, JourListener {
    String nom;
    double efficacite;
    CompteBancaire compteBancaire;
    List<Poste> postes;

    public Entreprise(Hitbox hitbox, double tempsTraversee, double x, double y) {
        super(hitbox, tempsTraversee, x, y,ConstantesEco.coeffTransmissionEntreprise);
        efficacite = 0;
        this.nom = nom;
        postes = new LinkedList<>();
        compteBancaire = new CompteBancaire(0);
    }

    public void ajouterPoste(Poste p)
    {
        postes.add(p);
        p.setEntreprise(this);
    }

    public void Update(double dt)
    {
        efficacite = calculerEfficacite();

    }
    public double calculerEfficacite()
    {
        double efficacitePotentielle =0;
        double efficaciteReelle =0;
        for(Poste p : postes)
        {
            efficacitePotentielle += p.getImportance();
            if(p.getOccupant()!= null && getVisiteurs().contains(p.getOccupant())) {
                efficaciteReelle += p.importanceFournie();
            }
        }
        return efficaciteReelle/efficacitePotentielle;
    }
    public double payerSalaires()
    {
        double sommes = 0;
        for(Poste p : postes)
        {
            if(p.getOccupant() != null)
            {
                double salaire = p.getSalaire();
                sommes += salaire;
                CompteBancaire compteEmploye =  p.getOccupant().getCompteBancaire();
                compteBancaire.payer(compteEmploye,salaire,"Salaire "+nom);
            }
        }
        return sommes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Habitant> getEmployes() {
        List<Habitant> mt = getPostes().stream()
                    .filter(res -> res.getOccupant() != null)
                    .map(l -> l.getOccupant())
                    .collect(Collectors.toList());
            return mt;
    }


    public double getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }


    public List<Poste> getPostes() {
        return postes;
    }

    public void setPostes(List<Poste> postes) {
        this.postes = postes;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }
    public void supprimer()
    {
        super.supprimer();
        for(Poste p : getPostes())
        {
            p.renvoyer();
        }
    }
    public void oublier(Habitant hab)
    {
        for(Poste p : getPostes())
        {
            if(p.getOccupant() == hab)
            {
                p.renvoyer();
            }
        }
    }

    @Override
    public void jourPasse(double dt) {
        super.jourPasse(dt);
        payerSalaires();
    }
}
