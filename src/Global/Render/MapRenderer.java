package Global.Render;

import Global.Editor.Architecte;
import Global.Monde;
import Global.SrcEconomie.ConstantesEco;
import Global.SrcEconomie.Hitboxes.LieuPhysique;
import Global.SrcEconomie.Vie.Habitant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class MapRenderer extends JPanel  implements MouseListener {
    double scale;
    double y0;
    double x0;
    double w ;
    double h ;

    public MapRenderer() {
        super(true);
        setPreferredSize(new Dimension(300,600));
        x0=0;
        y0=0;
        scale =0.3;
        Dimension size = getSize();
        w = size.getWidth();
        h = size.getHeight();
        this.addMouseListener(this);
    }

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
        drawElements(g2d);
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
        g2d.setStroke(new BasicStroke((int)(ConstantesEco.taille_route/scale)));
        g2d.setColor(Color.BLACK);
        for(LieuPhysique lpc : lp.getAdjacents())
        {
            Point2D finish = getCoordRender(lpc.getPoint());
            g2d.drawLine((int)start.getX(),(int)start.getY(),(int)finish.getX(),(int)finish.getY());
        }
    }
    public void drawLieuPhysique(LieuPhysique lp, Graphics2D g2d)
    {
        Point2D render = getCoordRender(lp.getPoint());
        if(dansChamp(render)) {
            Image tx = RessGetter.getTexture(lp);
            int w = (int)(lp.getHitbox().getLongueur()/scale);
            int h = (int)(lp.getHitbox().getLongueur()/scale);
            int x0 = (int)render.getX() - w/2;
            int y0 = (int)render.getY() - h/2;
            if(tx != null)
            {
                g2d.drawImage(tx,x0,y0,w,h,null);
                if(Architecte.selectionne == lp)
                {
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawOval(x0,y0,w,h);
                }
            }
        }
    }
    public void drawHabitant(Habitant ha, Graphics2D g2d)
    {
        System.out.println(ha.toString());
        Point2D render = getCoordRender(ha.getPositionActuele());
        if(dansChamp(render)) {
            Image tx = RessGetter.getTexture(ha);
            int w = (int)(ha.getHitbox().getLongueur()/scale);
            int h = (int)(ha.getHitbox().getLongueur()/scale);
            int x0 = (int)render.getX() - w/2;
            int y0 = (int)render.getY() - h/2;
            if(tx != null)
            {
                g2d.drawImage(tx,x0,y0,w,h,null);
                if(Architecte.selectionne == ha)
                {
                    g2d.drawOval(x0,y0,w,h);
                    Point2D nx = ha.getNext_pt();
                    if(nx != null) {
                        Point2D rendernx = getCoordRender(nx);
                        if (dansChamp(rendernx)) {
                            g2d.drawLine((int) render.getX(), (int) render.getY(), (int) rendernx.getX(), (int) rendernx.getY());
                        }
                    }
                }
            }
        }
    }
    public void drawElements(Graphics2D g2d)
    {
        for(LieuPhysique lp : Monde.getLieuxPhysiques())
        {
            drawConnexions(lp,g2d);
        }
        for(LieuPhysique lp : Monde.getLieuxPhysiques())
        {
            drawLieuPhysique(lp,g2d);
        }
        for(Habitant h : Monde.getHabitants())
        {
            drawHabitant(h,g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point2D.Double xr = getCoordReelles(e.getPoint());
        Architecte.clique(xr.getX(),xr.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
