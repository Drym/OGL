package fr.unice.polytech.ogl.islbb.reports;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Stocke les données sur une ressource contenue dans une case.
 */
public class Resource {

    /**
     * Le type de la ressource (WOOD, FUR, FLOWER)
     * La quantité de la ressource retournée par Explore (HIGH, MEDIUM, LOW)
     * L'exploitabilité de la ressource (EASY, FAIR, HARSH)
     */
    private String type;
    private String amount;
    private String condition;

    /**
     * Méthode d'instanciation de la ressource dans le cas d'un Scout.
     * Seulement une liste de ressource, les conditions ne sont pas connues.
     */
    public Resource(String scoutedType) {
        this.type = scoutedType;
        this.amount = null;
        this.condition = null;
    }

    /**
     * Méthode d'instanciation de la ressource dans le cas d'un Explore.
     * On connaît alors les conditions de la ressource.
     */
    public Resource(String exploredType, String exploredAmount, String exploredCondition) {
        this.type = exploredType;
        this.amount = exploredAmount;
        this.condition = exploredCondition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
