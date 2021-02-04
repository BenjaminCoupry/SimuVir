package SrcEconomie;

import SrcVirus.Lieu;

import java.util.List;

public class Entreprise extends Lieu {
    String nom;
    List<Habitant> employes;
    double efficacite;
    double fondsMonetaires;
    List<Poste> postes;
    public void Update(double dt)
    {
        payerSalaires();
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
    public void payerSalaires()
    {
        for(Poste p : postes)
        {
            if(p.getOccupant() != null)
            {
                double salaire = p.getSalaire();
                p.getOccupant().setFondsMonetaires(p.getOccupant().getFondsMonetaires()+salaire);
                fondsMonetaires -= salaire;
            }
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Habitant> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Habitant> employes) {
        this.employes = employes;
    }

    public double getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }

    public double getFondsMonetaires() {
        return fondsMonetaires;
    }

    public void setFondsMonetaires(double fondsMonetaires) {
        this.fondsMonetaires = fondsMonetaires;
    }

    public List<Poste> getPostes() {
        return postes;
    }

    public void setPostes(List<Poste> postes) {
        this.postes = postes;
    }
}
