package tomdrever.manifest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.*;
import tomdrever.manifest.data.Planet;

import java.util.HashMap;

class Planets {
    private static Engine engine;

    private static Entity planetHighlightEntity;
    private static Entity planetSelectedHighlightEntity;

    static int planetSizeStandard = 100;

    private static Entity selectedPlanetEntity = null;

    private static final HashMap<Planet.Type, String> planetTypeTextureMap;
    static {
        planetTypeTextureMap = new HashMap<Planet.Type, String>();
        planetTypeTextureMap.put(Planet.Type.EMPTY, "PLANET_EMPTY_TEXTURE");
        planetTypeTextureMap.put(Planet.Type.PLAYER, "PLANET_PLAYER_TEXTURE");
        planetTypeTextureMap.put(Planet.Type.ENEMY, "PLANET_ENEMY_TEXTURE");
        planetTypeTextureMap.put(Planet.Type.PASSIVE, "PLANET_PASSIVE_TEXTURE");
        planetTypeTextureMap.put(Planet.Type.NOMADIC, "PLANET_NOMADIC_TEXTURE");
    }

    // REM - not actually a  planet type, returns a sort of planet-template, used in creating other
    // planet types
    static Entity newPlanetEntity(final Planet planet, final float x, final float y) {
        final Entity newPlanetEntity = new Entity();

        PopulationComponent popComponent = new PopulationComponent(
                planet.getInitialPopulation(), planet.getGrowthRate(), planet.getMaxPopulation());
        newPlanetEntity.add(popComponent);

        newPlanetEntity.add(new SpriteComponent((Texture) Resources.loadResource(planetTypeTextureMap.get(planet.getType())).get(),
                planetSizeStandard * planet.getSizeMultiplier(), planetSizeStandard * planet.getSizeMultiplier(), x, y));

        newPlanetEntity.add(new TextComponent(popComponent.toString(),
                (BitmapFont) Resources.loadResource("PLANET_POPULATION_FONT").get()));

        newPlanetEntity.add(new OnHoverComponent(new OnHoverComponent.OnHover() {
            @Override
            public void onMouseEnter() {

                SpriteComponent planetHighlightEntitySpriteComponent = new SpriteComponent(
                        planetSizeStandard * planet.getSizeMultiplier(),
                        planetSizeStandard * planet.getSizeMultiplier(),
                        x, y);

                // If there is a selected planet...
                if (selectedPlanetEntity != null) {
                    // If the player is hovering over an owned planet...
                    if (planet.getType() == Planet.Type.PLAYER) {
                        // Which is NOT the selected planet...
                        if (selectedPlanetEntity != newPlanetEntity) {
                            // Show green aura
                            planetHighlightEntitySpriteComponent.setTexture(
                                    (Texture) Resources.loadResource("PLANET_TARGET_PLAYER_HIGHLIGHT_TEXTURE").get());
                        }
                    } else { // If the player is hovering over an alien planet...
                        // The aura is red
                        planetHighlightEntitySpriteComponent.setTexture(
                                (Texture) Resources.loadResource("PLANET_TARGET_HIGHLIGHT_TEXTURE").get());
                    }
                } else { // If there is not a selected planet...
                    // And the player is hovering over an owned planet...
                    if (planet.getType() == Planet.Type.PLAYER) {
                        // Show white aura
                        planetHighlightEntitySpriteComponent.setTexture(
                                (Texture) Resources.loadResource("PLANET_SELECT_HIGHLIGHT_TEXTURE").get());
                    }
                }

                planetHighlightEntity.add(planetHighlightEntitySpriteComponent);
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
                    if (planet.getType() == Planet.Type.PLAYER) {
                        // Select planet
                        selectedPlanetEntity = newPlanetEntity;

                        SpriteComponent newPlanetSpriteComponent = newPlanetEntity.getComponent(SpriteComponent.class);

                        // Show selection highlight
                        planetSelectedHighlightEntity.add(new SpriteComponent(
                                (Texture) Resources.loadResource("PLANET_SELECT_HIGHLIGHT_TEXTURE").get(),
                                newPlanetSpriteComponent.getSize(), newPlanetSpriteComponent.getPosition()));
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

                        if (selectedPlanetEntity.getComponent(PopulationComponent.class).getPopulation() >= 2) {

                            // Launch fleet at it!
                            engine.addEntity(Fleets.newFleet(
                                    Math.round(selectedPlanetEntity.getComponent(PopulationComponent.class).getPopulation()) / 2,
                                    selectedPlanetEntity.getComponent(SpriteComponent.class).getPosition(),
                                    new Vector2(mousePosition.x, Gdx.graphics.getHeight() -  mousePosition.y)));

                            // Half planet's population, compensating for the interval timer's increases
                            selectedPlanetEntity.getComponent(PopulationComponent.class).setPopulation(
                                    (Math.round(selectedPlanetEntity.getComponent(PopulationComponent.class).getPopulation() / 2)) - 1);

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

    static void setEngine(Engine engine1) {
        engine = engine1;

        planetHighlightEntity = new Entity();
        engine.addEntity(planetHighlightEntity);

        planetSelectedHighlightEntity = new Entity();
        engine.addEntity(planetSelectedHighlightEntity);
    }
}
