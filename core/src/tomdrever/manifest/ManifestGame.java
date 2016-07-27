package tomdrever.manifest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.systems.ClickSystem;
import tomdrever.manifest.systems.PopulationSystem;
import tomdrever.manifest.systems.RenderingSystem;
import tomdrever.manifest.systems.TextRenderingSystem;

public class ManifestGame extends ApplicationAdapter {
	private Engine engine;
    private SpriteBatch spriteBatch;

    private Texture background;
	
	@Override
	public void create () {
		engine = new Engine();
        spriteBatch = new SpriteBatch();

        // TODO - loading screen?
        Resources.loadAssets();

        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);
        engine.addSystem(renderingSystem);

        TextRenderingSystem textRenderingSystem = new TextRenderingSystem(spriteBatch);
        engine.addSystem(textRenderingSystem);

        ClickSystem clickSystem = new ClickSystem();
        engine.addSystem(clickSystem);

        PopulationSystem popSystem = new PopulationSystem();
        engine.addSystem(popSystem);

        Levels levels = Levels.load();

        Entity[] entities = levels.getLevel("Level 1");
        for (Entity entity:entities) {
            engine.addEntity(entity);
        }

        background = (Texture) Resources.loadResource("BACKGROUND_TEXTURE").get();

        // TODO - OVERVIEW - Planets (pop, selection), Fleets (creation, handling), UI (levels screen)
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        engine.update(Gdx.graphics.getDeltaTime());
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        Resources.disposeOfResources();
        spriteBatch.dispose();
    }
}
