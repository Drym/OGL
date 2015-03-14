package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ulysse RICCIO on 02/03/15.
 * Classe permetant d'initaliser les informations envoyées par le serveur : budget, men, creek, resource ...
 */
public class Initialization {

    private int budget , men;
    private String creek;
    private ArrayList<String> resource = new ArrayList<String>();
    private ArrayList<Integer> amount = new ArrayList<Integer>();

    public Initialization(String str) {
        JSONObject obj = new JSONObject(str);
        creek = obj.getString("creek");
        budget = obj.getInt("budget");
        men = obj.getInt("men");
        JSONArray arr = obj.getJSONArray("objective");
        for (int i = 0; i < arr.length(); i++) {
            resource.add(arr.getJSONObject(i).getString("resource"));
            amount.add(arr.getJSONObject(i).getInt("amount"));
        }
    }

    public JSONObject StrToJSON(String str) {
        JSONObject obj1 = new JSONObject(str);
        return obj1;
    }

    public int getBudget()
    {
        return budget;
    }

    public int getMen()
    {
        return men;
    }

    public ArrayList<Integer> getAmount()
    {
        return amount;
    }

    public String getCreek()
    {
        return creek;
    }

    public ArrayList<String> getResource()
    {
        return resource;
    }

    public void setBudget(int m)
    {
        budget = m;
    }

    public void setMen( int m)
    {
        men = m;
    }

    public void setAmount(ArrayList<Integer> m ) { amount = m; }

    public void setCreek( String m)
    {
        creek = m;
    }

    public void setResource(ArrayList<String> m)
    {
        resource = m;
    }

    /**
     * Retourne le résultat de la déserialisation du JSON d'initialisation donné par l'arène au lancement, dans une forme plus lisible.
     * @return
     */
    public String initDesc() {
        String desc = "Creek ID: " + this.creek + "\nAction points: " + this.budget + "\nMens available: " + this.men + "\nObjectives:";
        for (int i = 0 ; i < this.resource.size() ; i++) {
            desc += "\n" + this.resource.get(i) + ": " + this.amount.get(i);
        }
        desc += "\n";

        return desc;
    }

}