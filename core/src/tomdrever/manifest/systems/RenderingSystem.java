package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.SpriteComponent;

public class RenderingSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;

    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

    public RenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all( SpriteComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite entitySprite = spriteComponentMap.get(entity).getSprite();
        Vector2 entityPosition = spriteComponentMap.get(entity).getPosition();
        Vector2 entitySize = spriteComponentMap.get(entity).getSize();

        TextureRegion region = new TextureRegion(entitySprite.getTexture(),
                entitySprite.getTexture().getWidth(),
                entitySprite.getTexture().getHeight());

        spriteBatch.draw(region,
                entityPosition.x,
                entityPosition.y,
                entitySize.x / 2,
                entitySize.y / 2,
                entitySize.x,
                entitySize.y,
                1, 1,
                entitySprite.getRotation(), false);

    }
}
