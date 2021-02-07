package Global.SrcEconomie.Entreprises;

import Global.SrcEconomie.CompteBancaire;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Habitant;
import Global.SrcEconomie.LieuPhysique;

import java.util.List;
import java.util.stream.Collectors;

public class Entreprise extends LieuPhysique {
    String nom;
    double efficacite;
    CompteBancaire compteBancaire;
    List<Poste> postes;
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
}
