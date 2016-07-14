package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import tomdrever.manifest.assets.Assets;
import tomdrever.manifest.data.Level;
import tomdrever.manifest.data.Planet;

import java.util.ArrayList;
import java.util.Map;

public class Levels {
    private Map<String, Level> levels;

    private Levels() { }

    public static Levels load() {
        Levels levels = new Levels();

        Gson gson = new GsonBuilder().create();
        Level[] loadedLevels = gson.fromJson((String) Assets.getAsset("LEVELS_TEXT").get(), Level[].class);

        Map<String, Level> loadedLevelsDict = new LinkedTreeMap<String, Level>() ;
        for (Level level: loadedLevels) {
            loadedLevelsDict.put(level.name, level);
        }

        levels.levels = loadedLevelsDict;

        return levels;
    }

    public Entity[] getLevel(String name) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        Level level = levels.get(name);

        int yPlanetCount = level.planets.length;
        int yPadding = yPlanetCount == 1 ? 0 : 50 - (10 * yPlanetCount);
        int y = (Gdx.graphics.getHeight() / 2) +
                (((yPlanetCount - 1) * (Planets.planetSize)) / 2)
                    + ((yPlanetCount) * yPadding);

        for (Planet[] planetRow : level.planets) {
            int xPlanetCount = planetRow.length;
            int xPadding = xPlanetCount == 1 ? 0 : 75 - (15 * xPlanetCount);
            int x = (Gdx.graphics.getWidth() / 2) -
                    ((((xPlanetCount - 1) * (Planets.planetSize)) / 2)
                            + ((xPlanetCount) * xPadding));

            y -= yPadding;

            for (Planet planet: planetRow) {
                x += xPadding;
                // TODO - create some form of planet manager to assign and change planet types, (...)
                // handle pop (and AI fleets?)
                entities.add(Planets.newPlanetEntity(planet, x, y));

                x += xPadding;
                x += Planets.planetSize;
            }

            y -= yPadding;
            y -= Planets.planetSize;
        }

        return entities.toArray(new Entity[]{});
    }

    public int getLevelCount() {
        return levels.size();
    }
}
