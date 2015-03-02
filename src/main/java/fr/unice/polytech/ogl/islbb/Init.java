package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ulysse RICCIO on 02/03/15.
 */
public class Init {

    private int budget , men, amount;
    private String creek, ressource;

    public Init(String str)
    {
        JSONObject obj = new JSONObject(str);
        creek = obj.getString("creek");
        budget = obj.getInt("budget");
        men = obj.getInt("men");
        JSONArray arr = obj.getJSONArray("objective");
            System.out.println(arr.getJSONObject(0).getString("resource"));
            System.out.println(arr.getJSONObject(0).getInt("amount"));

    }

    public JSONObject StrToJSON(String str)
    {
        JSONObject obj1 = new JSONObject(str);
        return obj1;
    }
}
