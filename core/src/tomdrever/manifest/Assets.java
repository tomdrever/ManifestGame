package tomdrever.manifest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static BitmapFont PLANET_FONT;

    public static void loadAssets() {
        PLANET_FONT = new BitmapFont();
        PLANET_FONT.setColor(Color.GREEN);
    }
}
