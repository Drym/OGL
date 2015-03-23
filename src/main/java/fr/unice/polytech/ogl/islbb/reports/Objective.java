package fr.unice.polytech.ogl.islbb.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 23/03/15.
 */
public class Objective {

    private int amount;
    private String objective;

    Objective(String obj , int number ) {
        amount = number;
        objective = obj;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }


    public static List<Objective> buildobjectives(List<String> res,List<Integer>amount ){
        List<Objective>list=new ArrayList<Objective>();
        int i;
        for(i=0;i<res.size();i++){
            Objective nouv=new Objective(res.get(i),amount.get(i));
            list.add(nouv);
        }
        return list;
    }
}

