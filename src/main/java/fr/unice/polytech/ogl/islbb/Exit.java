package main.java.fr.unice.polytech.ogl.islbb;

import org.json.JSONObject;

/**
 * Created by Lucas on 02/03/2015.
 * Classe permetant d'automatiser l'action exit pour l'explorer.
 */
public final class Exit extends Command {

    public static JSONObject exit(){
        JSONObject obj = new JSONObject();
        obj.put("action", "stop");

        return obj;
    }
}
