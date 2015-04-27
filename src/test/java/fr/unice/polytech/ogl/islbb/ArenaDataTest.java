package fr.unice.polytech.ogl.islbb;

import com.atlassian.clover.reporters.json.JSONObject;
import fr.unice.polytech.ogl.islbb.data.ArenaData;
import fr.unice.polytech.ogl.islbb.data.ExplorerData;
import fr.unice.polytech.ogl.islbb.data.ExplorerDecision;
import fr.unice.polytech.ogl.islbb.data.TileProcessing;
import fr.unice.polytech.ogl.islbb.reports.IslandMap;
import static org.junit.Assert.assertEquals;

import fr.unice.polytech.ogl.islbb.reports.IslandTile;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Lucas on 26/04/2015.
 * Classe test de ArenaData
 */
public class ArenaDataTest {

    ArenaData arenaData;
    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    String resultClasic = "{\"status\":\"OK\",\"cost\":1}";
    String resultExplore = "{\"cost\": 1,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"FISH\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";
    String resultScout = "{\"status\":\"OK\",\"cost\":1,\"extras\":{\"resources\":[\"FISH\"],\"altitude\":23}}";
    String resultExploit = "{\"status\":\"OK\",\"cost\":1,\"extras\":{\"amount\":300}}";
    String resultGlimpse = "{\"cost\":39,\"extras\":{\"asked_range\":4,\"report\":[[[\"MANGROVE\",80],[\"BEACH\",20]],[[\"MANGROVE\",80],[\"BEACH\",20]],[\"TROPICAL_RAIN_FOREST\",\"OTHER\"],[\"TROPICAL_RAIN_FOREST\"]]},\"status\":\"OK\"}";

    @Test
    public void testArenaData() {

        arenaData = new ArenaData(init);

        //land
        arenaData.getExplorerInformation().setLastDecision("land");
        arenaData.update(resultClasic);
        assertEquals(arenaData.getExplorerInformation().getX(), 0);
        assertEquals(arenaData.getExplorerInformation().getY(), 0);

        //exit
        arenaData.getExplorerInformation().setLastDecision("exit");
        arenaData.update(resultClasic);

        //scout (les tests de scout sont effectués plus bas. Ici je scout pour le move)
        arenaData.getExplorerInformation().setLastDecision("scout");
        arenaData.update(resultScout);

        //Stcok le budget pour faire un test
        int budget = arenaData.getExplorerInformation().getAvailableBudget();

        //move
        arenaData.getExplorerInformation().setLastDecision("move");
        arenaData.update(resultClasic);
        assertEquals(arenaData.getArenaMap().getVisit(0, 1), 1);
        assertEquals(arenaData.getArenaMap().getVisit(-1, 1), -1);
        assertEquals(arenaData.getExplorerInformation().getX(), 0);
        assertEquals(arenaData.getExplorerInformation().getY(), 1);
        //Move, coutant 1, test de budget
        assertEquals(arenaData.getExplorerInformation().getAvailableBudget(), budget - 1);

        //explore, ne vas rien faire(au niveau de l'enregistrement), la case étant déjà enregistrer
        arenaData.getExplorerInformation().setLastDecision("explore");
        arenaData.update(resultExplore);
        assertEquals(arenaData.getArenaMap().isAlreadyExplored(0, 1), true);

        //Je fais donc un move, plus un explore, pour voir si la case s'enregistre bien
        assertEquals(arenaData.getArenaMap().isAlreadyExplored(0, 2), false);
        arenaData.getExplorerInformation().setLastDecision("move");
        arenaData.update(resultClasic);
        arenaData.getExplorerInformation().setLastDecision("explore");
        arenaData.update(resultExplore);

        assertEquals(arenaData.getArenaMap().isAlreadyExplored(0, 2), true);
        assertEquals(arenaData.getArenaMap().getVisit(0, 1), 1);

        //exploit
        assertEquals(arenaData.getExplorerInformation().getObjectives().size(), 2);
        assertEquals(arenaData.getExplorerInformation().getObjectives().get(1).getAmount(), 600);
        //Ajout d'un objectif
        arenaData.getExplorerInformation().setExploitedObjective("FISH");
        arenaData.getExplorerInformation().setLastDecision("exploit");
        arenaData.update(resultExploit);

        //L'amount, passe bien de 600 à 300
        assertEquals(arenaData.getExplorerInformation().getObjectives().get(1).getAmount(), 300);

        arenaData.getExplorerInformation().setLastDecision("exploit");
        arenaData.update(resultExploit);

        //La liste devint de taille 1, en effet l'objectif FISH est fini
        assertEquals(arenaData.getExplorerInformation().getObjectives().size(), 1);

    }

