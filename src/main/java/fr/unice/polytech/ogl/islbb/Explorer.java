package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;

import fr.unice.polytech.ogl.islbb.data.ArenaData;
import fr.unice.polytech.ogl.islbb.data.ExplorerDecision;

/**
 * Classe qui implemente les différentes actions du robot
 */

public class Explorer implements IExplorerRaid {

    // NEW MODULAR VERSION

    private ArenaData islandData;
    private ExplorerDecision explorerAI;

    /**
     * Initialize the explorer with the given objectives.
     *
     * @param context Objectives given by the arena.
     */

    @Override
    public void initialize(String context) {

        // NEW MODULAR VERSION

        this.islandData = new ArenaData(context);
        this.explorerAI = this.islandData.getExplorerAI();

    }

    /**
     * The AI of the explorer, makes his decisions.
     *
     * @return Decision taken (JSON format).
     */

    @Override
    public String takeDecision() {

        // NEW MODULAR VERSION

        return this.explorerAI.computeDecision();

    }

    /**
     * Get the results returned from the arena, regarding the last decision executed.
     *
     * @param results Results given by the arena.
     */

    @Override
    public void acknowledgeResults(String results) {

        // NEW MODULAR VERSION

        this.islandData.update(results);


    }

}
