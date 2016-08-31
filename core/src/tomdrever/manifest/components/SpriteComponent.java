package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent implements Component {
    private Sprite sprite;

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }

    public Vector2 getSize() {
        return new Vector2(sprite.getWidth(), sprite.getHeight());
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }
    
    public void setSize(Vector2 size) {
        sprite.setSize(size.x, size.y);
    }
    
    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public void setPosition(Vector2 position) {
        sprite.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public SpriteComponent(Vector2 size, Vector2 position) {
        this(new Texture((int)size.x, (int)size.y, Pixmap.Format.RGBA8888), size, position);
    }

    public SpriteComponent(Texture texture, Vector2 size, Vector2 position) {
        this(texture, size.x, size.y, position.x, position.y);
    }

    public SpriteComponent(float width, float height, float x, float y) {
        this(new Texture((int)width, (int)height, Pixmap.Format.RGBA8888), (int)width, (int)height, (int)x, (int)y);
    }

    public SpriteComponent(Texture texture, float width, float height, float x, float y) {
        sprite = new Sprite(texture, (int)x, (int)y, (int)width, (int)height);
        sprite.setX(x);
        sprite.setY(y);
    }
}
