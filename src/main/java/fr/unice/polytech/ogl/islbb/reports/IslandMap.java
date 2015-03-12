package fr.unice.polytech.ogl.islbb.reports;

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

    public IslandMap() {
        this.islandMap = new HashMap<String, IslandTile>();
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

    public boolean alreadyExplored(int x, int y) {
        //deja explorer = true
        if (this.islandMap.containsKey(x + "-" + y)) {
            return true;
        }
        //sinon pas explorer
        else return false;
    }

}
