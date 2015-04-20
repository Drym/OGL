package fr.unice.polytech.ogl.islbb.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 23/03/15.
 */
public class Objective {

    private int amount;
    private String objective;

    public Objective(String obj , int number) {
        amount = number;
        objective = obj;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getObjective() {
        return this.objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public boolean updateAmount(int exploit)
    {
        amount -= exploit;
        if (amount <= 0) {
            return true;
        }
        return false;
    }

    public static List<Objective> buildObjectives(List<String> res, List<Integer> amount){
        List<Objective>list=new ArrayList<Objective>();
        int i;
        for(i=0;i<res.size();i++){
            Objective nouv = new Objective(res.get(i),amount.get(i));
            list.add(nouv);
        }
        return list;
    }

    public static void print(List<Objective> list){
        for(Objective obj:list){
            System.out.println(obj.getObjective()+obj.getAmount());
        }
    }

    @Override
    public String toString() {
        return this.objective + ": " + this.amount;
    }
}

