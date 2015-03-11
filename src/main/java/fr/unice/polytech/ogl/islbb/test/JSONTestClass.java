package fr.unice.polytech.ogl.islbb.test;


import fr.unice.polytech.ogl.islbb.Init;
import fr.unice.polytech.ogl.islbb.actions.Exit;
import fr.unice.polytech.ogl.islbb.actions.Land;
import fr.unice.polytech.ogl.islbb.actions.Scout;

/**
 * Created by user on 02/03/2015.
 */
public class JSONTestClass {
    public static void main(String[] args)
    {

        String initializationTest = " { \"creek\": \"creek_id\", \"budget\": 600, \"men\": 50, \"objective\": [ { \"resource\": \"WOOD\", \"amount\": 600 }, { \"resource\": \"FUCKS GIVEN\", \"amount\": 0 } ] }";


        Init initClassTest = new Init(initializationTest);
        System.out.println(initClassTest.initDesc());
        System.out.println(Exit.exit().toString());
        System.out.println(Scout.scout("N").toString());
        System.out.println(Land.land(initClassTest.getCreek(), 1));

    }
}


