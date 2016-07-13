package tomdrever.manifest.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.PositionComponent;
import tomdrever.manifest.components.RenderedComponent;
import tomdrever.manifest.components.SizeComponent;
import tomdrever.manifest.components.SpriteComponent;

public class RenderingSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;

    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<PositionComponent> positionComponentMap = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sizeComponentMap = ComponentMapper.getFor(SizeComponent.class);

    public RenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(RenderedComponent.class, SpriteComponent.class, PositionComponent.class, SizeComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if (entity.getComponent(RenderedComponent.class).visible) {
            Sprite entitySprite = spriteComponentMap.get(entity).sprite;
            Vector2 entityPosition = positionComponentMap.get(entity).position;
            Vector2 entitySize = sizeComponentMap.get(entity).size;

            spriteBatch.draw(entitySprite.getTexture(),
                    entityPosition.x - (entitySize.x / 2),
                    entityPosition.y - (entitySize.y / 2),
                    entitySize.x,
                    entitySize.y);
        }
    }
}
