package Global.SrcEconomie.Vie;

import Global.Editor.Selectionnable;
import Global.Monde;
import Global.SrcEconomie.*;
import Global.SrcEconomie.Entreprises.*;
import Global.SrcEconomie.Entreprises.Commerce.Boutique;
import Global.SrcEconomie.Entreprises.Enseignement.Connaissance;
import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Entreprises.Finance.Banque;
import Global.SrcEconomie.Entreprises.Finance.CompteBancaire;
import Global.SrcEconomie.Entreprises.Finance.Monetaire;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.FamillesMarchandises;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;

import Global.SrcEconomie.Entreprises.Transport.EntrepriseTransport;
import Global.SrcEconomie.Entreprises.Transport.Stockage;
import Global.SrcEconomie.Entreprises.Transport.TypeDisponibilite;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.Hitboxes.HitboxCercle;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Logement.Residence;
import Global.SrcVirus.Fonctions;
import Global.SrcVirus.Individu;
import java.awt.geom.Point2D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Habitant extends Individu implements Monetaire, JourListener,DtListener, Selectionnable {
    String prenom;
    String nomFamille;
    Residence residence;
    Universite universite;
    Banque banque;
    List<Connaissance> connaissances;

    Poste poste;
    CompteBancaire compteBancaire;
    LieuPhysique position;
    LieuPhysique next;
    LieuPhysique objectif;
    Marchandise volonteAchat;
    ModeActivite modeActiviteReel;
    ModeActivite modeActiviteVoulu;
    double avancementLieu;
    BesoinsHabitant besoins;
    Point2D next_pt;
    Point2D start_pt;
    double cooldown;
    Inventaire inventaire;
    Hitbox hitbox;


    public Habitant(Function<Double, Double> probaMortNaturelle, double age,String prenom,String nomFamille,LieuPhysique position) {
        super(probaMortNaturelle, age);
        this.prenom = prenom;
        this.position = position;
        this.nomFamille = nomFamille;
        hitbox = new HitboxCercle(0,0,ConstantesEco.taille_individu);
        residence = null;
        universite =null;
        banque = null;
        poste = null;
        cooldown = ConstantesEco.cooldown_actions;
        start_pt = position.getHitbox().getRandomPoint();
        next_pt = position.getHitbox().getRandomPoint();;
        modeActiviteReel = ModeActivite.ATTENDRE;
        modeActiviteVoulu = ModeActivite.ATTENDRE;
        avancementLieu =0;
        besoins = new BesoinsHabitant(this);
        volonteAchat = null;
        compteBancaire = new CompteBancaire(0);
        objectif = position;
        next = objectif;
        connaissances = new LinkedList<>();
        inventaire = new Inventaire();
    }
    public void apprendre(Connaissance c)
    {
        boolean update = false;
        Connaissance rm = null;
        for(Connaissance co : connaissances)
        {
            if(co.getTypeConnaissance().equals(c.getTypeConnaissance()))
            {
                update = true;
                if(co.getNiveau()<c.getNiveau())
                {
                    connaissances.add(c);
                    rm = co;
                }
            }
        }
        if(rm!=null)
        {
            connaissances.remove(rm);
        }
        if(!update)
        {
            connaissances.add(c);
        }
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return getPrenom()+" "+getNomFamille();
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public Hitbox getHitbox() {
        Point2D pact = getPositionActuele();
        hitbox.setX(pact.getX());
        hitbox.setY(pact.getY());
        return hitbox;
    }

    public Entreprise getTravail() {
        if(poste == null)
        {
            return null;
        }else
        {
            return poste.getEntreprise();
        }
    }

    public Universite getUniversite() {
        return universite;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
    }

    public List<Connaissance> getConnaissances() {
        return connaissances;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }


    public Point2D getPositionActuele()
    {
        double kav = avancementLieu/position.getTempsTraversee();
        double xres = start_pt.getX()*(1.0-kav) + next_pt.getX()*kav;
        double yres = start_pt.getY()*(1.0-kav) + next_pt.getY()*kav;
        return new Point2D.Double(xres,yres);
    }
    public void deplacer(double dt)
    {
        avancementLieu+= dt;
        if(avancementLieu> position.getTempsTraversee())
        {
            avancementLieu=0;
            entrerLieu(next);
            start_pt = next_pt;
            if(objectif != null) {
                if(objectif != position) {
                    List<LieuPhysique> next_ = position.getChemin(objectif);
                    if (next_.size() > 1) {
                        next = next_.get(1);
                    }
                }
                next_pt = next.getHitbox().getRandomPoint();
            }
        }
    }
    public void entrerLieu(LieuPhysique cible)
    {
        position.getVisiteurs().remove(this);
        position =cible;
        cible.getVisiteurs().add(this);
    }



    public ModeActivite choisirComportement()
    {
        //Besoin vital
        if(besoins.affame())
        {

            if (inventaire.peutEquiperFamille(FamillesMarchandises.ALIMENTAIRE) || inventaire.estEquipeFamille(FamillesMarchandises.ALIMENTAIRE)) {
                return ModeActivite.MANGER;
            }
            else
            {
                if (volonteAchat == null || !(volonteAchat.getFamilles().contains(FamillesMarchandises.ALIMENTAIRE))) {
                    List<Marchandise> nourriture = Monde.getDansMagasinParFamille(FamillesMarchandises.ALIMENTAIRE);
                    Marchandise tm = nourriture.get(Fonctions.r.nextInt(nourriture.size()));
                    volonteAchat = tm;
                }
                return ModeActivite.ACHETER;
            }
        }
        if(besoins.fatigue())
        {
            return ModeActivite.REPOS;
        }
        if(besoins.triste())
        {
            return ModeActivite.VISITER;
        }

        //besoins vitaux satisfaits

        if(getPoste()!= null && getPoste().getHoraires().doitTravailler())
        {
            return ModeActivite.TRAVAILLER;
        }else
        {
            if(getUniversite() != null && getUniversite().ouverte()) {
                return ModeActivite.ETUDIER;
            }else
            {
                return ModeActivite.VISITER;
            }
        }
    }
    public void comportement()
    {
        switch (modeActiviteVoulu)
        {
            case REPOS:
                rentrerDomicile();
                inventaire.deEquiperFamille(FamillesMarchandises.ALIMENTAIRE);
                break;
            case MANGER:
                manger();
                break;
            case ATTENDRE:
                attendre();
                inventaire.deEquiperFamille(FamillesMarchandises.ALIMENTAIRE);
                break;
            case VISITER:
                partirVisiter();
                inventaire.deEquiperFamille(FamillesMarchandises.ALIMENTAIRE);
                break;
            case TRAVAILLER:
                partirTravailler();
                inventaire.deEquiperFamille(FamillesMarchandises.ALIMENTAIRE);
                break;
            case ETUDIER:
                partirEtudier();
                inventaire.deEquiperFamille(FamillesMarchandises.ALIMENTAIRE);
                break;
            case ACHETER:
                partirAcheter();
                inventaire.deEquiperFamille(FamillesMarchandises.ALIMENTAIRE);
                break;
        }
    }

    public void partirTravailler()
    {

        Entreprise travail = getTravail();
        if(travail != null && getPoste().getHoraires().doitTravailler()) {
            modeActiviteReel = ModeActivite.TRAVAILLER;
            if(travail instanceof EntrepriseTransport)
            {
                objectif = ((EntrepriseTransport) travail).getObjectif(this);
            }
            else {
                objectif = travail;
            }
        }else
        {
            attendre();
        }

    }
    public void rentrerDomicile()
    {
        if(residence != null) {
            modeActiviteReel = ModeActivite.REPOS;
            objectif = residence;
        }else
        {
            attendre();
        }
    }
    public void partirEtudier()
    {
        if(universite != null) {
            modeActiviteReel = ModeActivite.ETUDIER;
            objectif = universite;
        }else
        {
            attendre();
        }
    }
    public void partirAcheter()
    {

        if(volonteAchat != null)
        {
            modeActiviteReel = ModeActivite.ACHETER;
            List<Stockage> dispo = Monde.trouverDisponibilites(volonteAchat, TypeDisponibilite.MAGASIN);
            if(dispo.contains(position))
            {
                //Dans le magasin
                objectif = position;
                Boutique bt = (Boutique) position;
                bt.vendre(volonteAchat,this);
                volonteAchat = null;
            }
            else if(dispo.size()>0)
            {
                //Il existe un magasin
                if(!dispo.contains(objectif)) {
                    //trouver la destination
                    Boutique bt = (Boutique) dispo.get(Fonctions.r.nextInt(dispo.size()));
                    objectif = bt;
                }
            }
            else
            {
                //Pas de magasin
                attendre();
                volonteAchat = null;
            }
        }
        else
        {
            partirVisiter();
        }
    }
    public void partirVisiter()
    {
        List<LieuPhysique> lieuxPhysiques = Monde.getLieuxPhysiques();
        LieuPhysique lp = lieuxPhysiques.get(Fonctions.r.nextInt(lieuxPhysiques.size()));
        objectif = lp;
        modeActiviteReel = ModeActivite.VISITER;
    }
    public void attendre()
    {
        objectif = position;
        modeActiviteReel = ModeActivite.ATTENDRE;
    }
    public void manger()
    {
        if(!inventaire.estEquipeFamille(FamillesMarchandises.ALIMENTAIRE)) {
            inventaire.equiperFamille(FamillesMarchandises.ALIMENTAIRE);
        }
        if(inventaire.estEquipeFamille(FamillesMarchandises.ALIMENTAIRE)) {
            if (residence != null) {
                modeActiviteReel = ModeActivite.MANGER;
                objectif = residence;
            } else {
                modeActiviteReel = ModeActivite.MANGER;
                objectif = position;
            }
        }
        else
        {
            attendre();
        }
    }






    @Override
    public void Update(double dt)
    {
        super.Update(dt);
        if(!isMort()) {
            cooldown-=dt;
            if(cooldown<0)
            {
                cooldown = ConstantesEco.cooldown_actions;
                modeActiviteVoulu = choisirComportement();
            }
            comportement();
            inventaire.userEquipementPorte(dt);
            deplacer(dt);
            besoins.update(dt, modeActiviteReel);
        }
        if(isMort())
        {
            supprimer();
        }
    }


    public void trouverAffectations()
    {
        if(residence==null)
        {
            List<Residence> res = Monde.trouverResidencesPossibles(this);
            if(res.size()>0) {
                Residence choisie;
                if(poste == null) {
                    choisie = res.get(Fonctions.r.nextInt(res.size()));
                }else
                {
                    choisie = res.stream().min(Comparator.
                            comparingDouble(x -> x.getPoint().distance(poste.getEntreprise().getPoint()))).get();
                }
                choisie.emmenager(this);
            }
        }
        if(poste==null || getTravail() == null)
        {
            List<Poste> res = Monde.trouverPostesPossibles(this);
            if(res.size()>0) {
                Poste choisi = res.stream().max(Comparator.comparingDouble(x -> x.getSalaire())).get();
                choisi.recruter(this);
            }
        }
        if(universite==null)
        {
            List<Universite> res = Monde.trouverUniversitesPossibles(this);
            if(res.size()>0) {
                Universite chosie = res.get(Fonctions.r.nextInt(res.size()));
                chosie.inscrire(this);
            }
        }
        if(banque==null)
        {
            List<Banque> res = Monde.trouverBanquesPossibles(this);
            if(res.size()>0) {
                Banque chosie = res.get(Fonctions.r.nextInt(res.size()));
                chosie.sInscrire(this);
            }
        }
    }
    public void supprimer()
    {
        if(poste != null)
        {
            Entreprise e = poste.getEntreprise();
            if(e!=null)
            {
                e.oublier(this);
            }
        }
        if(universite != null)
        {
            universite.oublier(this);
        }
        if(banque != null)
        {
            banque.oublier(this);
        }
        Monde.getHabitants().remove(this);
    }

    @Override
    public void jourPasse(double dt) {
        if(!isMort()) {
            trouverAffectations();
        }
    }

    public String getNomFamille() {
        return nomFamille;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }
}


//TODO Entreprises de services
