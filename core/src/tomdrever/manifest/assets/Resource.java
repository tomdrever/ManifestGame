package tomdrever.manifest.assets;

import com.badlogic.gdx.utils.Disposable;

public class Resource <T> implements Disposable{
    private T resource;
    public Resource(T resource) {
        this.resource = resource;
    }

    public T get() {
        return resource;
    }

    @Override
    public void dispose() {
        if (resource instanceof Disposable) {
            ((Disposable) resource).dispose();
        }
    }
}
