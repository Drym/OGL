package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;
import fr.unice.polytech.ogl.islbb.actions.*;

/**
 * Classe qui implemente les différentes actions du robot
 */
public class Explorer implements IExplorerRaid {
    public Boolean hasmoove;
    public Init explorerInitialization;
    public int decision;
    public Data data;
    public Boolean hascout;
    public int card;
    public Comportement comport;
    String direction;

    /**
     * Initialize the explorer with the given objectives.
     *
     * @param context Objectives given by the arena.
     */
    @Override
    public void initialize(String context) {
        data = new Data();
        hasmoove=false;
        decision = 0;
        hascout = false;
        comport = new Comportement();
        card = 0;
        this.explorerInitialization = new Init(context);
        this.decision = 0;
        direction="N";
    }

    /**
     * The AI of the explorer, makes his decisions.
     *
     * @return Decision taken (JSON format).
     */
    @Override
    public String takeDecision() {
        if (this.decision == 0) {
            this.decision++;
            return Land.land(this.explorerInitialization.getCreek(), 1).toString();
        }
        if(hasmoove&&comport.haswood(direction)){
            hasmoove=false;
            decision++;
            return Exploiting.exploit("WOOD").toString();
        }
        hasmoove=false;
        if(card<4){
            hascout=false;
        }
        if (this.decision == 20) {
            return Exit.exit().toString();
        }
        if (hascout) {
            decision++;
            card = 0;
            hasmoove=true;
            return Move.move(direction).toString();
        }
        if (!hascout) {
            decision++;
            card++;
            return Scout.scout(data.getCardinaux(card - 1)).toString();
        }
        return Exit.exit().toString();
    }

    /**
     * Get the results returned from the arena, regarding the last decision executed.
     *
     * @param results Results given by the arena.
     */
    @Override
    public void acknowledgeResults(String results) {
        if (this.decision != 0) {
            if(!hascout) {
                comport.setObj(results);
                comport.getscout(results,data.getCardinaux(card - 1));
                if(card>=4){
                    hascout=true;
                }
            }
            if(hascout&&!hasmoove){
                direction=comport.takeDirection();
            }
        }

    }
}