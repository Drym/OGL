package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.actions.*;

import fr.unice.polytech.ogl.islbb.reports.Objective;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibault OBER on 14/03/2015.
 */
public class ExplorerTest {
    String init="{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    String resultland="{\"status\":\"OK\",\"cost\":12}";
    String testN = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
    String testW = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testS = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testE = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"FISH\"],\"altitude\":23}}";
    String exploit="{\"status\":\"OK\",\"cost\":40,\"extras\":{\"amount\":600}}";
    String testScout ="{\"status\":\"OK\",\"cost\":550,\"extras\":{\"resources\":[\"FLOWER\",\"FISH\"],\"altitude\":23}}";
    String init2="{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"FLOWER\", \"amount\":2}]}";
    ArrayList<String>resultscout;
    Data cardinaux;
    ArrayList<String>scoutdecision;
    int direction=1;

    @Ignore
    @Test public void testExplorer(){
        Objective obj=new Objective("WOOD",600);
        cardinaux=new Data();
        //contient les décisions après les scouts
        scoutdecision=new ArrayList<String>();
        //contient les résultats des scouts
        resultscout=new ArrayList<String>();

        resultscout.add(testN);
        resultscout.add(testE);
        resultscout.add(testS);
        resultscout.add(testW);


        int i=0;
        Explorer test=new Explorer();
        test.initialize(init);
        String decision1=test.takeDecision();
        assertEquals(decision1, Land.land("creek_id", 3));
        //première décision
        test.acknowledgeResults(resultland);
        //regarde si la 2 eme est un scout
        assertEquals(Scout.scout("N"), test.takeDecision());
        test.acknowledgeResults(resultscout.get(i));
        scoutdecision.add(test.takeDecision());
        i++;
        //scout est
        assertEquals(Scout.scout(cardinaux.getCardinaux(i)),test.takeDecision());
        test.acknowledgeResults(resultscout.get(i));
        //move E
        assertEquals(Move.move("E"), test.takeDecision());//trouve du bois land 10
        test.acknowledgeResults(resultland);
        //exploit
        assertEquals(Exploit.exploit("FISH"), test.takeDecision());
        test.acknowledgeResults(exploit);
        Objective.print(test.getObjectives());
        //redémarre à scoot
        assertEquals(Scout.scout("N"), test.takeDecision());
        //regarde si objectives est à jour
        List<Objective> objec=new ArrayList<Objective>();
        objec.add(obj);
        assertEquals(test.getObjectives().size(),1);
        test.takeDecision();
        }

     public void testglimpse(){
        Objective obj=new Objective("WOOD",600);
        cardinaux=new Data();
        //contient les décisions après les scouts
        scoutdecision=new ArrayList<String>();
        //contient les résultats des scouts
        resultscout=new ArrayList<String>();

        resultscout.add(testN);
        resultscout.add(testE);
        resultscout.add(testS);
        resultscout.add(testW);

        Explorer essai=new Explorer();
        int i=0;
        Objective.print(essai.getObjectives());
        essai.initialize(init2);
        String decision1=essai.takeDecision();
        assertEquals(decision1, Land.land("creek_id", 3));
        //première décision
        essai.acknowledgeResults(resultland);
        //regarde si la 2 eme est un scout
        assertEquals(Scout.scout("N"), essai.takeDecision());
        essai.acknowledgeResults(resultscout.get(i));
        while(i<=4){
            scoutdecision.add(essai.takeDecision());
            essai.acknowledgeResults(resultscout.get(i));
            assertEquals(scoutdecision.get(i++), Scout.scout(cardinaux.getCardinaux(i)));
            i++;
        }
        System.out.println(essai.takeDecision());


    }
//
//    /**
//     * @Creator Lucas
//     * Vérifie si le bot quitte bien l'ile quand son budget est infèrieur a 50
//     */
//    @Test public void testStopByBudget(){
//        Explorer test=new Explorer();
//        test.initialize(init);
//        String decision1=test.takeDecision();
//
//        //première décision ( coute 550 avec 600 de budget)
//        test.acknowledgeResults(testScout);
//        //regarde si le bot quiite l'ile
//        assertEquals(Exit.exit(), test.takeDecision());
//
//    }
}
