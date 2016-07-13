package tomdrever.manifest.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.PositionComponent;
import tomdrever.manifest.components.TextComponent;

public class TextRenderingSystem extends EntitySystem {
    private SpriteBatch spriteBatch;

    private ImmutableArray<Entity> entities;

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public TextRenderingSystem(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TextComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);

            BitmapFont entityFont = entity.getComponent(TextComponent.class).font;
            String entityText = entity.getComponent(TextComponent.class).text;

            Vector2 entityPosition = entity.getComponent(PositionComponent.class).position;

            // Calculate centered position and draw
            glyphLayout.setText(entityFont, entityText);
            entityFont.draw(spriteBatch, entityText,
                    entityPosition.x - (glyphLayout.width / 2),
                    // Inverted screen co-ords, keep as +
                    entityPosition.y + (glyphLayout.height / 2));
        }
    }
}
