package tomdrever.manifest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.LinearMovementComponent;
import tomdrever.manifest.components.RenderedComponent;
import tomdrever.manifest.components.SpriteComponent;

class Fleets {

    private final static int fleetSizeStandard = 64;

    static Entity newFleet(final int fleetCapacity, Vector2 position, Vector2 destination) {
        Entity fleet = new Entity();
        // TODO - Change the texture  of the fleet based off of its population
        fleet.add(new SpriteComponent((Texture) Resources.loadResource("FLEET_SMALL_TEXTURE").get()));
        fleet.add(new BoundsComponent(position.x, position.y, 94, 56));
        fleet.add(new LinearMovementComponent(
                new Vector2(destination.x - (fleetSizeStandard / 2), destination.y - (fleetSizeStandard / 2)), 3f,
                new LinearMovementComponent.OnDestinationReached() {
                    @Override
                    public void run() {
                        // TODO - Calculate targeted planet's losses, based off of fleet size
                        // TODO - animate fleet descent?
                    }
            }));

        fleet.add(new RenderedComponent());
        return fleet;
    }
}
