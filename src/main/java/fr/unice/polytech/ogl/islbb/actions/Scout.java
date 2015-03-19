package fr.unice.polytech.ogl.islbb.actions;

import org.json.JSONObject;
import java.util.LinkedHashMap;

/**
 * Created by Thibault on 02/03/2015.
 * Classe permetant d'automatiser l'action scout pour l'explorer.
 */
public final class Scout {

    public static String scout(String dir) {
        JSONObject obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction",dir);
        obj.put("action", "scout");
        obj.put("parameters",par);
        return obj.toString();
    }

}
