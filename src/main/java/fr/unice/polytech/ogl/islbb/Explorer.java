package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;
import fr.unice.polytech.ogl.islbb.actions.*;
import fr.unice.polytech.ogl.islbb.reports.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui implemente les différentes actions du robot
 */
public class Explorer implements IExplorerRaid {

    // CONSTANTS
    private final static int MEN_USED = 3;

    private Initialization contract;

    private String creek;
    private int budget;
    private int men;
    private List<Objective> objectives;

    private int landedMen;

    private IslandMap arenaMap;

    private int currentAltitude;
    private int currentX;
    private int currentY;

    private int moveBudget;
    private int deltaX;
    private int deltaY;

    private int lastScoutDirection;
    private int scoutedX;
    private int scoutedY;


    private int lastMoveDirection;

    private int lastGlimpseDirection;

    private String exploitObjective;
    private int objectiveX;
    private int objectiveY;
    private boolean hasObjective;

    private String lastDecision;


    /**
     * Initialize the explorer with the given objectives.
     *
     * @param context Objectives given by the arena.
     */
    @Override
    public void initialize(String context) {

        this.contract = new Initialization(context);

        this.creek = this.contract.getCreek();
        this.budget = this.contract.getBudget();
        this.men = contract.getMen();
        this.objectives = Objective.buildObjectives(this.contract.getResources(), this.contract.getAmounts());

        this.landedMen = Math.min(this.men, this.MEN_USED);

        this.arenaMap = new IslandMap();

        this.currentAltitude = 0;
        this.currentX = 0;
        this.currentY = 0;

        this.moveBudget = 0;

        this.lastScoutDirection = 0;
        this.scoutedX = 0;
        this.scoutedY = 0;


        this.lastMoveDirection = 0;

        this.lastGlimpseDirection = 0;

        this.exploitObjective = null;
        this.objectiveX = 0;
        this.objectiveY = 0;
        this.hasObjective = false;

        this.lastDecision = null;

    }

