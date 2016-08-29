package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class OnHoverComponent implements Component {
    private OnHover onHover;

    public OnHover getOnHover() {
        return onHover;
    }

    public void setOnHover(OnHover onHover) {
        this.onHover = onHover;
    }


    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    private boolean isHoveredOver = false;

    public boolean isHoveredOver() {
        return isHoveredOver;
    }

    public void setHoveredOver(boolean hoveredOver) {
        isHoveredOver = hoveredOver;
    }

    public OnHoverComponent(OnHover onHover) {
        this.onHover = onHover;
    }

    public interface OnHover{
        void onMouseEnter();
        void onMouseExit();
    }
}
