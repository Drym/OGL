package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;

public class Explorer implements IExplorerRaid {
	/*
	initialise notre explorateur
	 */
    @Override
	public void initialize(String context) {
	//ne fait rien
	}
	/*
	Décide de la  marche à suivre
	 */
    @Override
	public String takeDecision() {
		String fin="{ \"action\": \"stop\" }";
		return fin;
	}
	/*
	traite les informations retournées par l'action en cours
	 */
    @Override
	public void acknowledgeResults(String results) {
		//ne fait rien
	}
}