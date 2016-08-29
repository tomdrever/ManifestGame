package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class RenderedComponent implements Component{
    private boolean visible = true;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
