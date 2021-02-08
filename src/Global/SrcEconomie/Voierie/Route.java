package Global.SrcEconomie.Voierie;

import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Hitboxes.Hitbox;
import Global.SrcEconomie.Hitboxes.LieuPhysique;

public class Route extends LieuPhysique {
    public Route(Hitbox hitbox, double tempsTraversee, double x, double y) {
        super(hitbox, tempsTraversee, x, y, ConstantesEco.coeffTransmissionRue);
    }
}
