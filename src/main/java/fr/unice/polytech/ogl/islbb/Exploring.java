package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ulysse on 04/03/15.
 */
public class Exploring {

    private String status;
    private int cost;
    private JSONObject extras;
    private ArrayList<String> resource , amount , cond , kind , id = new ArrayList<String>();

    public Exploring()
    {
        //initialiser le tableau

    }

    public JSONObject explore()
    {
        JSONObject obj = new JSONObject();
        obj.put("action", "explore");
        return obj;
    }

    public void retrive(JSONObject obj)
    {
        status = obj.getString("status");
        cost = obj.getInt("cost");
        extras = obj.getJSONObject("extras");

        JSONArray arr = extras.getJSONArray("resources");
        JSONArray arr1 = extras.getJSONArray("pois");

        for (int i = 0; i < arr.length(); i++) {
            resource.add(arr.getJSONObject(i).getString("resource"));
            amount.add(arr.getJSONObject(i).getString("amount"));
            cond.add(arr.getJSONObject(i).getString("cond"));
        }

        for (int i = 0; i < arr1.length(); i++) {
            kind.add(arr.getJSONObject(i).getString("kind"));
            id.add(arr.getJSONObject(i).getString("id"));
        }

    }

    public void setCost(int extras) {
        this.cost = extras;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExtras(JSONObject extras) {
        this.extras = extras;
    }

    public void setAmount(ArrayList<String> amount) {
        this.amount = amount;
    }

    public void setCond(ArrayList<String> cond) {
        this.cond = cond;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }

    public void setKind(ArrayList<String> kind) {
        this.kind = kind;
    }

    public void setResource(ArrayList<String> resource) {
        this.resource = resource;
    }

    public int getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getAmount() {
        return amount;
    }

    public ArrayList<String> getCond() {
        return cond;
    }

    public ArrayList<String> getId() {
        return id;
    }

    public ArrayList<String> getKind() {
        return kind;
    }

    public ArrayList<String> getResource() {
        return resource;
    }
}
