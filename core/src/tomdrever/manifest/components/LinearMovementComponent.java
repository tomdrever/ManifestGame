package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class LinearMovementComponent implements Component {
    public Vector2 destination;
    public float speedMultiplier;
    public Vector2 offset = new Vector2(0, 0);
    public boolean hasBegunMoving = false;
    public OnDestinationReached onDestinationReached = null;

    public LinearMovementComponent(Vector2 destination, float speed) {
        this.destination = destination;
        this.speedMultiplier = speed;
    }

    public LinearMovementComponent(Vector2 destination, float speedMultiplier, OnDestinationReached onDestinationReached) {
        this(destination, speedMultiplier);
        this.onDestinationReached = onDestinationReached;
    }

    public interface OnDestinationReached {
        void run();
    }
}
