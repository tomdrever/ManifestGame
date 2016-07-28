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

    private int speed;
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private boolean hasBegunMoving;
    public boolean getHasBegunMoving() {
        return hasBegunMoving;
    }
    public void setHasBegunMoving(boolean hasBegunMoving) {
        this.hasBegunMoving = hasBegunMoving;
    }

    private Vector2 offset;
    public Vector2 getOffset() {
        return offset;
    }
    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public LinearMovementComponent(Vector2 destination, int speed) {
        this.destination = destination;
        this.speed = speed;
    }
}