    /**
     * The AI of the explorer, makes his decisions.
     *
     * @return Decision taken (JSON format).
     */
    @Override
    public String takeDecision() {

        if (this.lastDecision == null) {
            this.lastDecision = "land";
            return Land.land(this.creek, this.landedMen);
        }


        if (75 > this.budget) {
            this.lastDecision = "exit";
            return Exit.exit();
        }

        if (this.objectives.isEmpty()) {
            this.lastDecision = "exit";
            return Exit.exit();
        }

        if (((this.lastDecision.equals("land")) || (this.lastDecision.equals("scout")) || (this.lastDecision.equals("move")) || (this.lastDecision.equals("exploit"))) && (!this.hasObjective)) {
            while (this.lastScoutDirection < 4) {
                this.scoutedX = this.currentX + ResultsComputing.xOffset(this.lastScoutDirection);
                this.scoutedY = this.currentY + ResultsComputing.yOffset(this.lastScoutDirection);

                if (this.arenaMap.isAlreadyScouted(this.scoutedX, this.scoutedY)) {
                    if (this.arenaMap.getInformation(this.scoutedX, this.scoutedY).hasResources(this.objectives).isEmpty()) {
                        this.lastScoutDirection++;
                    } else {
                        this.objectiveX = this.scoutedX;
                        this.objectiveY = this.scoutedY;
                        if (!this.arenaMap.getInformation(this.objectiveX, this.objectiveY).isAlreadyExploited()) {
                            this.hasObjective = true;
                            this.exploitObjective = this.arenaMap.getInformation(this.scoutedX, this.scoutedY).hasResources(this.objectives).get(0).getType();
                            break;
                        }
                    }
                } else {
                    break;
                }

            }

            if (!this.hasObjective) {
                if (this.lastScoutDirection < 4) {
                    this.lastDecision = "scout";
                    return Scout.scout(this.lastScoutDirection);
                }
                else {
                    this.lastScoutDirection = 0;
                    this.lastGlimpseDirection = 0;
                    this.lastDecision = "glimpse";
                    //return Glimpse.glimpse(this.lastGlimpseDirection, 2);
                }
            }

        }

        if (this.lastDecision.equals("glimpse")) {
            while (this.lastGlimpseDirection < 4) {
                if (this.arenaMap.isAlreadyGlimpsed(this.currentX + ResultsComputing.xOffset(this.lastGlimpseDirection), this.currentY + ResultsComputing.yOffset(this.lastGlimpseDirection))) {
                    if (this.arenaMap.isWater(this.currentX + ResultsComputing.xOffset(this.lastGlimpseDirection), this.currentY + ResultsComputing.yOffset(this.lastGlimpseDirection))) {
                        this.lastGlimpseDirection++;
                    }
                    else if (!this.arenaMap.getInformation(this.currentX + ResultsComputing.xOffset(this.lastGlimpseDirection), this.currentY + ResultsComputing.yOffset(this.lastGlimpseDirection)).isAlreadyExploited()) {
                        this.lastMoveDirection = this.lastGlimpseDirection;
                        this.lastGlimpseDirection = 0;
                        this.lastDecision = "move";
                        return Move.move(this.lastMoveDirection);
                    }
                    else {
                        this.lastGlimpseDirection++;
                    }
                }
                else {
                    return Glimpse.glimpse(this.lastGlimpseDirection, 2);
                }

            }

            if (this.lastGlimpseDirection >= 4) {
                this.lastMoveDirection = this.arenaMap.lessWaterDirection(this.currentX, this.currentY);
                this.lastGlimpseDirection = 0;
                this.lastDecision = "move";
                return Move.move(this.lastMoveDirection);
            }
        }

        if (this.hasObjective) {
            this.lastScoutDirection = 0;
            if ((this.currentX != this.objectiveX) || (this.currentY != this.objectiveY)) {
                this.lastDecision = "move";
                if (this.currentX < this.objectiveX) {
                    this.lastMoveDirection = 1;
                    return Move.move(this.lastMoveDirection);
                }
                if (this.currentX > this.objectiveX) {
                    this.lastMoveDirection = 3;
                    return Move.move(this.lastMoveDirection);
                }
                if (this.currentY < this.objectiveY) {
                    this.lastMoveDirection = 0;
                    return Move.move(this.lastMoveDirection);
                }
                if (this.currentY > this.objectiveY) {
                    this.lastMoveDirection = 2;
                    return Move.move(this.lastMoveDirection);
                }
            }

            this.hasObjective = false;
            this.lastDecision = "exploit";
            return Exploit.exploit(this.exploitObjective);
        }

        this.lastDecision = "exit";
        return Exit.exit();

    }

