package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import tomdrever.manifest.components.*;
import tomdrever.manifest.data.Level;
import tomdrever.manifest.data.Planet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class LevelManager {
    private Map<String, Level> levels;
    
    private static int planetSize = 100;

    private LevelManager() { }

    public static LevelManager load() throws IOException {
        LevelManager levelManager = new LevelManager();

        byte[] encoded = Files.readAllBytes(Paths.get("levels.txt"));
        Gson gson = new GsonBuilder().create();
        Level[] loadedLevels = gson.fromJson( new String(encoded), Level[].class);

        Map<String, Level> loadedLevelsDict = new LinkedTreeMap<String, Level>() ;
        for (Level level: loadedLevels) {
            loadedLevelsDict.put(level.name, level);
        }

        levelManager.levels = loadedLevelsDict;

        return levelManager;
    }

    public Entity[] getLevel(String name) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        Level level = levels.get(name);

        int yPlanetCount = level.planets.length;
        int yPadding = yPlanetCount == 1 ? 0 : 50 - (10 * yPlanetCount);
        int y = (Gdx.graphics.getHeight() / 2) +
                (((yPlanetCount - 1) * (planetSize)) / 2)
                    + ((yPlanetCount) * yPadding);

        for (Planet[] planetRow : level.planets) {
            int xPlanetCount = planetRow.length;
            int xPadding = xPlanetCount == 1 ? 0 : 75 - (15 * xPlanetCount);
            int x = (Gdx.graphics.getWidth() / 2) -
                    ((((xPlanetCount - 1) * (planetSize)) / 2)
                            + ((xPlanetCount) * xPadding));

            y -= yPadding;

            for (Planet planet: planetRow) {
                x += xPadding;
                entities.add(new Entity()
                        .add(new RenderedComponent())
                        .add(new SpriteComponent(Assets.PLANET_EMPTY_TEXTURE))
                        .add(new BoundsComponent(x, y, planetSize, planetSize))
                        .add(new TextComponent(String.format("%d", planet.population), Assets.PLANET_FONT))
                        .add(new OnClickComponent(new OnClickListener() {
                            @Override
                            public void run() {
                                System.out.println("planet clicked!");
                            }
                        })));

                x += xPadding;
                x += planetSize;
            }

            y -= yPadding;
            y -= planetSize;
        }

        return entities.toArray(new Entity[]{});
    }

    public int getLevelCount() {
        return levels.size();
    }
}
