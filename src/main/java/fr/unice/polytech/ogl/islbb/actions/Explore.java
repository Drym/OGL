package fr.unice.polytech.ogl.islbb.actions;
import org.json.JSONObject;

/**
 * Created by Ulysse on 04/03/15.
 * Classe permetant d'automatiser l'action exploring pour l'explorer.
 */
public class Explore {

    public static String explore() {
        JSONObject obj = new JSONObject();
        obj.put("action", "explore");
        return obj.toString();
    }

}
