package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Land;
import fr.unice.polytech.ogl.islbb.actions.Scout;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by user on 14/03/2015.
 */
public class ExplorerTest {
    String memo="NOSE";
    String init="{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}]}";
    String resultland="{\"status\":\"OK\",\"cost\":12}";
    String testN = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
    String testO = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testS = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testE = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":23}}";
    ArrayList<String>resultscout;
    Data cardinaux;
    ArrayList<String>scoutdecision;
    int direction=1;

    @Test public void testExplorer(){
        scoutdecision=new ArrayList<String>();
        resultscout=new ArrayList<String>();
        resultscout.add("testN");
        resultscout.add("testO");
        resultscout.add("testS");
        resultscout.add("testE");


        int i=0;
            Explorer test=new Explorer();
            test.initialize(init);
            String decision1=test.takeDecision();
            assertEquals(decision1, Land.land("creek_id", 1));
            test.acknowledgeResults(resultland);
            assertEquals(Scout.scout("N"),test.takeDecision());
            while(i<=3){
                test.acknowledgeResults(resultscout.get(i));
                scoutdecision.add(test.takeDecision());
                i++;
            }
        i=1;
        while(i<4){
            assertEquals(Scout.scout(cardinaux.getCardinaux(i)),scoutdecision.get(i-1));
        }
        }
}
