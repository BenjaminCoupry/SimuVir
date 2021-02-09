package Global.SrcEconomie.Vie;

public class BesoinsHabitant {
    double nourriture;
    double loisir;
    double energie;
    Habitant hab;
    public BesoinsHabitant(Habitant hab)
    {
        nourriture = 100;
        loisir = 100;
        energie =100;
        this.hab=hab;
    }
    //TODO vieillir plus vite en fonction de la qualite de vie
    private double clamp(double x)
    {
        return Math.max(0,Math.min(100,x));
    }
    public void update(double dt, ModeActivite activite)
    {
        switch (activite)
        {
            case REPOS:
                nourriture = clamp(nourriture-0.2*dt);
                loisir = clamp(loisir+0.3*dt);
                energie = clamp(energie + 1.0*dt);
                break;
            case MANGER:
                nourriture = clamp(nourriture+1*dt);
                loisir = clamp(loisir+0.1*dt);
                energie = clamp(energie + 0.2*dt);
                break;
            case ATTENDRE:
                nourriture = clamp(nourriture-0.5*dt);
                loisir = clamp(loisir-0.2*dt);
                energie = clamp(energie - 0.2*dt);
                break;
            case ACHETER:
                nourriture = clamp(nourriture-0.5*dt);
                loisir = clamp(loisir+0.2*dt);
                energie = clamp(energie - 0.2*dt);
                break;
            case VISITER:
                nourriture = clamp(nourriture-0.5*dt);
                loisir = clamp(loisir+1*dt);
                energie = clamp(energie - 0.2*dt);
                break;
            case TRAVAILLER:
            case ETUDIER:
                nourriture = clamp(nourriture-0.9*dt);
                loisir = clamp(loisir-0.6*dt);
                energie = clamp(energie -1.0*dt);
                break;
        }
    }
    public boolean affame()
    {
        return nourriture<20;
    }
    public boolean fatigue()
    {
        return energie<20;
    }
    public boolean triste()
    {
        return loisir<20;
    }
}
