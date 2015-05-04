package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.data.ArenaData;
import fr.unice.polytech.ogl.islbb.data.ExplorerData;
import fr.unice.polytech.ogl.islbb.data.ExplorerDecision;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Lucas on 26/04/2015.
 * Classe test de ExplorerDecision
 */
public class ExplorerDecisionTest {

    ExplorerData explorerCurrentState;
    ArenaData arenaData;
    ExplorerDecision test;
    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    String initOutOfBudget = "{\"creek\":\"creek_id\", \"budget\":0,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    Initialization init2;
    String land = "{\"debug\":\"Initial landing.\",\"action\":\"land\",\"parameters\":{\"creek\":\"creek_id\",\"people\":2}}";
    String land2 = "{\"debug\":\"Exploring first tile.\",\"action\":\"explore\"}";
    String explore = "{\"debug\":\"Exploring first tile.\",\"action\":\"explore\"}";
    String exit = "{\"debug\":\"No creek given at initialization, stopping...\",\"action\":\"stop\"}";
    String exit2 = "{\"debug\":\"Not enough budget, distance:0\",\"action\":\"stop\"}";
    String glimpse = "{\"debug\":\"Continuing to glimpsing, direction: 0 | current: false | N: false | E: false | S: false | W: false\",\"action\":\"glimpse\",\"parameters\":{\"range\":2,\"direction\":\"N\"}}";
    String glimpseE = "{\"debug\":\"Continuing to glimpsing, direction: 1 | current: true | N: true | E: false | S: false | W: false\",\"action\":\"glimpse\",\"parameters\":{\"range\":2,\"direction\":\"E\"}}";
    String scoutAfterMove = "{\"debug\":\"After moving because nothing interesting was found, restart scouting.\",\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}";
    String moveAfterScoutSucess = "{\"debug\":\"If after scouting a possible objective has been found, moving to it.\",\"action\":\"move_to\",\"parameters\":{\"direction\":\"N\"}}";
    String resultExplore = "{\"cost\": 1,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"WOOD\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploit = "{\"debug\":\"Exploit objective present enough on initial case.\",\"action\":\"exploit\",\"parameters\":{\"resource\":\"WOOD\"}}";
    String resultExploit = "{\"status\":\"OK\",\"cost\":1,\"extras\":{\"amount\":300}}";
    String resultScout = "{\"status\":\"OK\",\"cost\":1,\"extras\":{\"resources\":[\"WOOD\"],\"altitude\":23}}";
    String resultScoutFail = "{\"status\":\"OK\",\"cost\":1,\"extras\":{\"resources\":[\"FLOOR\"],\"altitude\":23}}";
    String moveAfterScout = "{\"debug\":\"If after scouting a possible objective has been found, moving to it.\",\"action\":\"move_to\",\"parameters\":{\"direction\":\"N\"}}";
    String scoutE = "{\"debug\":\"Scouting next tile, direction: 1 | current: false | N: true | E: false | S: false | W: false\",\"action\":\"scout\",\"parameters\":{\"direction\":\"E\"}}";
    String resultGlimpse = "{\"cost\":39,\"extras\":{\"asked_range\":4,\"report\":[[[\"MANGROVE\",80],[\"BEACH\",20]],[[\"MANGROVE\",80],[\"BEACH\",20]],[\"TROPICAL_RAIN_FOREST\",\"OTHER\"],[\"TROPICAL_RAIN_FOREST\"]]},\"status\":\"OK\"}";


