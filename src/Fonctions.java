import java.util.Random;
import java.util.function.Function;

public class Fonctions {
    public static Random r = new Random();
    public static Function<Double,Double> getSigmoide(double x0, double tau, double A)
    {
        return x -> A/(1.0+Math.exp(-tau*(x-x0)));
    }
    public static Function<Double,Double> getGaussienne(double x0, double sigma, double A)
    {
        return x -> A*Math.exp(-Math.pow((x-x0)/sigma,2));
    }
    public static Function<Double,Double> getConstante(double A)
    {
        return x -> A;
    }
    public static Function<Double,Double> getLineaire(double x0,double x1,double y0,double y1)
    {
        return x -> ((x-x0)/(x1-x0))*(y1-y0)+y0;
    }
    public static Function<Double,Double> getCos(double x0,double x1,double y0,double y1)
    {
        return x -> {
            double kx = Math.min(1,Math.max(0,(x-x0)/(x1-x0)));
            double kcos = 0.5*(1.0-Math.cos(Math.PI*x));
            return kcos*(y1-y0)+y0;
        };
    }

}
