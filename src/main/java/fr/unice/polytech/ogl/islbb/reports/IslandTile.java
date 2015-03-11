package fr.unice.polytech.ogl.islbb.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Classe contenant les informations sur une case.
 * Sous la forme du type de Biome (à venir), la liste des ressources, la liste des POI et son altitude).
 */
public class IslandTile {

    private String type;
    private int altitude;
    private List<Resource> resources;
    private List<POI> pois;

    /**
     * Méthode d'instanciation lors d'un Scout.
     */
    public IslandTile(int scoutedAltitude, List<Resource> scoutedResources) {
        this.type = null;
        this.altitude = scoutedAltitude;
        this.resources = scoutedResources;
        this.pois = new ArrayList<POI>();
    }

    /**
     * Méthode d'instanciation lors d'un Explore.
     */
    public IslandTile(List<Resource> exploredResources, List<POI> exploredPOIs) {
        this.type = null;
        this.altitude = 0;
        this.resources = exploredResources;
        this.pois = exploredPOIs;
    }

    public void exploreTile(List<Resource> exploredResources, List<POI> exploredPOIs) {
        this.resources = exploredResources;
        this.pois = exploredPOIs;
    }
}
