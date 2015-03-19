package fr.unice.polytech.ogl.islbb.reports;

import java.util.List;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Classe contenant les informations sur une case.
 * Sous la forme du type de Biome (à venir), la liste des ressources, la liste des POI et son altitude).
 */
public class IslandTile {

    private int altitude;
    private List<Resource> resources;
    private List<POI> pois;
    private List<Biome> biomes;
    private int glimpseRange;

    private boolean alreadyScouted = false;
    private boolean alreadyExplored = false;
    private boolean alreadyGlimpsed = false;

    /**
     * Méthode d'instanciation lors d'un Scout.
     */
    public IslandTile(int scoutedAltitude, List<Resource> scoutedResources) {
        this.altitude = scoutedAltitude;
        this.resources = scoutedResources;
        //this.pois = new ArrayList<POI>();
        this.alreadyScouted = true;
    }

    /**
     * Méthode d'instanciation lors d'un Explore.
     */
    public IslandTile(List<Resource> exploredResources, List<POI> exploredPOIs) {
        this.altitude = 0;
        this.resources = exploredResources;
        this.pois = exploredPOIs;
        this.alreadyExplored = true;
    }

    /**
     * Méthode d'instanciation lors d'un Glimpse.
     */
    public IslandTile(List<Biome> biomeList, int range) {
        this.altitude = 0;
        this.biomes = biomeList;
        this.glimpseRange = range;
        this.alreadyGlimpsed = true;
    }


    /**
     * Méthode pour explorer une case (rajouter les conditions des ressources).
     */
    public void exploreTile(List<Resource> exploredResources, List<POI> exploredPOIs) {
        this.resources = exploredResources;
        this.pois = exploredPOIs;
        this.alreadyExplored = true;
    }

    /**
     * Méthode pour Glimpse une case (rajouter les biomes).
     */
    public void glimpseTile(List<Biome> glimpsedBiome, int range) {
        if (this.alreadyGlimpsed != true) {
            this.biomes = glimpsedBiome;
            this.alreadyGlimpsed = true;
        }
        else {
            if (this.glimpseRange < range) {
                this.biomes = glimpsedBiome;
            }
        }
    }

    /**
     * Méthode pour savoir si une ressource est présente sur la case.
     */
    public Resource hasResource(String aResource) {
        if (this.alreadyExplored || this.alreadyScouted) {
            for (Resource currentResource : this.resources) {
                if (currentResource.getType().equals(aResource)) {
                    return currentResource;
                }
            }
        }
        return null;
    }

    /**
     * Méthode pour savoir si le biome est présent sur la case.
     */
    public Biome containBiome(String aBiome) {
        if (this.alreadyGlimpsed) {
            for (Biome currentBiome : this.biomes) {
                if (currentBiome.getBiome().equals(aBiome))
                    return currentBiome;
            }
        }
        return null;
    }

    /**
     * On supprime la ressource de la case (après une exploitation)
     */
    public void removeResource(String aResource) {
        if (!this.resources.isEmpty())
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
        if (this.containBiome("OCEAN") != null) {
            return true;
        }
        return false;
    }

    /**
     * La case a besoin d'être Scout ?
     * @return true/false
     */
    public boolean hasToScout() {
        return !this.alreadyScouted;
    }

    /**
     * La case a besoin d'être Explore ?
     * @return true/false
     */
    public boolean hasToExplore() {
        return !this.alreadyExplored;
    }

    public boolean hasToGlimpse() {
        return !this.alreadyScouted;
    }

    public boolean isAlreadyScouted() {
        return this.alreadyScouted;
    }

    public boolean isAlreadyExplored() {
        return this.alreadyExplored;
    }

    public int getAltitude() {
        return altitude;
    }

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
