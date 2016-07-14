package tomdrever.manifest.data;

public class Planet {
    public enum Type {
        EMPTY,
        PLAYER,
        PASSIVE,
        ENEMY,
        NOMADIC
    }

    public int initialPopulation;
    public int population;
    public float growthRate = 1;
    public float sizeMultiplier = 1;
    public Type type;
}
