package SrcVirus;

import java.util.Random;
import java.util.function.Function;

public class Fonctions {
    public static int UID=0;
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
    public static Function<Double,Double> getAlea()
    {
        return x -> r.nextDouble();
    }
    public static Function<Double,Double> getAleaFixe(int nbPoints,double x0,double x1,double y0,double y1)
    {
        double[] valsFixes = new double[nbPoints];
        for(int i=0;i<nbPoints;i++)
        {
            valsFixes[i] = r.nextDouble()*(y1-y0)+y0;
        }
        return x -> {
            double kx = Math.min(1,Math.max(0,(x-x0)/(x1-x0)))*(nbPoints-1);
            int i0 = (int)Math.max(0,Math.min(Math.floor(kx),nbPoints-1));
            int i1 = (int)Math.max(0,Math.min(Math.ceil(kx),nbPoints-1));
            double k0 = 1.0-(kx-i0);
            return k0*valsFixes[i0] + (1.0-k0)*valsFixes[i1];
        };
    }
    public static Function<Double,Double> getCos(double x0,double x1,double y0,double y1)
    {
        return x -> {
            double kx = Math.min(1,Math.max(0,(x-x0)/(x1-x0)));
            double kcos = 0.5*(1.0-Math.cos(Math.PI*x));
            return kcos*(y1-y0)+y0;
        };
    }
    public static Function<Double,Double> kMult(double k,Function<Double,Double> f)
    {
        return x -> k*f.apply(x);
    }
    public static Function<Double,Double> add(Function<Double,Double> f1 , Function<Double,Double> f2)
    {
        return x -> f1.apply(x)+f2.apply(x);
    }
    public static String getUID()
    {
        UID++;
        return "_"+UID;

    }


    public static int poisson(double lambda)
    {
        int k=0;
        double p=1;
        double el = Math.exp(-lambda);
        while(p>el)
        {
            p = p*r.nextDouble();
        }
        return k-1;
    }

}
