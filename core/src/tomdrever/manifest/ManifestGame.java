package tomdrever.manifest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tomdrever.manifest.assets.Resources;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.RenderedComponent;
import tomdrever.manifest.components.SpriteComponent;
import tomdrever.manifest.systems.*;

public class ManifestGame extends ApplicationAdapter {
	private Engine engine;
    private SpriteBatch spriteBatch;

    public static boolean paused = false;
	
	@Override
	public void create () {
		engine = new Engine();
        spriteBatch = new SpriteBatch();

        GameInput gameInput = new GameInput();
        Gdx.input.setInputProcessor(gameInput);

        // TODO - loading screen?
        Resources.loadAssets();

        Planets.setEngine(engine);

        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);
        engine.addSystem(renderingSystem);

        TextRenderingSystem textRenderingSystem = new TextRenderingSystem(spriteBatch);
        engine.addSystem(textRenderingSystem);

        ClickSystem clickSystem = new ClickSystem();
        engine.addSystem(clickSystem);

        PopulationSystem popSystem = new PopulationSystem();
        engine.addSystem(popSystem);

        LinearMovementSystem linearMovementSystem = new LinearMovementSystem();
        engine.addSystem(linearMovementSystem);

        Levels levels = Levels.loadLevels();

        addBackground();

        Entity[] entities = levels.getLevel("Level 3");
        for (Entity entity:entities) {
            engine.addEntity(entity);
        }

        // TODO - OVERVIEW - Planets (pop, selection), Fleets (creation, handling), UI (levels screen)

        // REM - LIBGDX DRAWS FROM BOTTOM-LEFT
    }

    public void addBackground() {
        Entity background = new Entity();
        background.add(new RenderedComponent())
                .add(new BoundsComponent(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()))
                .add(new SpriteComponent((Texture) Resources.loadResource("BACKGROUND_TEXTURE").get()));
        engine.addEntity(background);
    }

    @Override
    public void render() {
        if (!paused) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            spriteBatch.begin();
            engine.update(Gdx.graphics.getDeltaTime());
            spriteBatch.end();
        }
    }

    @Override
    public void dispose() {
        Resources.disposeOfResources();
        spriteBatch.dispose();
    }
}
