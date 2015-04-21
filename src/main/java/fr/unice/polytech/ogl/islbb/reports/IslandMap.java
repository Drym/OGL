package fr.unice.polytech.ogl.islbb.reports;

import fr.unice.polytech.ogl.islbb.Data;
import fr.unice.polytech.ogl.islbb.Explorer;
import fr.unice.polytech.ogl.islbb.ResultsComputing;
import fr.unice.polytech.ogl.islbb.data.ExplorerDecision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Classe qui contiendra une Map avec comme clé les coordonnées d'une case sous la forme "x-y",
 * ainsi que les informations connues dessus (ressources, altitude, POI).
 */
public class IslandMap {

    private Map<String, IslandTile> islandMap;
    private Data directions;
    private int firstDirectionRandom;

    public IslandMap() {
        this.islandMap = new HashMap<String, IslandTile>();
        this.directions = new Data();
        this.firstDirectionRandom = 0;
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

    /**
     * Trouve la case voisine (distance 1) contenant le moins d'eau.
     * Prend en compte la direction du dernier mouvement :
     *  Si la direction choisie à la fin de la première boucle est équivalent à un retour sur la case d'où on vient,
     *  on récupère la deuxième direction contenant le moins d'eau.
     *
     */
    public int lessWaterDirection(int x, int y, int lastMove) {

        String debug = new String();

        int result = -1;
        int minimumWater = 101;

        for (int i = 0 ; i < 4 ; i++) {
            int currentWater = this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getOceanPart();
            if (minimumWater >= currentWater) {
                result = i;
                minimumWater = currentWater;
                debug += "Loop 1: " + i + " " + minimumWater + " ";
            }
        }

        debug += "(TEST MOD " + ((lastMove + 2) % 4) + " = " + result + ") ";
        int lastResult = result;
        if (((lastMove + 2) % 4) == result) {

            debug += " | ";

            minimumWater = 101;

            for (int i = 0 ; i < 4 ; i++) {
                debug += "(TEST " + i + " = " + lastResult + ") ";
                if (i == lastResult) {
                    continue;
                }
                int currentWater = this.getInformation(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i)).getOceanPart();
                if ((minimumWater >= currentWater) && (currentWater < 90)) {
                    result = i;
                    minimumWater = currentWater;
                    debug += "Loop 2: " + i + " " + minimumWater + " ";
                }
            }
        }

        ExplorerDecision.setDebug(debug);
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
     *
     */

    public int firstDirectionToGlimpse(int x, int y) {

        int observedX, observedY;
        
        for (int d = 0 ; d < 4 ; d++) {
            
            for (int p = 1 ; p < 4 ; p--) {
                
                observedX = x + (ResultsComputing.xOffset(d) * p);
                observedY = y + (ResultsComputing.yOffset(d) * p);
                
                if (this.isAlreadyGlimpsed(observedX,  observedY)) {
                    continue;
                }
                else {
                    return d;
                }
                
            }
            
        }
        
        return -1;
    }
    /**
     * augmente le nombre de addVisit sur la case;
     */

    public void addVisit(int x, int y) {

        if (this.isRegistered(x, y))
            this.getInformation(x, y).addTileVisit();

    }

    public boolean isVisited(int x,int y){

        return this.getInformation(x, y).getTileVisits() > 0;

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

    public int randomDirectionToScout(int x, int y) {

        Random r = new Random();

        ArrayList<Integer> possibleDirections = new ArrayList<>();

        for (int i = 0 ; i < 4 ; i++) {
            if (!this.isAlreadyScouted(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i))) {
                possibleDirections.add(i);
            }
        }

        if (possibleDirections.size() > 0) {
            return possibleDirections.get(r.nextInt(possibleDirections.size()));
        }

        return 0;

    }
    public int firstDirectionToScout(int x, int y) {
        this.firstDirectionRandom++;
        for (int i = 0 ; i < 4 ; i++) {
            if (!this.isAlreadyScouted(x + ResultsComputing.xOffset((i + this.firstDirectionRandom) % 4), y + ResultsComputing.yOffset((i + this.firstDirectionRandom) % 4))) {
                return i;
            }
        }
        return 0;
    }

/*    public int firstDirectionToGlimpse(int x, int y) {
        for (int i = 0 ; i < 4 ; i++) {
            if (!this.isAlreadyGlimpsed(x + ResultsComputing.xOffset(i), y + ResultsComputing.yOffset(i))) {
                return i;
            }
        }
        return 0;
    }*/

}
