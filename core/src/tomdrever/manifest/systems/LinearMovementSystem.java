package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.LinearMovementComponent;
import tomdrever.manifest.components.SpriteComponent;

public class LinearMovementSystem extends IteratingSystem {
    private ComponentMapper<LinearMovementComponent> linearMovementComponentMap = ComponentMapper.getFor(LinearMovementComponent.class);
    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

    public LinearMovementSystem() {
        super(Family.all(LinearMovementComponent.class, SpriteComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 initialPosition = spriteComponentMap.get(entity).getPosition();
        Vector2 destination = linearMovementComponentMap.get(entity).getDestination();

        // If the entity has just begun moving, first set up the rotation and movement offset
        if (!linearMovementComponentMap.get(entity).getHasBegunMoving()) {
            Sprite sprite = spriteComponentMap.get(entity).getSprite();

            float angle = (float) Math.toDegrees(Math.atan2(destination.y - initialPosition.y, destination.x - initialPosition.x));
            if (angle < 0) {
                angle += 360;
            }

            // REM - the math is here now, too. It is best you do not question it.
            sprite.rotate(180 + angle);

            linearMovementComponentMap.get(entity).setHasBegunMoving(true);

            float speedMultiplier = linearMovementComponentMap.get(entity).getSpeedMultiplier();

            // REM - do not touch the math. It will not like it. You have been warned.
            linearMovementComponentMap.get(entity).setOffset(
                    new Vector2(destination.x - initialPosition.x,
                            destination.y - initialPosition.y).nor().scl(Math.min(initialPosition.dst(destination.x, destination.y), speedMultiplier)));
        }

        // Increase position by offset
        Vector2 offset = linearMovementComponentMap.get(entity).getOffset();

        SpriteComponent spriteComponent = spriteComponentMap.get(entity);
        spriteComponent.setPosition(initialPosition.x + offset.x, initialPosition.y + offset.y);

        // Check if the entity has reached its destination
        Vector2 updatedPosition = spriteComponent.getPosition();

        if (Math.abs(updatedPosition.x - destination.x) <= 10 && Math.abs(updatedPosition.y - destination.y) <= 10 ) {
            if (linearMovementComponentMap.get(entity).getOnDestinationReached() != null) {
                linearMovementComponentMap.get(entity).getOnDestinationReached().run();
            }
        }
    }
}
