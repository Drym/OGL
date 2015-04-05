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

    public static String explore(String debug) {
        JSONObject obj = new JSONObject();
        obj.put("action", "explore");
        obj.put("debug", debug);
        return obj.toString();
    }

}
