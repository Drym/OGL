package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;
import fr.unice.polytech.ogl.islbb.actions.*;

/**
 * Classe qui implemente les différentes actions du robot
 */
public class Explorer implements IExplorerRaid {

    public Boolean hasMoved;
    public Init explorerInitialization;
    public int decision;
    public Data data;
    public Boolean hasScouted;
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
        hasMoved=false;
        decision = 0;
        hasScouted = false;
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
        if(hasMoved&&comport.hasWood(direction)){
            hasMoved=false;
            decision++;
            return Exploit.exploit("WOOD").toString();
        }
        hasMoved=false;
        if(card<4){
            hasScouted =false;
        }
        if (this.decision == NB_MOVE_MAX) {
            return Exit.exit().toString();
        }
        if (hasScouted) {
            decision++;
            card = 0;
            hasMoved=true;
            //On stock la dernière direction
            lastDirection = direction;
            return Move.move(direction).toString();
        }
        if (!hasScouted) {
            decision++;
            card++;
            /*
            if(lastDirection.equals((data.getCardinaux(card)))) {
                return null;
            }
            */
            return Scout.scout(data.getCardinaux(card - 1));
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
            if(!hasScouted) {
                comport.setObj(results);
                comport.getScout(results,data.getCardinaux(card - 1));
                if(card>=4){
                    hasScouted =true;
                }
            }
            if(hasScouted &&!hasMoved){
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
            if (comport.getExploitAmount() > 0 ) {
                resultExploit = resultExploit + comport.getExploitAmount();
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