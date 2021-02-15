package Global.Render;

import Global.SrcEconomie.Hitboxes.LieuPhysique;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class MapRenderer extends JPanel {
    double scale;
    double y0;
    double x0;
    double w ;
    double h ;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        w = size.getWidth();
        h = size.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);
    }
    public boolean dansChamp(Point2D coordRender)
    {
        double x = coordRender.getX();
        double y = coordRender.getY();
        return (x>0 && x<w && y>0 && y<h);
    }
    public Point2D.Double getCoordRender(Point2D coordReelles)
    {
        double x = (coordReelles.getX()-x0)/scale;
        double y = (coordReelles.getY()-y0)/scale;
        return new Point2D.Double(x,y);
    }
    public Point2D.Double getCoordReelles(Point2D coordRender)
    {
        double x = (coordRender.getX()+x0)*scale;
        double y = (coordRender.getY()+y0)*scale;
        return new Point2D.Double(x,y);
    }
    public void drawConnexions(LieuPhysique lp, Graphics2D g2d)
    {
        Point2D start = getCoordRender(lp.getPoint());
        for(LieuPhysique lpc : lp.getAdjacents())
        {
            Point2D finish = getCoordRender(lpc.getPoint());
            g2d.drawLine((int)start.getX(),(int)start.getY(),(int)finish.getX(),(int)finish.getY());
        }
    }
    public void drawLieuPhysique(LieuPhysique lp, Graphics2D g2d)
    {
        drawConnexions(lp,g2d);
    }
}
