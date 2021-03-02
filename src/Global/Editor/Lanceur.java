package Global.Editor;

import Global.Monde;
import Global.Render.MapRenderer;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Lanceur {
    static MapRenderer mr = new MapRenderer();
    static JPanel actions = ActionMenu.getPannelActions();
    static Timer clk = new Timer();
    public static void main(String[] args) {
        JFrame frame = new JFrame("SimuVir");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.getContentPane().add(BorderLayout.CENTER, mr);
        frame.getContentPane().add(BorderLayout.SOUTH, actions);
        frame.setVisible(true);
        frame.pack();

    }
    public static void pause() {
        clk.cancel();
    }
    public static void resume() {
        clk = new Timer();
        clk.schedule(new TimerTask() {
            @Override
            public void run() {
                Monde.Update();
                System.out.println(Monde.getHabitants());
                mr.repaint();
            }
        },0,100);
    }
    public static MapRenderer getMr() {
        return mr;
    }

    public static JPanel getActions() {
        return actions;
    }

}
