package Global;

import java.util.stream.Collectors;

public class Statistiques {
    //TODO completer
    public static int getNombreHabitants()
    {
        return Monde.getHabitants().size();
    }
    public static double getMasseMonetaire()
    {
        return Monde.getMonetaires().stream().map(m->m.getCompteBancaire().getSomme()).collect(Collectors.summingDouble(i->i));
    }
    public static double getArgentPositif()
    {
        return Monde.getMonetaires().stream().map(m->m.getCompteBancaire().getSomme())
                .filter(d->d>0)
                .collect(Collectors.summingDouble(i->i));
    }
    public static double getArgentNegatif()
    {
        return Monde.getMonetaires().stream().map(m->m.getCompteBancaire().getSomme())
                .filter(d->d<0)
                .collect(Collectors.summingDouble(i->i));
    }
}
