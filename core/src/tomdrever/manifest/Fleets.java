package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.SpriteComponent;

public class Fleets {
    // TODO - factory for fleets
    public static Entity newFleet() {
        Entity fleet = new Entity();
        fleet.add(new SpriteComponent((Texture) Resources.loadResource("").get()));
        return fleet;
    }
}
