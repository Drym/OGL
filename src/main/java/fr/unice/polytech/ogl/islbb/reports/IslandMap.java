package fr.unice.polytech.ogl.islbb.reports;

import fr.unice.polytech.ogl.islbb.Data;
import fr.unice.polytech.ogl.islbb.ResultsComputing;

import javax.xml.transform.Result;
import java.util.HashMap;
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
        if (this.isRegistered(x, y) == true) {
            return this.getInformation(x, y).isWater();
        }
        else {
            return false;
        }
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
            return this.getInformation(x, y).isAlreadyScouted();
        }
        else {
            return false;
        }
    }

}