    @Test
    public void testExplorerDecision() {

        //Initialisation
        init2 = new Initialization(init);
        explorerCurrentState = new ExplorerData(init2);
        arenaData = new ArenaData(init);

        test = new ExplorerDecision(explorerCurrentState, arenaData);

        //Tests

        //null, revient a land
        test.getExplorerCurrentState().setLastDecision(null);
        assertEquals(test.computeDecision(), land);

        //Doit explore la case d'arrivé apres un land
        assertEquals(test.computeDecision(), explore);

        //Exit(pas de creek)
        test.getExplorerCurrentState().setStartCreek(null);
        test.getExplorerCurrentState().setLastDecision(null);
        assertEquals(test.computeDecision(), exit);

        test.getExplorerCurrentState().setStartCreek("creek_id");

        //Land
        test.getExplorerCurrentState().setLastDecision("land");
        assertEquals(test.computeDecision(), land2);

        //Move for scout
        test.getExplorerCurrentState().setLastDecision("move");
        assertEquals(test.computeDecision(), scoutAfterMove);

        //explore
        arenaData.getExplorerInformation().setLastDecision("explore");
        arenaData.update(resultExplore);
        test.getExplorerCurrentState().setLastDecision("explore");
        assertEquals(test.computeDecision(), exploit);

        //exploit
        arenaData.getExplorerInformation().setLastDecision("exploit");
        arenaData.update(resultExploit);
        test.getExplorerCurrentState().setLastDecision("exploit");
        assertEquals(test.computeDecision(), exploit);
    }

    /**
     * Test si l'explorer prend les bonnes decisions quand il n'a plus de budget
     */
    @Test
    public void testExplorerDecisionOutOfBudget() {

        //Initialisation
        init2 = new Initialization(initOutOfBudget);
        explorerCurrentState = new ExplorerData(init2);
        arenaData = new ArenaData(initOutOfBudget);

        test = new ExplorerDecision(explorerCurrentState, arenaData);

        //Test
        test.getExplorerCurrentState().setLastDecision("scout");
        assertEquals(test.computeDecision(), exit2);
    }

    /**
     * Test si l'explorer prendre les bonnes decision après un scout
     */
    @Test
    public void testExplorerDecisionScout() {

        //Initialisation
        init2 = new Initialization(init);
        explorerCurrentState = new ExplorerData(init2);
        arenaData = new ArenaData(init);

        test = new ExplorerDecision(explorerCurrentState, arenaData);

        //Tests

        //Scout then move
        arenaData.getExplorerInformation().setLastDecision("scout");
        arenaData.update(resultScout);
        test.getExplorerCurrentState().setLastDecision("scout");
        assertEquals(test.computeDecision(), moveAfterScout);

        //Scout then glimp
        arenaData.getExplorerInformation().setLastDecision("scout");
        arenaData.update(resultScoutFail);
        test.getExplorerCurrentState().setLastDecision("scout");
        assertEquals(test.computeDecision(), scoutE);
    }

    /**
     * Test si l'explorer prendre les bonnes decision après un glimpse
     */
    @Test
    public void testExplorerDecisionGlimpse() {

        //Initialisation
        init2 = new Initialization(init);
        explorerCurrentState = new ExplorerData(init2);
        arenaData = new ArenaData(init);

        test = new ExplorerDecision(explorerCurrentState, arenaData);

        //Tests

        //Glimpse
        test.getExplorerCurrentState().setLastDecision("glimpse");
        assertEquals(test.computeDecision(), glimpse);

        //Already glimpsed
        arenaData.getExplorerInformation().setLastDecision("glimpse");
        arenaData.update(resultGlimpse);
        test.getExplorerCurrentState().setLastDecision("glimpse");
        assertEquals(test.computeDecision(), glimpseE);
    }

    /**
     * Test si l'explorer prendre les bonnes decision après un move
     */
    @Test
    public void testExplorerDecisionMove() {

        //Initialisation
        init2 = new Initialization(init);
        explorerCurrentState = new ExplorerData(init2);
        arenaData = new ArenaData(init);

        test = new ExplorerDecision(explorerCurrentState, arenaData);

        //Tests

        //Move for scout
        test.getExplorerCurrentState().setLastDecision("move");
        assertEquals(test.computeDecision(), scoutAfterMove);

        //Move for explore
        arenaData.getExplorerInformation().setLastDecision("scout");
        arenaData.update(resultScout);
        test.getExplorerCurrentState().setLastDecision("scout");
        assertEquals(test.computeDecision(), moveAfterScoutSucess);
    }
}
