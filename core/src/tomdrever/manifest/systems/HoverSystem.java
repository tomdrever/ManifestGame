package tomdrever.manifest.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import tomdrever.manifest.components.OnHoverComponent;
import tomdrever.manifest.components.SpriteComponent;

public class HoverSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<OnHoverComponent> onHoverComponentMap = ComponentMapper.getFor(OnHoverComponent.class);
    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, OnHoverComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            final Entity entity = entities.get(i);
            Rectangle entityBounds = spriteComponentMap.get(entity).getBounds();
            if (onHoverComponentMap.get(entity).isActive()) {
                // Invert libgdx y co-ord
                if (entityBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    final OnHoverComponent.OnHover entityOnHover = onHoverComponentMap.get(entity).getOnHover();

                    entityOnHover.onMouseEnter();

                    onHoverComponentMap.get(entity).setHoveredOver(true);
                } else if (onHoverComponentMap.get(entity).isHoveredOver()) {
                    final OnHoverComponent.OnHover entityOnHover = onHoverComponentMap.get(entity).getOnHover();

                    entityOnHover.onMouseExit();

                    onHoverComponentMap.get(entity).setHoveredOver(false);
                }
            }
        }

        // After processing all entities that might want to respond to the detected movement,
        // stop processing
        setProcessing(false);
    }
}
