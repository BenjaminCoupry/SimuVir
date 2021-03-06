package Global.SrcEconomie.Entreprises.Industrie;

import java.util.List;

public class RecetteIndustrie {
    List<UsageMarchandise> consommation;
    List<UsageMarchandise> production;
    double tempsFabrication;

    public RecetteIndustrie(List<UsageMarchandise> consommation, List<UsageMarchandise> production, double tempsFabrication) {
        this.consommation = consommation;
        this.production = production;
        this.tempsFabrication = tempsFabrication;
    }


    public List<UsageMarchandise> getConsommation() {
        return consommation;
    }

    public void setConsommation(List<UsageMarchandise> consommation) {
        this.consommation = consommation;
    }

    public List<UsageMarchandise> getProduction() {
        return production;
    }

    public void setProduction(List<UsageMarchandise> production) {
        this.production = production;
    }

    public double getTempsFabrication() {
        return tempsFabrication;
    }

    public void setTempsFabrication(double tempsFabrication) {
        this.tempsFabrication = tempsFabrication;
    }
}
