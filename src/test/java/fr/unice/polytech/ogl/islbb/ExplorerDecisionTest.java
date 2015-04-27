package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Explore;
import fr.unice.polytech.ogl.islbb.data.ArenaData;
import fr.unice.polytech.ogl.islbb.data.ExplorerData;
import fr.unice.polytech.ogl.islbb.data.ExplorerDecision;
import static org.junit.Assert.assertEquals;

import fr.unice.polytech.ogl.islbb.reports.Objective;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Lucas on 26/04/2015.
 * Classe test de ExplorerDecision
 */
public class ExplorerDecisionTest {

    ExplorerData explorerCurrentState;
    ArenaData islandData;
    ExplorerDecision test;
    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    Initialization init2;
    String land = "{\"debug\":\"Initial landing.\",\"action\":\"land\",\"parameters\":{\"creek\":\"creek_id\",\"people\":2}}";
    String exploring = "{\"debug\":\"Exploring first tile.\",\"action\":\"explore\"}";

    @Test
    public void testExplorerDecision() {

        init2 = new Initialization(init);
        explorerCurrentState = new ExplorerData(init2);
        islandData = new ArenaData(init);

        test = new ExplorerDecision(explorerCurrentState, islandData);

        //Land
        assertEquals(test.computeDecision(), land);
        //Explore
        assertEquals(test.computeDecision(), exploring);

        //System.out.println(test.computeDecision());



    }
}
