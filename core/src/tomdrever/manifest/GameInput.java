package tomdrever.manifest;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInput extends InputAdapter {
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            ManifestGame.paused = !ManifestGame.paused;
        }

        return super.keyUp(keycode);
    }
}
