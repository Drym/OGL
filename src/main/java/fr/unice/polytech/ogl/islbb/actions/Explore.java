package fr.unice.polytech.ogl.islbb.actions;

import fr.unice.polytech.ogl.islbb.Command;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ulysse on 04/03/15.
 * Classe permetant d'automatiser l'action exploring pour l'explorer.
 */
public class Explore extends Command {

    public static String explore() {
        JSONObject obj = new JSONObject();
        obj.put("action", "explore");
        return obj.toString();
    }

}
