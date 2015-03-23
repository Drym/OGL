package fr.unice.polytech.ogl.islbb.reports;

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

}

