package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.data.ExplorerData;
import fr.unice.polytech.ogl.islbb.reports.Objective;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 26/04/2015.
 * Classe test de ExplorerData
 */
public class ExplorerDataTest {

    ExplorerData test;
    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    Initialization init2;


    @Test public void testExplorerData() {

        init2 = new Initialization(init);
        test = new ExplorerData(init2);

        ArrayList<Objective> objectifs = new ArrayList<>();
        Objective ob1 = new Objective("WOOD", 600);
        Objective ob2 = new Objective("FISH", 600);
        objectifs.add(ob1);
        objectifs.add(ob2);

        assertEquals(test.getX(), 0);
        assertEquals(test.getY(), 0);
        assertEquals(test.getAltitude(), 0);

        assertEquals(test.isStarting(), true);
        assertEquals(test.isHasObjective(), false);
        assertEquals(test.isInMovement(), false);

        assertEquals(test.getStartCreek(), "creek_id");
        assertEquals(test.getAvailableBudget(), 600);
        assertEquals(test.getAvailableMen(), 50);
        assertEquals(test.getObjectives().get(0).getObjective(), "WOOD");
        assertEquals(test.getObjectives().get(0).getAmount(), 600);
        assertEquals(test.getObjectives().get(1).getObjective(), "FISH");
        assertEquals(test.getObjectives().get(1).getAmount(), 600);

        test.decreaseCost(10);
        assertEquals(test.getAvailableBudget(), 590);

        test.updatePosition();
        assertEquals(test.getX(), 0);
        assertEquals(test.getY(), 1);

        test.updateAltitude(1);
        assertEquals(test.getAltitude(), 1);




    }
}
