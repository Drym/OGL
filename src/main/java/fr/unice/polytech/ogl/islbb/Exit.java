package fr.unice.polytech.ogl.islbb;

import org.json.JSONObject;

/**
 * Created by Lucas on 02/03/2015.
 */
public final class Exit {

    public static JSONObject exit(){
        JSONObject obj = new JSONObject();
        obj.put("action", "stop");

        return obj;
    }
}