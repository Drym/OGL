package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.Land;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by user on 14/03/2015.
 */
public class ExplorerTest {
        String init="{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}]}";
    @Test public void testExplorer(){
        Explorer test=new Explorer();
        test.initialize(init);
        String decision1=test.takeDecision();
        assertEquals(decision1, Land.land("creek_id",1));
    }
}