    /**
     * Get the results returned from the arena, regarding the last decision executed.
     *
     * @param results Results given by the arena.
     */
    @Override
    public void acknowledgeResults(String results) {

        if (ResultsComputing.getStatus(results)) {
            JSONObject JSONResults = new JSONObject(results);

            if (this.lastDecision.equals("land")) {
            }

            if (this.lastDecision.equals("exit")) {
            }

            if (this.lastDecision.equals("scout")) {

                // On créer la liste des ressources disponibles sur la case.
                List<Resource> tileResources = new ArrayList<>();
                JSONArray resourcesJSONArray = JSONResults.getJSONObject("extras").getJSONArray("resources");

                for (int i = 0; i < resourcesJSONArray.length(); i++) {
                    tileResources.add(new Resource(resourcesJSONArray.getString(i)));
                }

                boolean unreachableTile = JSONResults.getJSONObject("extras").has("unreachable");

                if (this.arenaMap.isRegistered(this.scoutedX, this.scoutedY)) {
                    this.arenaMap.getInformation(this.scoutedX, this.scoutedY).scoutTile(this.currentAltitude + JSONResults.getJSONObject("extras").getInt("altitude"), tileResources, unreachableTile);
                } else {
                    this.arenaMap.addTile(this.scoutedX, this.scoutedY, new IslandTile(this.currentAltitude + JSONResults.getJSONObject("extras").getInt("altitude"), tileResources, unreachableTile));
                }

            }

            if (this.lastDecision.equals("move")) {

                this.currentX += ResultsComputing.xOffset(this.lastMoveDirection);
                this.currentY += ResultsComputing.yOffset(this.lastMoveDirection);

                if (this.arenaMap.isAlreadyScouted(this.currentX, this.currentY)) {
                    this.currentAltitude += this.arenaMap.getInformation(this.currentX, this.currentY).getAltitude();
                }

                this.moveBudget += JSONResults.getInt("cost");

            }


            if (this.lastDecision.equals("explore")) {

                // On créer la liste des ressources disponibles sur la case.
                // Avec le type, la quantité et l'exploitabilité.
                List<Resource> tileResources = new ArrayList<>();
                JSONArray resourcesJSONArray = JSONResults.getJSONObject("extras").getJSONArray("resources");

                for (int i = 0; i < resourcesJSONArray.length(); i++) {
                    JSONObject resourceCondition = resourcesJSONArray.getJSONObject(i);
                    tileResources.add(new Resource(resourceCondition.getString("resource"), resourceCondition.getString("amount"), resourceCondition.getString("cond")));
                }

                // On créer la liste des points d'intérêts disponibles sur la case.
                // Avec le type, et l'ID.
                List<POI> tilePOIs = new ArrayList<>();
                JSONArray POIsJSONArray = JSONResults.getJSONObject("extras").getJSONArray("pois");

                for (int i = 0; i < POIsJSONArray.length(); i++) {
                    JSONObject POIInformation = POIsJSONArray.getJSONObject(i);
                    tilePOIs.add(new POI(POIInformation.getString("kind"), POIInformation.getString("id")));
                }

                // La case est déjà dans le carte de l'île, on la met à jour.
                if (this.arenaMap.isRegistered(this.currentX, this.currentY)) {
                    this.arenaMap.getInformation(this.currentX, this.currentY).exploreTile(tileResources, tilePOIs);
                }
                // La case n'est pas encore dans la carte de l'île, on l'ajoute avec les informations retirées.
                else {
                    this.arenaMap.addTile(this.currentX, this.currentY, new IslandTile(tileResources, tilePOIs));
                }

            }

            if (this.lastDecision.equals("exploit")) {

                for (int i = 0; i < this.objectives.size(); i++) {
                    if (this.objectives.get(i).getObjective().equals(this.exploitObjective)) {
                        if (!this.objectives.get(i).updateAmount(JSONResults.getJSONObject("extras").getInt("amount"))) {
                            this.objectives.remove(i);
                        }
                        break;
                    }
                }

                this.arenaMap.getInformation(this.currentX, this.currentY).removeResource(this.exploitObjective);

                this.exploitObjective = null;
                this.hasObjective = false;

            }

            if (this.lastDecision.equals("glimpse")) {

                JSONArray biomesJSONArray = JSONResults.getJSONObject("extras").getJSONArray("report");

                for (int i = 0; i < biomesJSONArray.length(); i++) {
                    int glimpsedX = this.currentX + (ResultsComputing.xOffset(this.lastGlimpseDirection) * i);
                    int glimpsedY = this.currentY + (ResultsComputing.yOffset(this.lastGlimpseDirection) * i);

                    List<Biome> tileBiomes = new ArrayList<>();
                    JSONArray tileBiomesJSONArray = biomesJSONArray.getJSONArray(i);
                    Map<String, Integer> biomesMap = new HashMap<>();

                    for (int j = 0; j < tileBiomesJSONArray.length(); j++) {
                        if (biomesMap.containsKey(tileBiomesJSONArray.getJSONArray(j).getString(0))) {
                            biomesMap.put(tileBiomesJSONArray.getJSONArray(j).getString(0), biomesMap.get(tileBiomesJSONArray.getJSONArray(j).getString(0)) + tileBiomesJSONArray.getJSONArray(j).getInt(1));
                        } else {
                            biomesMap.put(tileBiomesJSONArray.getJSONArray(j).getString(0), tileBiomesJSONArray.getJSONArray(j).getInt(1));
                        }
                    }

                    for (Map.Entry<String, Integer> entry : biomesMap.entrySet()) {
                        tileBiomes.add(new Biome(entry.getKey(), entry.getValue()));
                    }

                    if (this.arenaMap.isRegistered(glimpsedX, glimpsedY)) {
                        this.arenaMap.getInformation(glimpsedX, glimpsedY).glimpseTile(tileBiomes, i);
                    } else {
                        this.arenaMap.addTile(glimpsedX, glimpsedY, new IslandTile(tileBiomes, i));
                    }
                }

            }

            this.budget -= JSONResults.getInt("cost");

        }


    }
    public List<Objective> getObjectives(){
        return this.objectives;
    }
}