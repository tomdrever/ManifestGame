package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class OnHoverComponent implements Component {
    public OnHover onHover;
    public boolean isActive = true;
    public boolean isHoveredOver = false;

    public OnHoverComponent(OnHover onHover) {
        this.onHover = onHover;
    }

    public interface OnHover{
        void onMouseEnter();
        void onMouseExit();
    }
}
