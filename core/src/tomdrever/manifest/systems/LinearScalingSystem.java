package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.LinearScalingComponent;
import tomdrever.manifest.components.SpriteComponent;

public class LinearScalingSystem extends IteratingSystem {
    public ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);
    public ComponentMapper<LinearScalingComponent> linearScalingComponentMap = ComponentMapper.getFor(LinearScalingComponent.class);

    public LinearScalingSystem() {
        super(Family.all(SpriteComponent.class, LinearScalingComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 currentSize = spriteComponentMap.get(entity).getSize();
        Vector2 scaledSize = linearScalingComponentMap.get(entity).getScaledSize();

        if (!linearScalingComponentMap.get(entity).hasBegunScaling()) {
            float speedMultiplier = linearScalingComponentMap.get(entity).getSpeedMultiplier();

            linearScalingComponentMap.get(entity).setOffset(
                    new Vector2(scaledSize.x - currentSize.x,
                            scaledSize.y - currentSize.y).nor().scl(Math.min(currentSize.dst(scaledSize.x, scaledSize.y), speedMultiplier)));

            linearScalingComponentMap.get(entity).setHasBegunScaling(true);
        }

        Vector2 offset = linearScalingComponentMap.get(entity).getOffset();

        SpriteComponent spriteComponent = spriteComponentMap.get(entity);
        spriteComponent.setSize(currentSize.x + offset.x, currentSize.y + offset.y);

        Vector2 currentPosition = spriteComponent.getPosition();
        spriteComponent.setPosition(currentPosition.x - offset.x / 2, currentPosition.y - offset.y / 2);

        Vector2 updatedSize = spriteComponent.getSize();
        if (Math.abs(updatedSize.x - scaledSize.x) <= 5 && Math.abs(updatedSize.y - scaledSize.y) <= 5 ) {
            if (linearScalingComponentMap.get(entity).getOnScalingFinished() != null) {
                linearScalingComponentMap.get(entity).getOnScalingFinished().run();
            }
        }
    }
}
