package tomdrever.manifest.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import tomdrever.manifest.components.BoundsComponent;
import tomdrever.manifest.components.OnClick;
import tomdrever.manifest.components.OnClickComponent;

public class ClickSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<OnClickComponent> onClickComponentMap = ComponentMapper.getFor(OnClickComponent.class);
    private ComponentMapper<BoundsComponent> boundsComponentMap = ComponentMapper.getFor(BoundsComponent.class);

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(BoundsComponent.class, OnClickComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            final Entity entity = entities.get(i);
            Rectangle entityBounds = boundsComponentMap.get(entity).getBounds();
            if (onClickComponentMap.get(entity).isActive) {
                // Invert libgdx y co-ord
                if (entityBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    final OnClick entityOnClickReaction = onClickComponentMap.get(entity).onClick;

                    entityOnClickReaction.run(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                }
            }
        }

        // After processing all entities that might want to respond to the detected click,
        // stop processing
        setProcessing(false);
    }

    // REM - using the new GameInputHandler, this system is set to process once, when a click (touch up)
    // is detected. Below is the code from prior to this - REM - keep it just in case

    /*
    public ClickSystem() {
        super(Family.all(OnClickComponent.class, BoundsComponent.class).get());
    }

    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        Rectangle entityBounds = boundsComponentMap.get(entity).getBounds();
        if (onClickComponentMap.get(entity).isActive) {
                // Invert libgdx y co-ord
                if (entityBounds.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                    final OnClick entityOnClickReaction = onClickComponentMap.get(entity).onClick;
                    onClickComponentMap.get(entity).isActive = false;

                    // Run after delay
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    entityOnClickReaction.run();
                                    onClickComponentMap.get(entity).isActive = true;
                                }
                            },
                            onClickComponentMap.get(entity).delay
                    );
                }
        }
    }
    */
}
