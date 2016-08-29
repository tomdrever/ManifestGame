package tomdrever.manifest.data;

public class Level {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String prerequisiteLevelName = "none";

    public String getPrerequisiteLevelName() {
        return prerequisiteLevelName;
    }

    public void setPrerequisiteLevelName(String prerequisiteLevelName) {
        this.prerequisiteLevelName = prerequisiteLevelName;
    }


    private Planet[][] planets;

    public Planet[][] getPlanets() {
        return planets;
    }

    public void setPlanets(Planet[][] planets) {
        this.planets = planets;
    }
}
