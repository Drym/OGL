package fr.unice.polytech.ogl.islbb.actions;

import org.json.JSONObject;

/**
 * Created by Lucas on 02/03/2015.
 * Classe permetant d'automatiser l'action exit pour l'explorer.
 */
public final class Exit {

    public static String exit(){
        JSONObject obj = new JSONObject();
        obj.put("action", "stop");

        return obj.toString();
    }

    public static String exit(String debug){
        JSONObject obj = new JSONObject();
        obj.put("action", "stop");
        obj.put("debug", debug);

        return obj.toString();
    }

}
