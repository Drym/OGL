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

        assertEquals(test.xOffset(1),1);
        assertEquals(test.yOffset(1), 0);
        assertEquals(test.parseAmount("LOW"), 0);
        assertEquals(test.parseCondition("HARSH"), 0);

    }
}
