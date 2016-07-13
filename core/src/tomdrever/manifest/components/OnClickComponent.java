package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;

public class OnClickComponent implements Component {
    public OnClickListener onClickListener;
    public boolean isClicked = false;

    public OnClickComponent(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
