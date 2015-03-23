package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.*;

import junit.framework.TestSuite;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;



/**
 * Created by Thibault OBER on 14/03/2015.
 */
public class TestRunner {

        public static void main(String args[]) {

            TestSuite suite;
            String memo="NOSE";
            String init="{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}]}";
            String resultland="{\"status\":\"OK\",\"cost\":12}";
            String testN = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
            String testE = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
            String testS = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
            String testW = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"WOOD\"],\"altitude\":23}}";
            ArrayList<String> resultscout;
            resultscout=new ArrayList<String>();
            resultscout.add(testN);
            resultscout.add(testE);
            resultscout.add(testS);
            resultscout.add(testW);
            Data cardinaux=new Data();
            ArrayList<String>scoutdecision;
            int direction=1;
            scoutdecision=new ArrayList<String>();
            int i=0;

            Explorer test=new Explorer();
            test.initialize(init);
            String decision1=test.takeDecision();
            assertEquals(decision1, Land.land("creek_id", 1));
            test.acknowledgeResults(resultland);
            assertEquals(Scout.scout("N"), test.takeDecision());
            while(i<3){
                //System.out.println(resultscout.get(i));
                test.acknowledgeResults(resultscout.get(i));
                scoutdecision.add(test.takeDecision());
                i++;
            }
            i=0;
            while(i<3){
                System.out.println(scoutdecision.get(i));
                System.out.println(Scout.scout(cardinaux.getCardinaux(i + 1)));
               // assertEquals(Scout.scout(cardinaux.getCardinaux(i)),scoutdecision.get(i-1));
                i++;
            }
            test.acknowledgeResults(resultscout.get(3));//scout ouest
            System.out.println(test.takeDecision());
            test.acknowledgeResults(resultland);
            System.out.println(test.takeDecision());

//        JUnitCore runner = new JUnitCore();
//        runner.addListener(new TextListener(System.out));
//        runner.run(ComportementTest.class);
//        TestSuite suite= new TestSuite();
//        suite.addTest(new ExploreTest());
//        junit.textui.TestRunner.run(suite);
//       }
}}
