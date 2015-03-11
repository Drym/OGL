package fr.unice.polytech.ogl.islbb.actions;

import org.json.JSONObject;

/**
 * Created by Thibault on 02/03/2015.
 * Classe permetant d'automatiser l'action move pour l'explorer.
 */
public final class Move {

    public static String move(String dir) {
        JSONObject obj=new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj2.put("direction",dir);
        obj.put("action","move_to");
        obj.put("parameters",obj2);
        return obj.toString();
    }

}
