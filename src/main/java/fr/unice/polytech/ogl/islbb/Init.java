package fr.unice.polytech.ogl.islbb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ulysse RICCIO on 02/03/15.
 */
public class Init {

    private int budget , men;
    private String creek;
    private ArrayList<String> ressource = new ArrayList<String>();
    private ArrayList<Integer> amount = new ArrayList<Integer>();

    public Init(String str)
    {
        JSONObject obj = new JSONObject(str);
        creek = obj.getString("creek");
        budget = obj.getInt("budget");
        men = obj.getInt("men");
        JSONArray arr = obj.getJSONArray("objective");
        for (int i = 0; i < arr.length(); i++) {
            ressource.add(arr.getJSONObject(i).getString("resource"));
            amount.add(arr.getJSONObject(i).getInt("amount"));
        }
    }

    public JSONObject StrToJSON(String str)
    {
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

    public ArrayList<String> getRessource()
    {
        return ressource;
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

    public void setRessource( ArrayList<String> m)
    {
        ressource = m;
    }

    /**
     * Retourne le résultat de la déserialisation du JSON d'initialisation donné par l'arène au lancement, dans une forme plus lisible.
     * @return
     */
    public String initDesc() {
        String desc = "Creek ID: " + this.creek + "\nAction points: " + this.budget + "\nMens available: " + this.men + "\nObjectives:";
        for (int i = 0 ; i < this.ressource.size() ; i++) {
            desc += "\n" + this.ressource.get(i) + ": " + this.amount.get(i);
        }
        desc += "\n";

        return desc;
    }

}