    @Test public void testArenaDataScout() {

        arenaData = new ArenaData(init);

        //scout
        arenaData.getExplorerInformation().setLastDecision("scout");
        arenaData.update(resultScout);
        //True
        assertEquals(arenaData.getArenaMap().isAlreadyScouted(0, 1), true);
        assertEquals(arenaData.getArenaMap().isRegistered(0, 1), true);
        //False
        assertEquals(arenaData.getArenaMap().isAlreadyGlimpsed(0, 1), false);
        assertEquals(arenaData.getArenaMap().isAlreadyExplored(0, 1), false);
        assertEquals(arenaData.getArenaMap().isVisited(0, 1), false);
        assertEquals(arenaData.getArenaMap().isWater(0, 1), false);

        assertEquals(arenaData.getArenaMap().getVisit(0, 1), 0);
        assertEquals(arenaData.getArenaMap().getHighestTile(0, 1), -1);
        assertEquals(arenaData.getArenaMap().getInformation(0, 1).toExploreString(), "FISH: null - null ");
        assertEquals(arenaData.getArenaMap().getInformation("0-1").toExploreString(), "FISH: null - null ");

        //cas deja scout
        arenaData.getExplorerInformation().setLastDecision("scout");
        arenaData.update(resultScout);
    }

    @Test public void testArenaDataGlimpse() {

        arenaData = new ArenaData(init);

        //scout
        arenaData.getExplorerInformation().setLastDecision("glimpse");
        arenaData.update(resultGlimpse);
        //True
        assertEquals(arenaData.getArenaMap().isAlreadyGlimpsed(0, 1), true);
        assertEquals(arenaData.getArenaMap().isRegistered(0, 1), true);
        //False
        assertEquals(arenaData.getArenaMap().isAlreadyScouted(0, 1), false);
        assertEquals(arenaData.getArenaMap().isAlreadyExplored(0, 1), false);
        assertEquals(arenaData.getArenaMap().isVisited(0, 1), false);
        assertEquals(arenaData.getArenaMap().isWater(0, 1), false);
        assertEquals(arenaData.getArenaMap().isWater(50, 1), false);

        assertEquals(arenaData.getArenaMap().getVisit(0, 1), 0);
        assertEquals(arenaData.getArenaMap().getHighestTile(0, 1), -1);

        //cas deja glimpse
        arenaData.getExplorerInformation().setLastDecision("glimpse");
        arenaData.update(resultGlimpse);

        assertEquals(arenaData.getArenaMap().firstDirectionToGlimpse(0, 1), 0);

        //TODO améliorable
        assertEquals(arenaData.getArenaMap().bestBiomeDirections(0,1).isEmpty(), false);

    }

    @Test public void testArenaDataOther() {

        arenaData = new ArenaData(init);

        arenaData.getArenaMap().addTile("0-50", null);
        assertEquals(arenaData.getArenaMap().isRegistered(0, 50), true);

        assertEquals(arenaData.getExplorerAI().computeDecision(), "{\"debug\":\"Initial landing.\",\"action\":\"land\",\"parameters\":{\"creek\":\"creek_id\",\"people\":2}}");

        //Intestable
        arenaData.getArenaMap().randomDirectionToScout(0,1);

    }
}
