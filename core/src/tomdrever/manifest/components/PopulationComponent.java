package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class PopulationComponent implements Component{
    private float population;

    public float getPopulation() {
        return population;
    }

    public void setPopulation(float population) {
        this.population = population;
    }

    public void increasePopulation(float amount) {
        this.population += amount;
    }


    private float maxPopulation;

    public float getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(float maxPopulation) {
        this.maxPopulation = maxPopulation;
    }


    private float growthRate;

    public float getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(float growthRate) {
        this.growthRate = growthRate;
    }

    public PopulationComponent(float population, float growthRate, float maxPopulation) {
        this.population = population;
        this.growthRate = growthRate;
        this.maxPopulation = maxPopulation;
    }

    @Override
    public String toString(){
        return String.format("%.1f", population);
    }
}
