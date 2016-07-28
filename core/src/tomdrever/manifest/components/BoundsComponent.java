package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoundsComponent implements Component {
    // Position represents centre of component
    private Vector2 position = new Vector2(0, 0);
    public Vector2 getPosition() {
        return position;
    }
    public void setPosition(float x, float y) {
        this.position = new Vector2(x - (this.size.x / 2), y - this.size.y / 2);
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    private Vector2 size;
    public Vector2 getSize() {
        return size;
    }
    public void setSize(float width, float height) {
        this.size = new Vector2(width, height);
    }
    public void setSize(Vector2 size) {
        this.size = size;
    }

    public BoundsComponent(float x, float y, float width, float height) {
        size = new Vector2(width, height);
        position = new Vector2(x - (width / 2), y - height / 2);
    }

    public BoundsComponent(float width, float height) {
        size = new Vector2(width, height);
    }

    // Util method to easily get rectangle from bounds
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, size.x, size.y);
    }
}
