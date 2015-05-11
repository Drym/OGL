package fr.unice.polytech.ogl.islbb.actions;

import org.json.JSONObject;

/**
 * Created by Ulysse on 11/05/15.
 */
public class Transform {



    public static String transform (String aResource,int aQuantity , String bResource , int bQuantity , String debug) {
        JSONObject obj = new JSONObject();
        JSONObject resource = new JSONObject();
        resource.put(bResource,bQuantity);
        resource.put(aResource,aQuantity);
        obj.put("action", "transform");
        obj.put("parameters",resource);
        obj.put("debug", debug);
        return obj.toString();
    }



    public static String transform (String aResource,int aQuantity ,  String debug) {
        JSONObject obj = new JSONObject();
        JSONObject resource = new JSONObject();
        resource.put(aResource,aQuantity);
        obj.put("action", "transform");
        obj.put("parameters",resource);
        obj.put("debug", debug);
        return obj.toString();
    }

}
