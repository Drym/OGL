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

    private boolean alreadyScouted = false;
    private boolean alreadyExplored = false;

    /**
     * Méthode d'instanciation lors d'un Scout.
     */
    public IslandTile(int scoutedAltitude, List<Resource> scoutedResources) {
        this.type = null;
        this.altitude = scoutedAltitude;
        this.resources = scoutedResources;
        this.pois = new ArrayList<POI>();
        this.alreadyScouted = true;
    }

    /**
     * Méthode d'instanciation lors d'un Explore.
     */
    public IslandTile(List<Resource> exploredResources, List<POI> exploredPOIs) {
        this.type = null;
        this.altitude = 0;
        this.resources = exploredResources;
        this.pois = exploredPOIs;
        this.alreadyExplored = true;
    }


    /**
     * Méthode pour mettre à jour une case déjà Scout.
     */
    public void exploreTile(List<Resource> exploredResources, List<POI> exploredPOIs) {
        this.resources = exploredResources;
        this.pois = exploredPOIs;
        this.alreadyExplored = true;
    }

    /**
     * Méthode pour savoir si une ressource est présente sur la case.
     */
    public Resource hasResource(String aResource) {
        for (Resource currentResource : this.resources) {
            if (currentResource.getType().equals(aResource)) {
                return currentResource;
            }
        }
        return null;
    }

    /**
     * On supprime la ressource de la case (après une exploitation)
     */
    public void removeResource(String aResource) {
        this.resources.remove(this.resources.indexOf(aResource));
    }

    /**
     * Est-ce que la case semble marquer le rivage de l'océan ?
     */
    public boolean isWater() {
        if (this.altitude < 0) {
            if (this.hasResource("FISH") != null) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Doit être scouter ?
     * @return true/false
     */
    public boolean hasToScout() {
        //Pas scouté
        if(this.alreadyScouted == false) {
            return true;
        }
        //deja scouté
        else return false;
    }

    //TODO Passer de true a false quand on scout !
    /**
     * Doit etre explorer ?
     * @return true/false
     */
    public boolean hasToExplore() {
        //Pas exploré
        if (this.alreadyExplored == false) {
            return true;
        }
        //deja exploré
        else return false;
    }

    /*
        Getters
     */
    public boolean isAlreadyScouted() {
        return this.alreadyScouted;
    }

    public boolean isAlreadyExplored() {
        return this.alreadyExplored;
    }

    public int getAltitude() {
        return altitude;
    }

    /*
        Setters
     */
    public void setAlreadyScouted(boolean alreadyScouted) {
        this.alreadyScouted = alreadyScouted;
    }

    public void setAlreadyExplored(boolean alreadyExplored) {
        this.alreadyExplored = alreadyExplored;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
