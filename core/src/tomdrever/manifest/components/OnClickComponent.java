package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class OnClickComponent implements Component {
    public OnClick onClick;
    public boolean isActive = true;

    // Compensate against looped mouse clicks
    public int delay = 100;

    public OnClickComponent(OnClick onClick) {
        this.onClick = onClick;
    }
}
