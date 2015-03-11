package fr.unice.polytech.ogl.islbb.reports;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Stocke les données sur un Point of Interest (POI).
 */
public class POI {

    private String type;
    private String id;

    public POI(String exploredType, String exploredID) {
        this.type = exploredType;
        this.id = exploredID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
