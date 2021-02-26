package Global.Editor;

import javax.swing.*;
import java.awt.*;

public class ActionMenu {
    public static JPanel getPannelActions()
    {
        JPanel actions = new JPanel();
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.TRAILING);
        actions.setLayout(fl);
        Button bSuppr = new Button("Supprimer");
        bSuppr.addActionListener(a->Architecte.passerMode(EditMode.SUPPRIMER));
        actions.add(bSuppr);
        Button broute = new Button("Tirer Route");
        broute.addActionListener(a->Architecte.passerMode(EditMode.ROUTE));
        actions.add(broute);
        Button bVie = new Button("Relancer");
        bVie.addActionListener(a->Architecte.passerMode(EditMode.VIE));
        actions.add(bVie);
        Button bHab = new Button("Placer Habitant");
        bHab.addActionListener(a->Architecte.passerMode(EditMode.PLACER_HABITANT));
        actions.add(bHab);
        Button bBat = new Button("Placer Batiment");
        bBat.addActionListener(a->Architecte.passerMode(EditMode.PLACER_BATIMENT));
        actions.add(bBat);
        return actions;
    }
}
