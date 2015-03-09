package fr.unice.polytech.ogl.islbb;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by Thibault on 02/03/2015.
 * Classe permetant d'automatiser l'action scout pour l'explorer.
 */
public final class Scout {
    public Scout() {
    }
    public static JSONObject scout(String dir){
        JSONObject obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction",dir);
        obj.put("action", "scoot");
        obj.put("parameters",par);
        return obj;
    }
}
