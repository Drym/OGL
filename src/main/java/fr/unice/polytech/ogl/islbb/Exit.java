package fr.unice.polytech.ogl.islbb;

import org.json.JSONObject;

/**
 * Created by Lucas on 02/03/2015.
 */
public class Exit {
    JSONObject obj;

    public Exit(){
        obj = new JSONObject();
        obj.put("action", "stop");
    }
}
