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

    private int altitude;
    private List<Resource> resources;

    public List<Resource> getResources() {
        return resources;
    }

    private List<POI> pois;
    private List<Biome> biomes;
    private int glimpsedRange;
    private int visits;
    private boolean alreadyScouted = false;
    private boolean alreadyExplored = false;
    private boolean alreadyGlimpsed = false;

    private boolean alreadyExploited = false;

    private boolean unreachable = false;

    public boolean isInterestring() {
        return interestring;
    }

    public void setInterestring(boolean interestring) {
        this.interestring = interestring;
    }

    private boolean interestring = true;



    /**
     * Méthode d'instanciation lors d'un Scout.
     */
    public IslandTile(int scoutedAltitude, List<Resource> scoutedResources, boolean isUnreachable) {

        this.altitude = scoutedAltitude;

        this.resources = scoutedResources;
        this.pois = new ArrayList<>();
        this.biomes = new ArrayList<>();

        this.glimpsedRange = -1;

        this.alreadyScouted = true;

        this.unreachable = isUnreachable;

        this.visits  = 0;
    }

    /**
     * Méthode d'instanciation lors d'un Explore.
     */
    public IslandTile(List<Resource> exploredResources, List<POI> exploredPOIs) {

        this.altitude = 0;

        this.resources = exploredResources;
        this.pois = exploredPOIs;
        this.biomes = new ArrayList<>();

        this.glimpsedRange = -1;

        this.alreadyExplored = true;

        this.visits = 0;
    }

    /**
     * Méthode d'instanciation lors d'un Glimpse.
     */
    public IslandTile(List<Biome> biomeList, int range) {

        this.altitude = 0;

        this.resources = new ArrayList<>();
        this.pois = new ArrayList<>();
        this.biomes = biomeList;

        this.glimpsedRange = range;

        this.alreadyGlimpsed = true;
        this.visits = 0;
    }

    /**
     * Méthode pour Scout une case déjà connue.
     */
    public void scoutTile(int scoutedAltitude, List<Resource> scoutedResources,  boolean isUnreachable) {

        if (this.alreadyExplored != true) {
            this.resources = scoutedResources;
        }

        this.altitude = scoutedAltitude;

        this.alreadyScouted = true;

        this.unreachable = isUnreachable;

    }

    public void scoutTile(IslandTile scoutedTile) {

        this.scoutTile(scoutedTile.altitude, scoutedTile.resources, scoutedTile.unreachable);

    }

    /**
     * Méthode pour explorer une case (rajouter les conditions des ressources).
     */
    public void exploreTile(List<Resource> exploredResources, List<POI> exploredPOIs) {

        this.resources = exploredResources;
        this.pois = exploredPOIs;

        this.alreadyExplored = true;

    }

    public void exploreTile(IslandTile exploredTile) {

        this.resources = exploredTile.resources;
        this.pois = exploredTile.pois;

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
            if (this.glimpsedRange < range) {
                this.biomes = glimpsedBiome;

                this.glimpsedRange = range;
            }
        }
    }

    public void glimpseTile(IslandTile glimpsedTile) {

        this.glimpseTile(glimpsedTile.biomes, glimpsedTile.glimpsedRange);
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
     * Méthode pour savoir si une ressource est uniquement présente sur la case.
     */
    public boolean hasOnlyResource(String aResource) {

        return ((this.hasResource(aResource) != null) && (this.resources.size() == 1));

    }
    /*
    Méthode pour savoir si une ressource parmis la liste des objectifs est uniquement présente
     */
    public boolean hasOnlyResources(List<Objective>res) {
        for (Objective obj:res){
            if((this.hasResource(obj.getObjective()) != null) && (this.resources.size() == 1)){
                return true;
            }
        }
        return false;
    }
    /**
     * Méthode pour savoir si une liste de ressources sont présentes sur la case.
     */
    public List<Resource> hasResources(List<Objective> res) {
        List<Resource> list = new ArrayList<>();
        if (this.alreadyExplored || this.alreadyScouted) {
            for (Resource currentResource : this.resources) {
                for (Objective obj : res) {
                    if (currentResource.getType().equals(obj.getObjective())) {
                        list.add(currentResource);
                    }
                }
            }
            return list;
        }
        return null;
    }

    public List<Biome> getBiomes() {
        return biomes;
    }

    /**
     * Méthode pour savoir si le biome est présent sur la case.
     */
    public Biome containBiome(String aBiome) {

        for (Biome currentBiome : this.biomes) {
            if (currentBiome.getBiome().equals(aBiome)) {
                return currentBiome;
            }
        }

        return null;
    }

    /**
     * On supprime la ressource de la case (après une exploitation)
     */
    public void removeResource(String aResource) {

        for (Resource currentResource : this.resources) {
            if (currentResource.getType().equals(aResource)) {
                this.resources.remove(currentResource);
                break;
            }
        }

    }
    public void addTileVisit(){
        this.visits++;
    }

    public int getTileVisits(){
        return this.visits;
    }
    /**
     * Est-ce que la case semble marquer le rivage de l'océan ?
     */
    public boolean isWater() {

        if (this.glimpsedRange < 2) {
            return this.getOceanPart() >= 60;
        }
        else {
            return this.containBiome("OCEAN") != null;
        }


    }

    public int getOceanPart() {

        for (Biome currentBiome : this.biomes) {
            if (currentBiome.getBiome().equals("OCEAN")) {
                return (int) currentBiome.getPercentage();
            }
        }

        return 0;
    }

    public int getAltitude() {

        return altitude;

    }


    public boolean isAlreadyScouted() {

        return this.alreadyScouted;

    }

    public boolean isAlreadyExplored() {

        return this.alreadyExplored;

    }

    public boolean isAlreadyGlimpsed() {

        return this.alreadyGlimpsed;

    }

    public void setAltitude(int altitude) {

        this.altitude = altitude;

    }

    public void setAlreadyScouted(boolean alreadyScouted) {

        this.alreadyScouted = alreadyScouted;

    }

    public void setAlreadyExplored(boolean alreadyExplored) {

        this.alreadyExplored = alreadyExplored;

    }

    public void setAlreadyGlimpsed(boolean alreadyGlimpsed) {

        this.alreadyGlimpsed = alreadyGlimpsed;
    }


    public boolean isAlreadyExploited() {

        return alreadyExploited;

    }

    public void setAlreadyExploited(boolean alreadyExploited) {

        this.alreadyExploited = alreadyExploited;

    }

    public boolean isUnreachable() {

        return this.unreachable;
    }

    public String toExploreString() {
        String result = new String();
        for (Resource curRes : this.resources) {
            result+= curRes.getType() + ": " + curRes.getAmount() + " - " + curRes.getCondition() + " ";
        }
        return result;
    }
}
