package Global.SrcEconomie.Vie;

import Global.SrcEconomie.Entreprises.Industrie.Marchandises.FamillesMarchandises;
import Global.SrcEconomie.Entreprises.Industrie.Marchandises.Marchandise;
import Global.SrcVirus.Fonctions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventaire {
    public Inventaire ()
    {
        inventaire = new LinkedList<>();
        inventaireEquipe = new LinkedList<>();
    }
    List<Marchandise> inventaire;
    List<Marchandise> inventaireEquipe;
    public void userEquipementPorte(double dt)
    {
        List<Marchandise> detruit = new LinkedList<>();
        for(Marchandise m : inventaireEquipe)
        {
            if(!m.user(dt))
            {
                detruit.add(m);
            }
        }
        for(Marchandise m : detruit)
        {
            inventaireEquipe.remove(m);
        }
    }

    private List<Marchandise> getMarchandiseFamille(FamillesMarchandises fm, List<Marchandise> source)
    {
        List<Marchandise> possib = source.stream().filter(m->m.getFamilles().contains(fm))
                .collect(Collectors.toList());
        return possib;
    }
    public void equiperFamille(FamillesMarchandises fm)
    {
        List<Marchandise> possib =getMarchandiseFamille(fm,inventaire);
        if(possib.size()>0) {
            Marchandise choix = possib.get(Fonctions.r.nextInt(possib.size()));
            inventaire.remove(choix);
            inventaireEquipe.add(choix);
        }
    }

    public boolean estEquipeFamille(FamillesMarchandises fm)
    {
        long possib = getMarchandiseFamille(fm,inventaireEquipe).size();
        return possib >0;
    }

    public boolean peutEquiperFamille(FamillesMarchandises fm)
    {
        long possib = getMarchandiseFamille(fm,inventaire).size();
        return possib >0;
    }

    public void deEquiperFamille(FamillesMarchandises fm)
    {
        List<Marchandise> possib = getMarchandiseFamille(fm,inventaireEquipe);

        inventaireEquipe.removeAll(possib);
        inventaire.addAll(possib);
    }
    public void donner(Marchandise m)
    {
        inventaire.add(m);
    }
}
