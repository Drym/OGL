package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Land;
import fr.unice.polytech.ogl.islbb.actions.Move;
import fr.unice.polytech.ogl.islbb.actions.Scout;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by user on 14/03/2015.
 */
public class ExplorerTest {
    String init="{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}]}";
    String resultland="{\"status\":\"OK\",\"cost\":12}";
    String testN = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
    String testW = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testS = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testE = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":23}}";
    ArrayList<String>resultscout;
    Data cardinaux;
    ArrayList<String>scoutdecision;
    int direction=1;

    @Test public void testExplorer(){
        cardinaux=new Data();
        //contient les décisions après les scouts
        scoutdecision=new ArrayList<String>();
        //contient les résultats des scouts
        resultscout=new ArrayList<String>();

        resultscout.add(testN);
        resultscout.add(testW);
        resultscout.add(testS);
        resultscout.add(testE);


        int i=0;
            Explorer test=new Explorer();
            test.initialize(init);
            String decision1=test.takeDecision();
            assertEquals(decision1, Land.land("creek_id", 1));
            //première décision
            test.acknowledgeResults(resultland);
            //regarde si la 2 eme est un scout
            assertEquals(Scout.scout("N"),test.takeDecision());
            while(i<3){
                test.acknowledgeResults(resultscout.get(i));
                scoutdecision.add(test.takeDecision());
                i++;
            }
        i=0;
        while(i<3){
            assertEquals(Scout.scout(cardinaux.getCardinaux(i+1)),scoutdecision.get(i));
            i++;
        }
        test.acknowledgeResults(resultscout.get(3));//scout ouest
        assertEquals(Land.land("creek_id",10),test.takeDecision());//trouve du bois land 10
        test.acknowledgeResults(resultland);

        }
}
