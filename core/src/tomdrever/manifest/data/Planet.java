package tomdrever.manifest.data;

public class Planet {
    public enum Type {
        EMPTY,
        PLAYER,
        PASSIVE,
        ENEMY,
        NOMADIC
    }

    private int initialPopulation;

    public int getInitialPopulation() {
        return initialPopulation;
    }

    public void setInitialPopulation(int initialPopulation) {
        this.initialPopulation = initialPopulation;
    }


    private int maxPopulation;

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }


    private float growthRate = 1;

    public float getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(float growthRate) {
        this.growthRate = growthRate;
    }


    private float sizeMultiplier = 1;

    public float getSizeMultiplier() {
        return sizeMultiplier;
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private Type type;
}
