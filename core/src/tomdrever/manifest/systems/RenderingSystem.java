package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.RenderedComponent;
import tomdrever.manifest.components.SpriteComponent;

public class RenderingSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;

    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<BoundsComponent> boundsComponentMap = ComponentMapper.getFor(BoundsComponent.class);

    public RenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(RenderedComponent.class, SpriteComponent.class, BoundsComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (entity.getComponent(RenderedComponent.class).visible) {
            Sprite entitySprite = spriteComponentMap.get(entity).sprite;
            Vector2 entityPosition = boundsComponentMap.get(entity).getPosition();
            Vector2 entitySize = boundsComponentMap.get(entity).getSize();

            spriteBatch.draw(entitySprite.getTexture(),
                    entityPosition.x,
                    entityPosition.y,
                    entitySize.x,
                    entitySize.y);
        }
    }
}
