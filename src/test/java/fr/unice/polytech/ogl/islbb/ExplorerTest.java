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
    String init = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    String init3 = "{\"creek\":\"creek_id\", \"budget\":70,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}";
    String resultland = "{\"status\":\"OK\",\"cost\":12}";
    String moveafterscoot = "{\"debug\":\"If after scouting a possible objective has been found, moving to it.\",\"action\":\"move_to\",\"parameters\":{\"direction\":\"E\"}}";
    String scootN = "{\"debug\":\"After the initial tile has been worked out, starting to explore the island.\",\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}";
    String scootE = "{\"debug\":\"Scouting next tile, direction: 1 | current: false | N: true | E: false | S: false | W: false\",\"action\":\"scout\",\"parameters\":{\"direction\":\"E\"}}";
    String scootNafterexpoit = "{\"debug\":\"After exploring and the resource was not exploitable or after exploiting it, restart scouting.\",\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}";

    String testN = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":-23}}";
    String testW = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testS = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\"],\"altitude\":23}}";
    String testE = "{\"status\":\"OK\",\"cost\":8,\"extras\":{\"resources\":[\"FLOWER\",\"FISH\"],\"altitude\":23}}";
    String exploit = "{\"status\":\"OK\",\"cost\":40,\"extras\":{\"amount\":600}}";
    String exploit2 = "{\"status\":\"OK\",\"cost\":73,\"extras\":{\"amount\":600}}";
    String land = "{\"debug\":\"Initial landing.\",\"action\":\"land\",\"parameters\":{\"creek\":\"creek_id\",\"people\":2}}";
    String exploring1 = "{\"debug\":\"Exploring first tile.\",\"action\":\"explore\"}";
    String exploring2 = "{\"debug\":\"Afer moving to a possible objective, exploring it.\",\"action\":\"explore\"}";
    String exploretest = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"FUR\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";

    String exploretest2 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"FISH\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploretest2b = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"WOOD\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploreTest3 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"WOOD\",\"cond\": \"EASY\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploreTest4 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"HIGH\",\"resource\": \"WOOD\",\"cond\": \"HARD\"}],\"pois\": []},\"status\": \"OK\"}";

    String exploreTest5 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"MEDIUM\",\"resource\": \"WOOD\",\"cond\": \"EASY\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploreTest6 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"MEDIUM\",\"resource\": \"WOOD\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploreTest7 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"MEDIUM\",\"resource\": \"WOOD\",\"cond\": \"HARD\"}],\"pois\": []},\"status\": \"OK\"}";

    String exploreTest8 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"LOW\",\"resource\": \"WOOD\",\"cond\": \"EASY\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploreTest9 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"LOW\",\"resource\": \"WOOD\",\"cond\": \"FAIR\"}],\"pois\": []},\"status\": \"OK\"}";
    String exploreTest10 = "{\"cost\": 6,\"extras\": {\"resources\": [{\"amount\": \"LOW\",\"resource\": \"WOOD\",\"cond\": \"HARD\"}],\"pois\": []},\"status\": \"OK\"}";
    String testScout = "{\"status\":\"OK\",\"cost\":576,\"extras\":{\"resources\":[\"FLOWER\",\"FISH\"],\"altitude\":23}}";
    String exploitdecision = "{\"debug\":\"After exploring, the objective is exploitable.\",\"action\":\"exploit\",\"parameters\":{\"resource\":\"FISH\"}}";
    String init2 = "{\"creek\":\"creek_id\", \"budget\":600,\"men\":50,\"objective\":[{ \"resource\": \"FLOWER\", \"amount\":2}]}";
    ArrayList<String> resultscout;
    Data cardinaux;
    ArrayList<String> scoutdecision;
    int direction = 1;

    @Ignore
    @Test
    public void testExplorer() {
        Objective obj = new Objective("WOOD", 600);
        cardinaux = new Data();
        //contient les décisions après les scouts
        scoutdecision = new ArrayList<String>();
        //contient les résultats des scouts
        resultscout = new ArrayList<String>();

        resultscout.add(testN);
        resultscout.add(testE);
        resultscout.add(testS);
        resultscout.add(testW);


        int i = 0;
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();
        assertEquals(land, decision1);
        //première décision
        test.acknowledgeResults(resultland);
        //regarde si la 2 eme est un scout
        assertEquals(test.takeDecision(), exploring1);
        test.acknowledgeResults(exploretest);
        assertEquals(test.takeDecision(), scootN);
        test.acknowledgeResults(testN);
        assertEquals(test.takeDecision(), scootE);
        test.acknowledgeResults(testE);
        assertEquals(test.takeDecision(), moveafterscoot);
        test.acknowledgeResults(resultland);
        assertEquals(test.takeDecision(), exploring2);
        test.acknowledgeResults(exploretest2);
        //exploit
        assertEquals(test.takeDecision(), exploitdecision);
        test.acknowledgeResults(exploit);
        assertEquals(test.takeDecision(), scootNafterexpoit);
        test.acknowledgeResults(testN);
        assertEquals(test.takeDecision(), scootE);
        //To do checker objectifs
        // assertEquals(Scout.scout("N"), test.takeDecision());
//        test.acknowledgeResults(resultscout.get(i));
//        scoutdecision.add(test.takeDecision());
//        i++;
//        //scout est
//        assertEquals(Scout.scout(cardinaux.getCardinaux(i)),test.takeDecision());
//        test.acknowledgeResults(resultscout.get(i));
//        //move E
//        assertEquals(Move.move("E"), test.takeDecision());//trouve du bois land 10
//        test.acknowledgeResults(resultland);
//        //exploit
//        assertEquals(Exploit.exploit("FISH"), test.takeDecision());
//        test.acknowledgeResults(exploit);
//        //Objective.print(test.getObjectives());
//        //redémarre à scoot
//        assertEquals(Scout.scout("N"), test.takeDecision());
//        //regarde si objectives est à jour
//        List<Objective> objec=new ArrayList<Objective>();
//        objec.add(obj);
//        //assertEquals(test.getObjectives().size(),1);
//        test.takeDecision();
    }

    public void testglimpse() {
        Objective obj = new Objective("WOOD", 600);
        cardinaux = new Data();
        //contient les décisions après les scouts
        scoutdecision = new ArrayList<String>();
        //contient les résultats des scouts
        resultscout = new ArrayList<String>();

        resultscout.add(testN);
        resultscout.add(testE);
        resultscout.add(testS);
        resultscout.add(testW);

        Explorer essai = new Explorer();
        int i = 0;
        //Objective.print(essai.getObjectives());
        essai.initialize(init2);
        String decision1 = essai.takeDecision();
        assertEquals(decision1, Land.land("creek_id", 3));
        //première décision
        essai.acknowledgeResults(resultland);
        //regarde si la 2 eme est un scout
        assertEquals(Scout.scout("N"), essai.takeDecision());
        essai.acknowledgeResults(resultscout.get(i));
        while (i <= 4) {
            scoutdecision.add(essai.takeDecision());
            essai.acknowledgeResults(resultscout.get(i));
            assertEquals(scoutdecision.get(i++), Scout.scout(cardinaux.getCardinaux(i)));
            i++;
        }
        System.out.println(essai.takeDecision());


    }

    /**
     * @Author Lucas
     * Vérifie si le bot quitte bien l'ile quand son budget est infèrieur a 25
     */
    @Test
    public void testStopByBudget() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //première décision ( coute 576 avec 600 de budget)
        test.acknowledgeResults(testScout);
        //regarde si le bot quiite l'ile
        assertEquals(Exit.exit("Not enough budget, distance:0"), test.takeDecision());

    }

    /**
     * @Author Lucas
     * Vérifie si le bot quitte bien l'ile quand il n'a plus assez de budget pour exploit mais que le budget était suppérieur à 25
     */
    @Test
    public void testExploitBudget() {
        Explorer test = new Explorer();
        test.initialize(init3);
        String decision1 = test.takeDecision();

        //Budget trop faible par rapport au coups de l'exploit, doit quitter
        test.acknowledgeResults(exploit2);

        //regarde si le bot quiite l'ile
        assertEquals(Exit.exit("Not enough budget, distance:0"), test.takeDecision());

    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //Ressource inutile, doit pas exploit, donc continu explore
        test.acknowledgeResults(exploretest);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore2() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //Ressource utile High et fair -> doit exploit
        test.acknowledgeResults(exploretest2b);
        //TODO assertEquals(Exploit.exploit("WOOD", "Exploit objective present enough on initial case."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore3() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //High et easy --> ok
        test.acknowledgeResults(exploreTest3);
        //TODO assertEquals(Exploit.exploit("WOOD", "Exploit objective present enough on initial case."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore4() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //High et hard --> pas ok
        test.acknowledgeResults(exploreTest4);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore5() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //Medium et easy --> ok
        test.acknowledgeResults(exploreTest5);
        //TODO assertEquals(Exploit.exploit("WOOD", "Exploit objective present enough on initial case."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore6() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //Medium et fair --> pas ok
        test.acknowledgeResults(exploreTest6);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore7() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //Medium et hard --> pas ok
        test.acknowledgeResults(exploreTest7);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore8() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //low et easy --> pas ok
        test.acknowledgeResults(exploreTest8);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore9() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //low et fair --> pas ok
        test.acknowledgeResults(exploreTest9);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

    /**
     * @Author Lucas
     * Vérifie si le bot exploit bien après une exploration
     */
    @Test
    public void testExploitAfterExplore10() {
        Explorer test = new Explorer();
        test.initialize(init);
        String decision1 = test.takeDecision();

        //low et hard --> pas ok
        test.acknowledgeResults(exploreTest10);
        assertEquals(Explore.explore("Exploring first tile."), test.takeDecision());
    }

}
