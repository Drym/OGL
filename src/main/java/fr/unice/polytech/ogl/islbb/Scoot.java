package fr.unice.polytech.ogl.islbb;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by user on 02/03/2015.
 */
public class Scoot {
    String direction;
    JSONObject obj;
    public Scoot(String dir){
        obj = new JSONObject();
        LinkedHashMap par = new LinkedHashMap();
        par.put("direction",dir);
        direction=dir;
        obj.put("action", "scoot");
        obj.put("parameters",par);
    }
    public JSONObject getobj(){
    return this.obj;
    }
}
