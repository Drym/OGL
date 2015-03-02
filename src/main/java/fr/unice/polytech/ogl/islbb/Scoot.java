package fr.unice.polytech.ogl.islbb;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by user on 02/03/2015.
 */
public final class Scoot {
    public Scoot() {
    }
    public static JSONObject scoot(String dir){
        JSONObject obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction",dir);
        obj.put("action", "scoot");
        obj.put("parameters",par);
        return obj;
    }
}
