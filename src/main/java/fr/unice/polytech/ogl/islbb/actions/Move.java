package fr.unice.polytech.ogl.islbb.actions;

import fr.unice.polytech.ogl.islbb.Data;
import org.json.JSONObject;

/**
 * Created by Thibault on 02/03/2015.
 * Classe permetant d'automatiser l'action move pour l'explorer.
 */
public final class Move {

    public static String move(String dir) {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj2.put("direction", dir);
        obj.put("action", "move_to");
        obj.put("parameters", obj2);
        return obj.toString();
    }

    public static String move(int dir) {

        Data directions = new Data();

        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj2.put("direction", directions.getCardinaux(dir));
        obj.put("action", "move_to");
        obj.put("parameters", obj2);
        return obj.toString();
    }

}
