package tomdrever.manifest;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import tomdrever.manifest.systems.ClickSystem;
import tomdrever.manifest.systems.HoverSystem;

class GameInputHandler extends InputAdapter {
    private ManifestGame manifestGame;

    GameInputHandler(ManifestGame manifestGame) {
        this.manifestGame = manifestGame;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            manifestGame.paused = !manifestGame.paused;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!manifestGame.paused) {
            manifestGame.engine.getSystem(ClickSystem.class).setProcessing(true);
        }

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (!manifestGame.paused) {
            manifestGame.engine.getSystem(HoverSystem.class).setProcessing(true);
        }

        return true;
    }
}
