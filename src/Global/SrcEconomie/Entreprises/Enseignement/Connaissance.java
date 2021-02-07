package Global.SrcEconomie.Entreprises.Enseignement;

import Global.SrcEconomie.Vie.Habitant;

import java.util.List;

public class Connaissance {
    TypeConnaissance typeConnaissance;
    int niveau;

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
}
