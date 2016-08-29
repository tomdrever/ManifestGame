package tomdrever.manifest.assets;

import com.badlogic.gdx.utils.Disposable;

// TODO - read up on better generics for this
public class Resource <T> implements Disposable{
    private T resource;

    Resource(T resource) {
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
