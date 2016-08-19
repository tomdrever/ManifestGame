package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.LinearMovementComponent;
import tomdrever.manifest.components.SpriteComponent;

public class LinearMovementSystem extends IteratingSystem {
    private ComponentMapper<BoundsComponent> boundsComponentMap = ComponentMapper.getFor(BoundsComponent.class);
    private ComponentMapper<LinearMovementComponent> linearMovementComponentMap = ComponentMapper.getFor(LinearMovementComponent.class);
    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

    public LinearMovementSystem() {
        super(Family.all(BoundsComponent.class, LinearMovementComponent.class, SpriteComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // FIXME - Calc dest correctly, make sure fleet moves towards the right point.

        Vector2 currentPosition = boundsComponentMap.get(entity).getPosition();
        Vector2 destination = linearMovementComponentMap.get(entity).destination;

        // If the entity has just begun moving, first set up the rotation and movement offset
        if (!linearMovementComponentMap.get(entity).hasBegunMoving) {
            // Calculate rotation

            Sprite sprite = spriteComponentMap.get(entity).sprite;

            float angle = (float) Math.toDegrees(Math.atan2(destination.y - currentPosition.y, destination.x - currentPosition.x));
            if (angle < 0){
                angle += 360;
            }

            sprite.rotate(angle);

            linearMovementComponentMap.get(entity).hasBegunMoving = true;

            float speedMultiplier = linearMovementComponentMap.get(entity).speedMultiplier;

            Vector2 offset = new Vector2(
                    ((destination.x - currentPosition.x) / (Gdx.graphics.getWidth() / 2)) * speedMultiplier,
                    ((destination.y - currentPosition.y) / (Gdx.graphics.getHeight() / 2)) * speedMultiplier);

            linearMovementComponentMap.get(entity).offset = offset;
        }

        // Increase position by offset
        Vector2 offset = linearMovementComponentMap.get(entity).offset;

        BoundsComponent boundsComponent = boundsComponentMap.get(entity);
        boundsComponent.setPosition(currentPosition.x + offset.x, currentPosition.y + offset.y);

        // Check if the entity has reached its destination

        Vector2 pos = boundsComponent.getPosition();

        // FIXME - Destination reached is occasionally run early
        if (Math.abs(pos.x - destination.x) <= 5 && Math.abs(pos.x - destination.x) <= 5 ) {
            if (linearMovementComponentMap.get(entity).onDestinationReached != null) {
                linearMovementComponentMap.get(entity).onDestinationReached.run();
            }

            entity.removeAll();
            getEngine().removeEntity(entity);
        }
    }
}
