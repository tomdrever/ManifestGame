package tomdrever.manifest.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.SpriteComponent;
import tomdrever.manifest.components.TextComponent;

public class TextRenderingSystem extends IteratingSystem {
    private SpriteBatch spriteBatch;

    private ComponentMapper<TextComponent> textComponentMap = ComponentMapper.getFor(TextComponent.class);
    private ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public TextRenderingSystem(SpriteBatch spriteBatch) {
        super(Family.all(TextComponent.class, SpriteComponent.class).get());
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BitmapFont entityFont = textComponentMap.get(entity).getFont();
        String entityText = textComponentMap.get(entity).getText();

        Vector2 entityPosition = spriteComponentMap.get(entity).getPosition();
        Vector2 entitySize = spriteComponentMap.get(entity).getSize();

        // Calculate centered position of text and draw
        glyphLayout.setText(entityFont, entityText);
        entityFont.draw(spriteBatch, entityText,
                (entityPosition.x + (entitySize.x / 2)) - (glyphLayout.width / 2),
                // Inverted screen co-ords, REM - keep as +
                (entityPosition.y + (entitySize.y / 2)) + (glyphLayout.height / 2));
    }
}
