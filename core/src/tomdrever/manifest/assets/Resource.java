package tomdrever.manifest.assets;

public class Resource <T> {
    private T resource;
    public Resource(T resource) {
        this.resource = resource;
    }

    public T get() {
        return resource;
    }
}
