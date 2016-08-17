package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class LinearMovementComponent implements Component {
    public Vector2 destination;
    public float speedMultiplier;
    public boolean hasBegunMoving;
    public Vector2 offset;

    public LinearMovementComponent(Vector2 destination, float speed) {
        this.destination = destination;
        this.speedMultiplier = speed;
    }
}
