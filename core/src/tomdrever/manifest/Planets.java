package tomdrever.manifest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.*;
import tomdrever.manifest.data.Planet;

class Planets {
    private static Engine engine;

    private static Entity planetHighlightEntity;
    private static Entity planetSelectedHighlightEntity;

    static int planetSizeStandard = 100;

    private static Entity selectedPlanetEntity = null;

    // REM - not actually a  planet type, returns a sort of planet-template, used in creating other
    // planet types
    private static Entity newBasicPlanet(final Planet planet, final float x, final float y) {
        final Entity newPlanetEntity = new Entity();

        newPlanetEntity.add(new RenderedComponent());

        PopulationComponent popComponent = new PopulationComponent(
                planet.initialPopulation, planet.growthRate, planet.maxPopulation);
        newPlanetEntity.add(popComponent);

        newPlanetEntity.add(new BoundsComponent(x, y,
                planetSizeStandard * planet.sizeMultiplier, planetSizeStandard * planet.sizeMultiplier));

        newPlanetEntity.add(new TextComponent(popComponent.toString(),
                (BitmapFont) Resources.loadResource("PLANET_POPULATION_FONT").get()));

        newPlanetEntity.add(new OnHoverComponent(new OnHoverComponent.OnHover() {
            @Override
            public void onMouseEnter() {

                if (planet.type == Planet.Type.PLAYER) {
                    planetHighlightEntity.add(new SpriteComponent(
                            (Texture) Resources.loadResource("PLANET_SELECT_HIGHLIGHT_TEXTURE").get()));
                } else if (selectedPlanetEntity != null){
                    planetHighlightEntity.add(new SpriteComponent(
                            (Texture) Resources.loadResource("PLANET_TARGET_HIGHLIGHT_TEXTURE").get()));
                }

                planetHighlightEntity.add(new BoundsComponent(
                                x, y,
                                planetSizeStandard * planet.sizeMultiplier, planetSizeStandard * planet.sizeMultiplier));

                planetHighlightEntity.add(new RenderedComponent());
            }

            @Override
            public void onMouseExit() {
                planetHighlightEntity.removeAll();
            }
        }));

        newPlanetEntity.add(new OnClickComponent(new OnClickComponent.OnClick() {
            @Override
            public void run(Vector2 mousePosition) {

                // If there is no current selected planet...
                if (selectedPlanetEntity == null) {
                    if (planet.type == Planet.Type.PLAYER) {
                        // Select planet
                        selectedPlanetEntity = newPlanetEntity;

                        // Show selection highlight
                        planetSelectedHighlightEntity.add(new RenderedComponent());
                        planetSelectedHighlightEntity.add(new SpriteComponent(
                                (Texture) Resources.loadResource("PLANET_SELECT_HIGHLIGHT_TEXTURE").get()
                        ));
                        Rectangle planetBounds = newPlanetEntity.getComponent(BoundsComponent.class).getBounds();
                        planetSelectedHighlightEntity.add(new BoundsComponent(
                                planetBounds.x, planetBounds.y,
                                planetBounds.width, planetBounds.height));
                    }
                } else { // If there is a selected planet...

                    // If the player is clicking on the selected planet...
                    if (selectedPlanetEntity == newPlanetEntity) {
                        // Hide selection highlight
                        planetSelectedHighlightEntity.removeAll();
                        planetHighlightEntity.removeAll();

                        // Deselect the selected planet.
                        selectedPlanetEntity = null;

                    } else { // If the planet clicked is not selected...

                        if (selectedPlanetEntity.getComponent(PopulationComponent.class).population >= 2) {

                            // Launch fleet at it!
                            engine.addEntity(Fleets.newFleet(
                                    Math.round(selectedPlanetEntity.getComponent(PopulationComponent.class).population) / 2,
                                    selectedPlanetEntity.getComponent(BoundsComponent.class).getPosition(),
                                    new Vector2(mousePosition.x, Gdx.graphics.getHeight() - mousePosition.y)));

                            // Half planet's population, compensating for the interval timer's increases
                            selectedPlanetEntity.getComponent(PopulationComponent.class).population =
                                    (Math.round(selectedPlanetEntity.getComponent(PopulationComponent.class).population / 2)) - 1;

                        }

                        // Hide selection highlight
                        planetSelectedHighlightEntity.removeAll();
                        planetHighlightEntity.removeAll();

                        // Then deselect the selected planet
                        selectedPlanetEntity = null;
                    }
                }
            }
        }));

        return newPlanetEntity;
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

    static Entity newPlanetEntity(Planet planet, float x, float y) {
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

    static void setEngine(Engine engine1) {
        engine = engine1;

        planetHighlightEntity = new Entity();
        engine.addEntity(planetHighlightEntity);

        planetSelectedHighlightEntity = new Entity();
        engine.addEntity(planetSelectedHighlightEntity);
    }
}
