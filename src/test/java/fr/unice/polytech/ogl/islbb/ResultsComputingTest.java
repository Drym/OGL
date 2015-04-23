package fr.unice.polytech.ogl.islbb;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by Lucas on 23/04/2015.
 */
public class ResultsComputingTest {

    ResultsComputing test = new ResultsComputing();
    Boolean isTrue;


    @Test public void TestResultsComputing() {

        isTrue = true;

        assertEquals(test.getStatus("{status : OK}"), isTrue);

        assertEquals(test.xOffset(0),0);
        assertEquals(test.xOffset(1),1);
        assertEquals(test.xOffset(2),0);
        assertEquals(test.xOffset(3),-1);
        assertEquals(test.xOffset(10),0);

        assertEquals(test.yOffset(0), 1);
        assertEquals(test.yOffset(1), 0);
        assertEquals(test.yOffset(2), -1);
        assertEquals(test.yOffset(3), 0);
        assertEquals(test.yOffset(10), 0);

        assertEquals(test.parseAmount("LOW"), 0);
        assertEquals(test.parseAmount("MEDIUM"), 1);
        assertEquals(test.parseAmount("HIGH"), 2);
        assertEquals(test.parseAmount("else"), -1);

        assertEquals(test.parseCondition("HARSH"), 0);
        assertEquals(test.parseCondition("FAIR"), 1);
        assertEquals(test.parseCondition("EASY"), 2);
        assertEquals(test.parseCondition("else"), -1);

        assertEquals(test.computeDistance(1,1,1,1), 0);

    }
}
