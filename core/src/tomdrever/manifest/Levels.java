package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.data.Level;
import tomdrever.manifest.data.Planet;

import java.util.ArrayList;
import java.util.Map;

class Levels {
    private Map<String, Level> levelMap;

    private Levels() { }

    static Levels loadLevels() {
        Levels levels = new Levels();

        Gson gson = new GsonBuilder().create();
        Level[] loadedLevels = gson.fromJson((String) Resources.loadResource("LEVELS_TEXT").get(), Level[].class);

        Map<String, Level> loadedLevelsDictionary = new LinkedTreeMap<String, Level>() ;
        for (Level level: loadedLevels) {
            loadedLevelsDictionary.put(level.getName(), level);
        }

        levels.levelMap = loadedLevelsDictionary;

        return levels;
    }

    Entity[] getLevel(String name) throws Exception {
        ArrayList<Entity> entities = new ArrayList();

        Level level = levelMap.get(name);

        if (level == null) {
            throw new LevelNotFoundException(String.format("No level with name: %s found", name));
        }

        // Padding is applied before and after each planet

        // Calculate y positions (positions for each row)
        int yPlanetCount = level.getPlanets().length;
        int yPadding = yPlanetCount == 1 ? 0 : 50 - (10 * yPlanetCount);
        int y = (Gdx.graphics.getHeight() / 2) +
                (((yPlanetCount - 1) * (Planets.planetSizeStandard)) / 2)
                    + ((yPlanetCount) * yPadding);

        for (Planet[] planetRow : level.getPlanets()) {

            // Calculate x positions (positions for each planet in row)
            int xPlanetCount = planetRow.length;
            int xPadding = xPlanetCount == 1 ? 0 : 75 - (15 * xPlanetCount);
            int x = (Gdx.graphics.getWidth() / 2) -
                    ((((xPlanetCount - 1) * (Planets.planetSizeStandard)) / 2)
                            + ((xPlanetCount) * xPadding));

            y -= yPadding;

            for (Planet planet: planetRow) {
                x += xPadding;

                entities.add(Planets.newPlanetEntity(planet,
                        x - ((Planets.planetSizeStandard * planet.getSizeMultiplier()) / 2),
                        y - ((Planets.planetSizeStandard * planet.getSizeMultiplier()) / 2)));

                x += xPadding;
                x += Planets.planetSizeStandard;
            }

            y -= yPadding;
            y -= Planets.planetSizeStandard;
        }

        return entities.toArray(new Entity[]{});
    }

    public int getLevelCount() {
        return levelMap.size();
    }

    private class LevelNotFoundException extends Exception {
        LevelNotFoundException(String message) {
            super(message);
        }
    }
}
