package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class LinearMovementComponent implements Component {
    private Vector2 destination;

    public Vector2 getDestination() {
        return destination;
    }

    public void setDestination(Vector2 destination) {
        this.destination = destination;
    }


    private float speedMultiplier;

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }


    private Vector2 offset = new Vector2(0, 0);

    public Vector2 getOffset() {
        return offset;
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }


    private boolean hasBegunMoving = false;

    public boolean getHasBegunMoving() {
        return hasBegunMoving;
    }

    public void setHasBegunMoving(boolean hasBegunMoving) {
        this.hasBegunMoving = hasBegunMoving;
    }


    private OnDestinationReached onDestinationReached = null;

    public OnDestinationReached getOnDestinationReached() {
        return onDestinationReached;
    }

    public void setOnDestinationReached(OnDestinationReached onDestinationReached) {
        this.onDestinationReached = onDestinationReached;
    }


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
