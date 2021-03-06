package fr.unice.polytech.ogl.islbb.data;

import fr.unice.polytech.ogl.islbb.Initialization;
import fr.unice.polytech.ogl.islbb.ResultsComputing;
import fr.unice.polytech.ogl.islbb.reports.Objective;

import java.util.List;

/**
 * Created by QWPHR on 20/04/2015.
 */
public class ExplorerData {

    protected int x, y, altitude;
    protected int lastScoutDirection;
    protected int lastGlimpseDirection;
    protected int lastMoveDirection;

    protected String lastDecision;
    protected String exploitedObjective;
    protected String transformedObjective;

    protected boolean starting;
    protected boolean hasObjective;
    protected boolean inMovement;
    protected boolean escaping;
    protected boolean restart;
    protected int fuckingDirection;

    protected int escapeMovementCount;

    protected String startCreek;
    protected int availableBudget;
    protected int availableMen;
    protected List<Objective> objectives;

    public ExplorerData(Initialization contract) {
        this.x = 0;
        this.y = 0;
        this.altitude = 0;

        this.starting = true;
        this.hasObjective = false;
        this.inMovement = false;

        this.startCreek = contract.getCreek();
        this.availableBudget = contract.getBudget();
        this.availableMen = contract.getMen();
        this.objectives = Objective.buildObjectives(contract.getResources(), contract.getAmounts());

        this.escaping = false;
        this.restart = false;
        this.fuckingDirection = 0;

    }

    public void decreaseCost(int cost) {
        this.availableBudget -= cost;
    }

    public void updatePosition() {

        this.x += ResultsComputing.xOffset(this.lastMoveDirection);
        this.y += ResultsComputing.yOffset(this.lastMoveDirection);

    }

    public void updateAltitude(int newAltitude) {

        this.altitude = newAltitude;

    }

    /* Getters */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAltitude() {
        return altitude;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public int getAvailableMen() {
        return availableMen;
    }

    public int getAvailableBudget() {
        return availableBudget;
    }

    public String getStartCreek() {
        return startCreek;
    }

    public boolean isInMovement() {
        return inMovement;
    }

    public boolean isHasObjective() {
        return hasObjective;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public String getTransformedObjective() {
        return transformedObjective;
    }

    public void setExploitedObjective(String exploitedObjective) {
        this.exploitedObjective = exploitedObjective;
    }

    public void setTransformedObjective(String transformedObjective) { this.transformedObjective = transformedObjective; }

    public void setStartCreek(String startCreek) {
        this.startCreek = startCreek;
    }
}
