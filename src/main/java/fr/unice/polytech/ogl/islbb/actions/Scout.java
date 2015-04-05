package fr.unice.polytech.ogl.islbb.actions;

import fr.unice.polytech.ogl.islbb.Data;
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
        par.put("direction", dir);
        obj.put("action", "scout");
        obj.put("parameters", par);
        return obj.toString();
    }

    public static String scout(int dir) {

        Data directions = new Data();

        JSONObject obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction", directions.getCardinaux(dir));
        obj.put("action", "scout");
        obj.put("parameters", par);
        return obj.toString();
    }

    public static String scout(int dir, String debug) {

        Data directions = new Data();

        JSONObject obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction", directions.getCardinaux(dir));
        obj.put("action", "scout");
        obj.put("parameters", par);
        obj.put("debug", debug);
        return obj.toString();

    }
}
