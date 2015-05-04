package fr.unice.polytech.ogl.islbb;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 23/04/2015.
 * Classe test de Initialization
 */
public class InitializationTest {

    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    Initialization init2 = new Initialization(init);

    @Test
    public void testInitialization() {

        //Initalisation
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(600);
        list.add(600);
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("WOOD");
        list2.add("FISH");

        ArrayList<String> list3 = new ArrayList<String>();
        list3.add("TROPICAL_SEASONAL_FOREST");
        list3.add("TROPICAL_RAIN_FOREST");
        list3.add("BEACH");
        list3.add("OCEAN");
        list3.add("LAKE");

        //Tests
        assertEquals(init2.getBudget(), 600);
        assertEquals(init2.getMen(), 50);
        assertEquals(init2.getAmounts(), list);
        assertEquals(init2.getCreek(), "creek_id");
        assertEquals(init2.getResources(), list2);

        assertEquals(init2.getResource(1), "FISH");
        assertEquals(init2.getAmount(1), 600);
        assertEquals(init2.getResource(1), "FISH");

        assertEquals(init2.getWantedBiomes(), list3);


    }
}
