package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.TextComponent;

public class TextRenderingSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;

    private ComponentMapper<TextComponent> textComponentMap = ComponentMapper.getFor(TextComponent.class);
    private ComponentMapper<BoundsComponent> boundsComponentMap = ComponentMapper.getFor(BoundsComponent.class);

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public TextRenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(TextComponent.class, BoundsComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BitmapFont entityFont = textComponentMap.get(entity).font;
        String entityText = textComponentMap.get(entity).text;

        Vector2 entityPosition = boundsComponentMap.get(entity).getPosition();
        Vector2 entitySize = boundsComponentMap.get(entity).getSize();

        // Calculate centered position and draw
        glyphLayout.setText(entityFont, entityText);
        entityFont.draw(spriteBatch, entityText,
                (entityPosition.x + (entitySize.x / 2)) - (glyphLayout.width / 2),
                // Inverted screen co-ords, keep as +
                (entityPosition.y + (entitySize.y / 2)) + (glyphLayout.height / 2));
    }
}
