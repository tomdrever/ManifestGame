package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class OnClickComponent implements Component {
    public OnClick onClick;
    public boolean isActive = true;

    public int cooldown = 100;

    public OnClickComponent(OnClick onClick) {
        this.onClick = onClick;
    }
}
