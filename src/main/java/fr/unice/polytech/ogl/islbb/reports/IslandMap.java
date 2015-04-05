package fr.unice.polytech.ogl.islbb.reports;

import fr.unice.polytech.ogl.islbb.Data;
import fr.unice.polytech.ogl.islbb.ResultsComputing;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Classe qui contiendra une Map avec comme clé les coordonnées d'une case sous la forme "x-y",
 * ainsi que les informations connues dessus (ressources, altitude, POI).
 */
public class IslandMap {

    private Map<String, IslandTile> islandMap;
    private Data directions;

    public IslandMap() {
        this.islandMap = new HashMap<String, IslandTile>();
        this.directions = new Data();
    }

    public void addTile(String coordinates, IslandTile newTile) {
        this.islandMap.put(coordinates, newTile);
    }

    public void addTile(int newX, int newY, IslandTile newTile) {
        String coordinates = newX + "-" + newY;
        this.islandMap.put(coordinates, newTile);
    }

    public IslandTile getInformation(String coordinates) {
        return this.islandMap.get(coordinates);
    }

    public IslandTile getInformation(int x, int y) {
        return this.islandMap.get(x + "-" + y);
    }

    public boolean isRegistered(int x, int y) {
        return this.islandMap.containsKey(x + "-" + y);
    }

    public int getHighestTile(int x, int y) {
        int resultDirection = -1;
        int highestAltitude = -999;
        for (int i = 0 ; i < 4 ; i++) {
            if (this.isAlreadyScouted(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i))) {
                if ((highestAltitude <= this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getAltitude())
                && (!this.isWater(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)))) {
                    resultDirection = i;
                    highestAltitude = this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getAltitude();
                }
            }
        }
        return resultDirection;
    }

    /**
     * La case semble-t-elle être de l'océan ?
     */
    public boolean isWater(int x, int y) {
        if (this.isRegistered(x, y)) {
            return this.getInformation(x, y).isWater();
        }
        else {
            return false;
        }
    }

    public int lessWaterDirection(int x, int y) {
        int result = 0;
        int minimumWater = 100;
        for (int i = 0 ; i < 4 ; i++) {
            int currentWater = this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getOceanPart();
            if (minimumWater >= currentWater) {
                result = i;
                minimumWater = currentWater;
            }
        }

        if (this.getInformation(x + ResultsComputing.xOffset(result), y + ResultsComputing.yOffset(result)).isAlreadyExploited()) {
            int lastResult = result;
            for (int i = 0 ; i < 4 ; i++) {
                if (i != lastResult) {
                    int currentWater = this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getOceanPart();
                    if (minimumWater >= currentWater) {
                        result = i;
                        minimumWater = currentWater;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Avec la case actuelle et ses voisines, donner la directions du Biome le plus important de chaque type.
     *
     *
     * FONCTIONNEMENT :
     *      Deux Map :      - une retenant le nom du Biome et la plus grande taille trouvée.
     *                      - une retenant le plus grand Biome lui même et la direction dans laquelle il est.
     */
    public Map<Biome, Integer> bestBiomeDirections(int x, int y){

        Map<Biome, Integer> result = new HashMap<>();
        Map<String, Integer> biomeSize = new HashMap<>();

        if (this.isAlreadyGlimpsed(x, y)) {
            for (Biome currentBiome : this.getInformation(x, y).getBiomes()) {
                biomeSize.put(currentBiome.getBiome(), (int) currentBiome.getPercentage());
                result.put(currentBiome, -1);
            }
        }

        for (int i = 0 ; i < 4 ; i++) {
            if (this.isAlreadyGlimpsed(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i))) {
                for (Biome currentBiome : this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getBiomes()) {
                    if ((biomeSize.containsKey(currentBiome.getBiome())) && (biomeSize.get(currentBiome.getBiome()) < (int) currentBiome.getPercentage())) {
                        biomeSize.put(currentBiome.getBiome(), (int) currentBiome.getPercentage());
                        result.put(currentBiome, i);
                    }
                    else {
                        biomeSize.put(currentBiome.getBiome(), (int) currentBiome.getPercentage());
                        result.put(currentBiome, i);
                    }
                }
            }
        }

        return result;

    }

    /**
     * La case a-t-elle déjà été Scout ?
     */
    public boolean isAlreadyScouted(int x, int y) {
        if (this.isRegistered(x, y) == true) {
            return this.getInformation(x, y).isAlreadyScouted();
        }
        else {
            return false;
        }
    }

    /**
     * La case x y a-t-elle déjà été Explore ?
     */
    public boolean isAlreadyExplored(int x, int y) {
        if (this.isRegistered(x, y) == true) {
            return this.getInformation(x, y).isAlreadyExplored();
        }
        else {
            return false;
        }
    }

    /**
     * La case x y est-elle déjà été Glimpse ?
     */
    public boolean isAlreadyGlimpsed(int x, int y) {
        if (this.isRegistered(x, y) == true) {
            return this.getInformation(x, y).isAlreadyGlimpsed();
        }
        else {
            return false;
        }
    }

    public int firstDirectionToScout(int x, int y) {
        for (int i = 0 ; i < 4 ; i++) {
            if (!this.isAlreadyScouted(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i))) {
                return i;
            }
        }
        return 0;
    }

}
