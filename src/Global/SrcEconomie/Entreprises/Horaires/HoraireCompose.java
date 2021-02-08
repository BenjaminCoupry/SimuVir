package Global.SrcEconomie.Entreprises.Horaires;

import java.util.List;
import java.util.stream.Collectors;

public class HoraireCompose implements HoraireTravail{
    List<HoraireTravail> horaires;

    public HoraireCompose(List<HoraireTravail> horaires) {
        this.horaires = horaires;
    }

    @Override
    public boolean doitTravailler() {
        List<Boolean> compo = horaires.stream().map(h->h.doitTravailler()).collect(Collectors.toList());
        return compo.contains(true);
    }
}
