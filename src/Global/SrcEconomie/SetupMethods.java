package Global.SrcEconomie;

import Global.SrcEconomie.Entreprises.FamillesMarchandises;
import Global.SrcEconomie.Entreprises.Marchandises;
import Global.SrcEconomie.Entreprises.TypeMarchandise;

import java.util.LinkedList;
import java.util.List;

public final class SetupMethods {
    public static void setupTypeMarchandises()
    {
        List<FamillesMarchandises> tags = new LinkedList<>();
        tags.add(FamillesMarchandises.INDUSTRIEL);
        TypeMarchandise.register(Marchandises.ALIMENTS_CRUS,tags,1);


        tags = new LinkedList<>();
        tags.add(FamillesMarchandises.ALIMENTAIRE);
        TypeMarchandise.register(Marchandises.REPAS,tags,5);

    }
}
