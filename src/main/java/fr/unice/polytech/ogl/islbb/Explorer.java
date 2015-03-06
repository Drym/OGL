package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;

public class Explorer implements IExplorerRaid {

    public Init explorerInitialization;
    public int decision;

    /**
     * Initialize the explorer with the given objectives.
     * @param context Objectives given by the arena.
     */
    @Override
	public void initialize(String context) {
        this.explorerInitialization = new Init(context);
        this.decision = 0;
	}

    /**
     * The AI of the explorer, makes his decisions.
     * @return Decision taken (JSON format).
     */
    @Override
	public String takeDecision() {
        if (this.decision == 0) {
            this.decision++;
            return Land.land(this.explorerInitialization.getCreek(), 1).toString();
        }
        else if (this.decision == 1) {
            this.decision++;
            return Scout.scout("N").toString();
        }
        this.decision = 0;
        return Exit.exit().toString();
	}

    /**
     * Get the results returned from the arena, regarding the last decision executed.
     * @param results Results given by the arena.
     */
    @Override
	public void acknowledgeResults(String results) {
		//Nothing for the moment.
	}



}