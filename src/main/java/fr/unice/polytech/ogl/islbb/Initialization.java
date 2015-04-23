package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ulysse RICCIO on 02/03/15.
 * Classe permetant d'initaliser les informations envoyées par le serveur : budget, men, creek, resources ...
 */
public class Initialization {

    private int budget , men;
    private String creek;
    private List<String> resources = new ArrayList<String>();
    private List<Integer> amounts = new ArrayList<Integer>();

    public Initialization(String str) {
        JSONObject obj = new JSONObject(str);
        creek = obj.getString("creek");
        budget = obj.getInt("budget");
        men = obj.getInt("men");
        JSONArray arr = obj.getJSONArray("objective");
        for (int i = 0; i < arr.length(); i++) {
            resources.add(arr.getJSONObject(i).getString("resource"));
            amounts.add(arr.getJSONObject(i).getInt("amount"));
        }
    }

    /*
    public JSONObject StrToJSON(String str) {
        JSONObject obj1 = new JSONObject(str);
        return obj1;
    }
    */

    public int getBudget()
    {
        return budget;
    }

    public int getMen()
    {
        return men;
    }

    public List<Integer> getAmounts()
    {
        return amounts;
    }

    public String getCreek()
    {
        return creek;
    }

    public List<String> getResources()
    {
        return resources;
    }

    public String getResource(int i) {
        return this.resources.get(i);
    }

    public int getAmount(int i) {
        return this.amounts.get(i);
    }

    /*
    public void setBudget(int m)
    {
        budget = m;
    }


    public void setMen( int m)
    {
        men = m;
    }

    public void setAmounts(List<Integer> m) { amounts = m; }

    public void setCreek( String m)
    {
        creek = m;
    }

    public void setResources(List<String> m)
    {
        resources = m;
    }

    */
    /**
     * Retourne le résultat de la déserialisation du JSON d'initialisation donné par l'arène au lancement, dans une forme plus lisible.
     */
    /*
    public String initDesc() {
        String desc = "Creek ID: " + this.creek + "\nAction points: " + this.budget + "\nMens available: " + this.men + "\nObjectives:";
        for (int i = 0 ; i < this.resources.size() ; i++) {
            desc += "\n" + this.resources.get(i) + ": " + this.amounts.get(i);
        }
        desc += "\n";

        return desc;
    }
    */
}
