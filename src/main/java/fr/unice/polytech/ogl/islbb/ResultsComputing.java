package fr.unice.polytech.ogl.islbb;

import org.json.*;

/**
 * Created by Quentin WEPHRE on 11/03/2015.
 *
 * Cette classe sert à définir des méthodes pour étudier les retours de l'arène.
 */
public class ResultsComputing {

    /**
     * Indique si la dernière action s'est bien réalisée.
     */
    public static boolean getStatus(String results) {
        JSONObject JSONResult =  new JSONObject(results);
        return JSONResult.getString("status").equals("OK");
    }

    /**
     * Indique le décalage en X pour la direction donnée.
     */
    public static int xOffset(int direction) {
        if (direction == 0) {
            return 0;
        }
        else if (direction == 1) {
           return 1;
        }
        else if (direction == 2) {
            return 0;
        }
        else {
            return -1;
        }
    }


    /**
     * Indique le décalage en Y pour la direction donnée.
     */
    public static int yOffset(int direction) {
        if (direction == 0) {
            return 1;
        }
        else if (direction == 1) {
            return 0;
        }
        else if (direction == 2) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /**
     * Indique la quantité de ressource en fonction de la String retournée par l'arène.
     */
    public static int parseAmount(String resourceAmount) {
        if (resourceAmount.equals("LOW")) {
            return 0;
        }
        else if (resourceAmount.equals("MEDIUM")) {
            return 1;
        }
        else if (resourceAmount.equals("HIGH")) {
            return 2;
        }
        else {
            return -1;
        }
    }

}
