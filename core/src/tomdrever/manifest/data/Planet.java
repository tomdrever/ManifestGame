package tomdrever.manifest.data;

public class Planet {
    public enum Type {
        EMPTY,
        PLAYER,
        PASSIVE,
        ENEMY
    }

    private int initialPopulation;
    public int population;
    private int growthRate = 1;
    public Type type;

    public Planet(int initialPopulation, Type type) {
        this.initialPopulation = initialPopulation;
        this.population = initialPopulation;
        this.type = type;
    }

    public Planet(Type type) {
        this.initialPopulation = 20;
        this.type = type;
    }
}
