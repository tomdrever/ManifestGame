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

    public final static int fleetSizeStandard = 64;

    public static Entity newFleet(final int fleetCapacity, Vector2 position, Vector2 destination) {
        Entity fleet = new Entity();
        // TODO - Change the texture and size of the fleet based off of its population
        fleet.add(new SpriteComponent((Texture) Resources.loadResource("DEBUG_FLEET_TEXTURE").get()));
        fleet.add(new BoundsComponent(position.x, position.y, fleetSizeStandard, fleetSizeStandard));
        fleet.add(new LinearMovementComponent(
                new Vector2(destination.x - (fleetSizeStandard / 2), destination.y - (fleetSizeStandard / 2)), 3f,
                new LinearMovementComponent.OnDestinationReached() {
                    @Override
                    public void run() {
                        System.out.println(String.format("Fleet sized: %d landed!", fleetCapacity));
                        // TODO - Get fleet size,
                    }
            }));
        fleet.add(new RenderedComponent());
        return fleet;
    }
}
