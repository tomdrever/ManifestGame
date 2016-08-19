package tomdrever.manifest;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import tomdrever.manifest.systems.ClickSystem;
import tomdrever.manifest.systems.HoverSystem;

public class GameInputHandler extends InputAdapter {
    private ManifestGame manifestGame;

    public GameInputHandler(ManifestGame manifestGame) {
        this.manifestGame = manifestGame;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            manifestGame.paused = !manifestGame.paused;
        }

        return super.keyUp(keycode);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!manifestGame.paused) {
            manifestGame.engine.getSystem(ClickSystem.class).setProcessing(true);
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (!manifestGame.paused) {
            manifestGame.engine.getSystem(HoverSystem.class).setProcessing(true);
        }

        return super.mouseMoved(screenX, screenY);
    }
}
