package fr.unice.polytech.ogl.islbb;

import org.json.JSONObject;

/**
 * Created by user on 02/03/2015.
 */
public final class Move {
    public Move(){}
    public static JSONObject move(String dir){
        JSONObject obj=new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj2.put("direction",dir);
        obj.put("action","move_to");
        obj.put("parameters",obj2);
        return obj;
    }
}
