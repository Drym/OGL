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
    static public int NB_MOVE_MAX = 20;
    public String lastDirection;
    public int resultExploit = 0;
    public Init init;
    //Montant de la première ressource a exploiter pour finir la mission
    public int exploitMission1 =  init.getAmount().get(0);

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
        //Si nous avons récolté toutes les ressources demandées
        if(resultExploit == exploitMission1) {
            return Exit.exit().toString();
        }
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
        if (this.decision == NB_MOVE_MAX) {
            return Exit.exit().toString();
        }
        if (hascout) {
            decision++;
            card = 0;
            hasmoove=true;
            //On stock la dernière direction
            lastDirection = direction;
            return Move.move(direction).toString();
        }
        if (!hascout) {
            decision++;
            card++;
            /*
            if(lastDirection.equals((data.getCardinaux(card)))) {
                return null;
            }
            */
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
        if (this.decision != 1) {
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
            /*
            if(hasmoove) {
                comport.setObj(results);
                //Vrai, c'est bien déplacé
                if(comport.getStatus()) {
                    setClassDirection(direction);
                }
            }
            */
            //Compteur de ressource exploité au total
            if (comport.Exploitresult() > 0 ) {
                resultExploit = resultExploit + comport.Exploitresult();
            }
        }

    }

    /**
     * @Author Lucas
     */
    public void setClassDirection(String direction) {

    }

    /**
     * @Author Lucas
     */
    public void getClassDirection() {

    }
}