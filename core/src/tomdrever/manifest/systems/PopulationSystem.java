package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import tomdrever.manifest.components.PopulationComponent;
import tomdrever.manifest.components.TextComponent;

public class PopulationSystem extends IntervalIteratingSystem {
    private ComponentMapper<PopulationComponent> populationComponentMap = ComponentMapper.getFor(PopulationComponent.class);
    private ComponentMapper<TextComponent> textComponentMap = ComponentMapper.getFor(TextComponent.class);

    public PopulationSystem() {
        // Interval - 1 1/2 secs
        super(Family.all(PopulationComponent.class, TextComponent.class).get(), 1f);
    }

    @Override
    protected void processEntity(Entity entity) {
        if (populationComponentMap.get(entity).population < populationComponentMap.get(entity).maxPopulation) {
            populationComponentMap.get(entity).population += populationComponentMap.get(entity).growthRate;
            textComponentMap.get(entity).text = populationComponentMap.get(entity).toString();
        }
    }
}
