package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class LinearScalingComponent implements Component {
    private Vector2 scaledSize;

    public Vector2 getScaledSize() {
        return scaledSize;
    }

    public void setScaledSize(Vector2 scaledSize) {
        this.scaledSize = scaledSize;
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


    private boolean hasBegunScaling;

    public boolean hasBegunScaling() {
        return hasBegunScaling;
    }

    public void setHasBegunScaling(boolean hasBegunScaling) {
        this.hasBegunScaling = hasBegunScaling;
    }


    private OnScalingFinished onScalingFinished;

    public OnScalingFinished getOnScalingFinished() {
        return onScalingFinished;
    }

    public void setOnScalingFinished(OnScalingFinished onScalingFinished) {
        this.onScalingFinished = onScalingFinished;
    }


    public LinearScalingComponent(Vector2 scaledSize, float speedMultiplier) {
        this( scaledSize, speedMultiplier, null);
    }

    public LinearScalingComponent( Vector2 scaledSize, float speedMultiplier, OnScalingFinished onScalingFinished) {
        this.speedMultiplier = speedMultiplier;
        this.scaledSize = scaledSize;
        this.onScalingFinished = onScalingFinished;
    }

    public interface OnScalingFinished {
        void run();
    }
}
