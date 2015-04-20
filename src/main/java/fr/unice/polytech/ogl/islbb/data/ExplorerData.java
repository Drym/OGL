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

    protected boolean starting;
    protected boolean hasObjective;
    protected boolean inMovement;

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

}
