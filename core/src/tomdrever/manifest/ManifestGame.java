package tomdrever.manifest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomdrever.manifest.systems.RenderingSystem;
import tomdrever.manifest.systems.TextRenderingSystem;

import java.io.IOException;

public class ManifestGame extends ApplicationAdapter {
	private Engine engine;
    private SpriteBatch spriteBatch;

    private Sprite debug;
	
	@Override
	public void create () {
		engine = new Engine();
        spriteBatch = new SpriteBatch();

        Assets.loadAssets();

        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);
        engine.addSystem(renderingSystem);

        TextRenderingSystem textRenderingSystem = new TextRenderingSystem(spriteBatch);
        engine.addSystem(textRenderingSystem);

        LevelManager levelManager = null;
        try {
            levelManager = LevelManager.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert levelManager != null;

        Entity[] entities = levelManager.getLevel("Level 1");
        for (Entity entity:entities) {
            engine.addEntity(entity);
        }

        debug = new Sprite(new Texture("badlogic.jpg"));
    }

    @Override
    public void render() {
        spriteBatch.begin();
        engine.update(Gdx.graphics.getDeltaTime());
        spriteBatch.draw(debug,
                (Gdx.graphics.getWidth()/2) - 5,
                (Gdx.graphics.getHeight()/2) - 5,
                10, 10);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
