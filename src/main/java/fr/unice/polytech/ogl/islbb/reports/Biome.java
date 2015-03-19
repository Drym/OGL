package fr.unice.polytech.ogl.islbb.reports;

/**
 * Created by Quentin WEPHRE on 19/03/2015.
 */
public class Biome {

    /**
     * La chaîne de caractère décrivant le biome :
     * Possibilités :
     *  - OCEAN
     *  - LAKE
     *  - SHRUBLAND
     *  - TROPICAL_SEASONAL_FOREST
     *  - TROPICAL_RAIN_FOREST
     *  - BEACH
     *  - MANGROVE
     */
    private String biome;
    private float percentage;

    public Biome(String biomeName) {
        this.biome = biomeName;
    }

    public Biome(String biomeName, float biomePercentage) {
        this(biomeName);
        this.percentage = biomePercentage;
    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

}
