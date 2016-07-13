package tomdrever.manifest.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.PositionComponent;
import tomdrever.manifest.components.RenderedComponent;
import tomdrever.manifest.components.SizeComponent;
import tomdrever.manifest.components.SpriteComponent;

public class RenderingSystem extends EntitySystem {
    private SpriteBatch spriteBatch;

    private ImmutableArray<Entity> entities;

    public RenderingSystem(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RenderedComponent.class, SpriteComponent.class, PositionComponent.class, SizeComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);

            if (entity.getComponent(RenderedComponent.class).visible) {
                Sprite entitySprite = entity.getComponent(SpriteComponent.class).sprite;
                Vector2 entityPosition = entity.getComponent(PositionComponent.class).position;
                Vector2 entitySize = entity.getComponent(SizeComponent.class).size;

                spriteBatch.draw(entitySprite.getTexture(),
                        entityPosition.x - (entitySize.x / 2),
                        entityPosition.y - (entitySize.y / 2),
                        entitySize.x,
                        entitySize.y);
            }
        }
    }
}
