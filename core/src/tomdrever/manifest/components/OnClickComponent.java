package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class OnClickComponent implements Component {
    public OnClick onClick;
    public boolean isActive = true;

    public OnClickComponent(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick {
        void run(Vector2 mousePosition);
    }
}
