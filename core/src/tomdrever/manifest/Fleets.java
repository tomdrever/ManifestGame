package tomdrever.manifest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.LinearMovementComponent;
import tomdrever.manifest.components.LinearScalingComponent;
import tomdrever.manifest.components.SpriteComponent;

class Fleets {
    private static Engine engine;

    static Entity newFleet(final int fleetCapacity, Vector2 position, Vector2 destination) {
        final Entity fleet = new Entity();
        int width, height;
        String resourceName;
        if (fleetCapacity >= 1 && fleetCapacity < 5) {
            width = 60;
            height = 40;
            resourceName = "FLEET_SMALL_TEXTURE";
        } else if (fleetCapacity >= 5 && fleetCapacity < 10) {
            width = 110;
            height = 60;
            resourceName = "FLEET_MEDIUM_TEXTURE";
        } else  {
            // Here, fleet capacity should always be greater than 10 - hence "large"
            width = 130;
            height = 70;
            resourceName = "FLEET_LARGE_TEXTURE";
        }
        fleet.add(new SpriteComponent((Texture) Resources.loadResource(resourceName).get(), width, height, position.x, position.y));

        fleet.add(new LinearMovementComponent(
                new Vector2(destination.x - (width / 2), destination.y - (height / 2)), 3f,
                new LinearMovementComponent.OnDestinationReached() {
                    @Override
                    public void run() {
                        // TODO - remove linearComponent
                        fleet.remove(LinearMovementComponent.class);

                        fleet.add(new LinearScalingComponent(new Vector2(0, 0), 3f, new LinearScalingComponent.OnScalingFinished() {
                            @Override
                            public void run() {
                                // TODO - remove scalingComponent
                                // TODO - Delete entity
                                fleet.removeAll();
                                engine.removeEntity(fleet);
                            }
                        }));
                        // TODO - Calculate targeted planet's losses, based off of fleet size
                    }
            }));



        return fleet;
    }

    static void setEngine(Engine engine1) {
        engine = engine1;
    }
}
