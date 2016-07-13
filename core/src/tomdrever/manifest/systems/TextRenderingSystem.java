package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.PositionComponent;
import tomdrever.manifest.components.TextComponent;

public class TextRenderingSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;

    private ComponentMapper<TextComponent> textComponentMap = ComponentMapper.getFor(TextComponent.class);
    private ComponentMapper<PositionComponent> positionComponentMap = ComponentMapper.getFor(PositionComponent.class);

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public TextRenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(TextComponent.class, PositionComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BitmapFont entityFont = textComponentMap.get(entity).font;
        String entityText = textComponentMap.get(entity).text;

        Vector2 entityPosition = positionComponentMap.get(entity).position;

        // Calculate centered position and draw
        glyphLayout.setText(entityFont, entityText);
        entityFont.draw(spriteBatch, entityText,
                entityPosition.x - (glyphLayout.width / 2),
                // Inverted screen co-ords, keep as +
                entityPosition.y + (glyphLayout.height / 2));
    }
}
