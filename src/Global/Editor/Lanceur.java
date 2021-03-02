package Global.Editor;

import Global.Monde;
import Global.Render.MapRenderer;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Lanceur {
    public static void main(String[] args) {
        MapRenderer mr = new MapRenderer();
        JPanel actions = ActionMenu.getPannelActions();
        Timer clk = new Timer();
        JFrame frame = new JFrame("SimuVir");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.getContentPane().add(BorderLayout.CENTER, mr);
        frame.getContentPane().add(BorderLayout.SOUTH, actions);
        frame.setVisible(true);
        frame.pack();
        clk.schedule(new TimerTask() {
            @Override
            public void run() {
                Monde.Update();
                System.out.println(Monde.getHabitants());
                mr.repaint();
            }
        },0,1000);
    }
}
