package Global.SrcEconomie.Entreprises.Enseignement;

import Global.SrcEconomie.Vie.Habitant;

import java.util.List;
import java.util.Objects;

public class Connaissance {
    TypeConnaissance typeConnaissance;
    int niveau;

    public Connaissance(TypeConnaissance typeConnaissance, int niveau) {
        this.typeConnaissance = typeConnaissance;
        this.niveau = niveau;
    }

    public TypeConnaissance getTypeConnaissance() {
        return typeConnaissance;
    }

    public void setTypeConnaissance(TypeConnaissance typeConnaissance) {
        this.typeConnaissance = typeConnaissance;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public static boolean estApte(Habitant hab, List<Connaissance> connaissances)
    {
        for(Connaissance c: connaissances)
        {
            boolean connaissanceOk = false;
            for(Connaissance chab : hab.getConnaissances())
            {
                if(c.getTypeConnaissance().equals(chab.getTypeConnaissance()))
                {
                    if(chab.getNiveau()>=c.getNiveau())
                    {
                        connaissanceOk = true;
                        break;
                    }
                }
            }
            if(!connaissanceOk)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connaissance that = (Connaissance) o;
        return getNiveau() == that.getNiveau() && getTypeConnaissance() == that.getTypeConnaissance();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeConnaissance(), getNiveau());
    }
}
