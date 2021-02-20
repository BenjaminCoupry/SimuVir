package Global.Render;

import Global.SrcEconomie.Entreprises.Enseignement.Universite;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Vie.Habitant;

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
                Image picture = ImageIO.read(new File("picture.png"));
                return picture;
            }
            else if(tx instanceof LieuPhysique)
            {
                LieuPhysique tl = (LieuPhysique) tx;
                if(tl instanceof Universite)
                {
                    Universite u = (Universite) tl;
                    Image picture = ImageIO.read(new File("picture.png"));
                    return picture;
                }
                //TODO....
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
