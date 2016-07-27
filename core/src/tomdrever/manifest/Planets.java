package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.*;
import tomdrever.manifest.data.Planet;

public class Planets {
    public static int planetSize = 100;

    // REM - not actually a  planet type, returns a sort of planet-template
    private static Entity newBasicPlanet(Planet planet, float x, float y) {
        Entity newEmptyPlanet = new Entity();
        newEmptyPlanet.add(new RenderedComponent());
        PopulationComponent popComponent = new PopulationComponent(
                planet.initialPopulation, planet.growthRate, planet.maxPopulation);
        newEmptyPlanet.add(popComponent);
        newEmptyPlanet.add(new BoundsComponent(x, y, planetSize * planet.sizeMultiplier, planetSize * planet.sizeMultiplier));
        newEmptyPlanet.add(new TextComponent(popComponent.toString(),
                (BitmapFont) Resources.loadResource("PLANET_POPULATION_FONT").get()));
        newEmptyPlanet.add(new OnClickComponent(new OnClick() {
            @Override
            public void run(){
                System.out.println("planet clicked!");
                // TODO - implement clickable planets, with selection toggle
            }
        }));
        return newEmptyPlanet;
    }

    private static Entity newEmptyPlanet(Planet planet, float x, float y) {
        Entity planetEntity = newBasicPlanet(planet, x, y);
        planetEntity.add(new SpriteComponent((Texture) Resources.loadResource("PLANET_EMPTY_TEXTURE").get()));
        return planetEntity;
    }

    private static Entity newPlayerPlanet(Planet planet, float x, float y) {
        Entity planetEntity = newBasicPlanet(planet, x, y);
        planetEntity.add(new SpriteComponent((Texture) Resources.loadResource("PLANET_PLAYER_TEXTURE").get()));
        return planetEntity;
    }

    private static Entity newEnemyPlanet(Planet planet, float x, float y) {
        Entity planetEntity = newBasicPlanet(planet, x, y);
        planetEntity.add(new SpriteComponent((Texture) Resources.loadResource("PLANET_ENEMY_TEXTURE").get()));
        return planetEntity;
    }

    private static Entity newPassivePlanet(Planet planet, float x, float y) {
        Entity planetEntity = newBasicPlanet(planet, x, y);
        planetEntity.add(new SpriteComponent((Texture) Resources.loadResource("PLANET_PASSIVE_TEXTURE").get()));
        return planetEntity;
    }

    private static Entity newNomadicPlanet(Planet planet, float x, float y) {
        Entity planetEntity = newBasicPlanet(planet, x, y);
        planetEntity.add(new SpriteComponent((Texture) Resources.loadResource("PLANET_NOMADIC_TEXTURE").get()));
        return planetEntity;
    }

    public static Entity newPlanetEntity(Planet planet, float x, float y) {
        switch (planet.type) {
            case EMPTY:
                return newEmptyPlanet(planet, x, y);
            case PLAYER:
                return newPlayerPlanet(planet, x, y);
            case PASSIVE:
                return newPassivePlanet(planet, x, y);
            case ENEMY:
                return newEnemyPlanet(planet, x, y);
            case NOMADIC:
                return newNomadicPlanet(planet, x, y);
            default:
                return new Entity();
        }
    }
}
