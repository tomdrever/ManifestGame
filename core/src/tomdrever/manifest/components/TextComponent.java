package tomdrever.manifest.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextComponent implements Component {
    public String text;
    public BitmapFont font;

    public TextComponent(String text, BitmapFont font) {
        this.text = text;
        this.font = font;
    }
}
