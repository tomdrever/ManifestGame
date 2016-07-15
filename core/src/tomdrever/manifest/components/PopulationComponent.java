package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class PopulationComponent implements Component{
    public float population;
    public float maxPopulation;
    public float growthRate;

    public PopulationComponent(float population, float growthRate, float maxPopulation) {
        this.population = population;
        this.growthRate = growthRate;
        this.maxPopulation = maxPopulation;
    }

    public String toString(){
        return String.format("%.1f", population);
    }
}
