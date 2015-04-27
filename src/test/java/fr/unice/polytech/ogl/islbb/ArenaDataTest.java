package fr.unice.polytech.ogl.islbb;

import com.atlassian.clover.reporters.json.JSONObject;
import fr.unice.polytech.ogl.islbb.data.ArenaData;
import fr.unice.polytech.ogl.islbb.data.ExplorerData;
import fr.unice.polytech.ogl.islbb.data.TileProcessing;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Lucas on 26/04/2015.
 * Classe test de ArenaData
 */
public class ArenaDataTest {

    ArenaData test;
    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    String resultland = "{\"status\":\"OK\",\"cost\":12}";

    @Test
    public void testArenaData() {

        test = new ArenaData(init);

        System.out.println(test.getExplorerAI().computeDecision());

        test.update(resultland);


    }
}
