package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;
import org.json.JSONObject;

public class Explorer implements IExplorerRaid {

    /**
     * Initialize the explorer with the given objectives.
     * @param context Objectives given by the arena.
     */
    @Override
	public void initialize(String context) {
	    // Nothing for the moment.
	}

    /**
     * The AI of the explorer, makes his decisions.
     * @return Decision taken (JSON format).
     */
    @Override
	public String takeDecision() {
		String decision = "{ \"action\": \"stop\" }";
		return decision;
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