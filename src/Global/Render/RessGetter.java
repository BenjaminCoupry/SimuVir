package Global.Render;

import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Vie.Habitant;
import Global.SrcEconomie.Voierie.Route;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RessGetter {
    public static Image getTexture(Texturable tx)
    {
        try {

            if(tx instanceof Habitant)
            {
                Habitant th = (Habitant) tx;
                Image picture = ImageIO.read(new File("Ress/hab.png"));
                return picture;
            }
            else if(tx instanceof LieuPhysique)
            {
                LieuPhysique tl = (LieuPhysique) tx;
                Image picture;
                if(tl instanceof Route)
                {
                    picture = ImageIO.read(new File("Ress/route.png"));
                }
                else
                {
                    picture = ImageIO.read(new File("Ress/bat.png"));
                }
                return picture;
                //TODO....
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
