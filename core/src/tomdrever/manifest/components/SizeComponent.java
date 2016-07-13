package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SizeComponent implements Component{
    public Vector2 size;

    public SizeComponent(float width, float height) {
        this.size = new Vector2(width, height);
    }
}
