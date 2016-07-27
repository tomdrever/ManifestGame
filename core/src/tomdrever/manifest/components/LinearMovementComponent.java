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
}
