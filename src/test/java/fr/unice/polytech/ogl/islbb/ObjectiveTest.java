package fr.unice.polytech.ogl.islbb;

import fr.unice.polytech.ogl.islbb.reports.Objective;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;


/**
 * Created by Lucas on 08/04/2015.
 * Classe test de Objective
 */
public class ObjectiveTest {

    private List<Objective> objectives;
    Initialization contract;
    Objective objectiveRef;
    Objective objectiveRef2;

    @Test
    public void testObjective() {

        objectiveRef = new Objective("WOOD",600);
        objectiveRef2= new Objective("FISH",600);
        contract = new Initialization("{\"creek\":\"creek_id\", \"budget\":70,\"men\":50,\"objective\":[{ \"resource\": \"WOOD\", \"amount\":600}, { \"resource\": \"FISH\", \"amount\": 600}]}");
        this.objectives = Objective.buildObjectives(this.contract.getResources(), this.contract.getAmounts());

        //Vérifie l'object ref
        assertEquals(objectiveRef.getObjective(), "WOOD");
        assertEquals(objectiveRef.getAmount(), 600);

        //Vérifie que les bonnes inforamtions sont récupérées dans le contract
        assertEquals(objectiveRef.getObjective(), objectives.get(0).getObjective());
        assertEquals(objectiveRef.getAmount(), objectives.get(0).getAmount());

        assertEquals(objectiveRef2.getObjective(), objectives.get(1).getObjective());
        assertEquals(objectiveRef2.getAmount(), objectives.get(1).getAmount());

        objectiveRef.setAmount(1000);
        objectiveRef.setObjective("FISH");

        assertEquals(objectiveRef.getObjective(), "FISH");
        assertEquals(objectiveRef.getAmount(), 1000);

        objectiveRef.updateAmount(10);
        assertEquals(objectiveRef.getAmount(), 990);

        assertEquals(objectiveRef.updateAmount(1), false);
        assertEquals(objectiveRef.updateAmount(989), true);

        assertEquals(objectiveRef.toString(), "FISH: 0");



    }
}
