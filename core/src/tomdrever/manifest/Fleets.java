package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.LinearMovementComponent;
import tomdrever.manifest.components.RenderedComponent;
import tomdrever.manifest.components.SpriteComponent;

public class Fleets {
    // TODO - factory for fleets
    public static Entity newFleet(Vector2 position, Vector2 destination) {
        Entity fleet = new Entity();
        fleet.add(new SpriteComponent((Texture) Resources.loadResource("DEBUG_FLEET_TEXTURE").get()));
        fleet.add(new BoundsComponent(position.x, position.y, 64, 64));
        fleet.add(new LinearMovementComponent(destination, 5));
        fleet.add(new RenderedComponent());
        return fleet;
    }
}
