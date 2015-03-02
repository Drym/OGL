package fr.unice.polytech.ogl.islbb;

import org.json.JSONObject;

/**
 * Created by user on 02/03/15.
 */
public class Init {

    private int budget , men, amount;
    private String creek, ressource;

    public Init(String str)
    {
        JSONObject obj = StrToJSON(str);
        creek = obj.getString("creek");
        budget = obj.getInt("budget");
        men = obj.getInt("men");

    }

    public JSONObject StrToJSON(String str)
    {
        JSONObject obj = new JSONObject(str);
        return obj;
    }
}
