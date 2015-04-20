package fr.unice.polytech.ogl.islbb.data;

import fr.unice.polytech.ogl.islbb.ResultsComputing;
import fr.unice.polytech.ogl.islbb.actions.*;
import fr.unice.polytech.ogl.islbb.reports.Resource;

/**
 * Created by QWPHR on 20/04/2015.
 */
public class ExplorerDecision {

    // DEBUG //////////////////////////////////////////////////////////////////

    private static String debugString;
    public static void setDebug(String newDebug) {
        debugString = newDebug;
    }

    ///////////////////////////////////////////////////////////////////////////
    
    protected ExplorerData explorerCurrentState;
    protected ArenaData islandData;
    
    public ExplorerDecision(ExplorerData explorerData, ArenaData arenaData) {
        
        this.explorerCurrentState = explorerData;
        this.islandData = arenaData;
                
    }
    
    public String computeDecision() {
        if (this.explorerCurrentState.lastDecision == null && this.explorerCurrentState.startCreek == null) {
            this.explorerCurrentState.lastDecision = "exit";
            return Exit.exit("No startCreek given at initialization, stopping...");
        }

        if (this.explorerCurrentState.lastDecision == null) {
            this.explorerCurrentState.lastDecision = "land";
            return Land.land(this.explorerCurrentState.startCreek, 2, "Initial landing.");
        }

        if (this.explorerCurrentState.availableBudget < ResultsComputing.computeDistance(this.explorerCurrentState.x, this.explorerCurrentState.y, 0, 0) + 25) {
            this.explorerCurrentState.lastDecision = "exit";
            return Exit.exit("Not enough budget, distance:" + ResultsComputing.computeDistance(this.explorerCurrentState.x, this.explorerCurrentState.y, 0, 0));
        }

        if (this.explorerCurrentState.lastDecision.equals("land") && this.explorerCurrentState.starting) {
            this.explorerCurrentState.lastDecision = "explore";
            return Explore.explore("Exploring first tile.");
        }

        if ((this.explorerCurrentState.lastDecision.equals("explore") || this.explorerCurrentState.lastDecision.equals("exploit")) && this.explorerCurrentState.starting) {
            for (Resource currentResource : this.islandData.arenaMap.getInformation(0, 0).hasResources(this.explorerCurrentState.objectives)) {
                if (ResultsComputing.parseAmount(currentResource.getAmount()) + ResultsComputing.parseCondition(currentResource.getCondition()) >= 2) {
                    this.explorerCurrentState.exploitedObjective = currentResource.getType();
                    this.explorerCurrentState.lastDecision = "exploit";
                    return Exploit.exploit(this.explorerCurrentState.exploitedObjective, "Exploit objective present enough on initial case.");
                }
            }
            this.islandData.arenaMap.getInformation(0, 0).setAlreadyExploited(true);
            this.explorerCurrentState.starting = false;

            this.explorerCurrentState.lastDecision = "scout";
            return Scout.scout(this.islandData.arenaMap.firstDirectionToScout(0, 0), "After the initial tile has been worked out, starting to explore the island.");
        }

        if (this.explorerCurrentState.inMovement) {

            if (this.explorerCurrentState.lastDecision.equals("glimpse")) {
                int observedX = this.explorerCurrentState.x + ResultsComputing.xOffset(this.explorerCurrentState.lastGlimpseDirection) * 3;
                int observedY = this.explorerCurrentState.y + ResultsComputing.yOffset(this.explorerCurrentState.lastGlimpseDirection) * 3;

                if (!this.islandData.arenaMap.isWater(observedX, observedY)) {
                    this.explorerCurrentState.escapeMovementCount++;
                    this.explorerCurrentState.lastMoveDirection = this.explorerCurrentState.lastGlimpseDirection;
                    this.explorerCurrentState.lastDecision = "move";
                    return Move.move(this.explorerCurrentState.lastMoveDirection, "Move after finding a possible direction without water.");
                }
                else {
                    this.explorerCurrentState.lastGlimpseDirection = this.islandData.arenaMap.firstDirectionToGlimpse(this.explorerCurrentState.x, this.explorerCurrentState.y);
                    return Glimpse.glimpse(this.explorerCurrentState.lastGlimpseDirection, 4, "Direction found with water...");
                }
            }

            if (this.explorerCurrentState.lastDecision.equals("move")) {
                if (this.explorerCurrentState.escapeMovementCount < 3) {
                    this.explorerCurrentState.escapeMovementCount++;
                    return Move.move(this.explorerCurrentState.lastMoveDirection, "Continuing escape moves.");
                }
                else {
                    this.explorerCurrentState.inMovement = false;
                }
            }
        }

        if (this.explorerCurrentState.lastDecision.equals("scout") || this.explorerCurrentState.lastDecision.equals("land")) {
            while (this.explorerCurrentState.lastScoutDirection < 4) {
                if (this.islandData.arenaMap.isAlreadyScouted(this.explorerCurrentState.x + ResultsComputing.xOffset(this.explorerCurrentState.lastScoutDirection), this.explorerCurrentState.y + ResultsComputing.yOffset(this.explorerCurrentState.lastScoutDirection))) {
                    if (this.islandData.arenaMap.getInformation(this.explorerCurrentState.x + ResultsComputing.xOffset(this.explorerCurrentState.lastScoutDirection), this.explorerCurrentState.y + ResultsComputing.yOffset(this.explorerCurrentState.lastScoutDirection)).isAlreadyExploited() == false) {
                        for (Resource currentResource : this.islandData.arenaMap.getInformation(this.explorerCurrentState.x + ResultsComputing.xOffset(this.explorerCurrentState.lastScoutDirection), this.explorerCurrentState.y + ResultsComputing.yOffset(this.explorerCurrentState.lastScoutDirection)).hasResources(this.explorerCurrentState.objectives)) {
                            this.explorerCurrentState.exploitedObjective = currentResource.getType();
                            this.explorerCurrentState.hasObjective = true;
                            this.explorerCurrentState.lastMoveDirection = this.explorerCurrentState.lastScoutDirection;
                            this.explorerCurrentState.lastScoutDirection = 0;
                            this.explorerCurrentState.lastDecision = "move";
                            return Move.move(this.explorerCurrentState.lastMoveDirection, "If after scouting a possible objective has been found, moving to it.");
                        }
                    }
                    this.islandData.arenaMap.getInformation(this.explorerCurrentState.x + ResultsComputing.xOffset(this.explorerCurrentState.lastScoutDirection), this.explorerCurrentState.y + ResultsComputing.yOffset(this.explorerCurrentState.lastScoutDirection)).setAlreadyExploited(true);
                    this.explorerCurrentState.lastScoutDirection++;
                }
                else {
                    break;
                }
            }

            if (this.explorerCurrentState.lastScoutDirection < 4) {
                this.explorerCurrentState.lastDecision = "scout";
                return Scout.scout(this.explorerCurrentState.lastScoutDirection, "Scouting next tile, direction: " + this.explorerCurrentState.lastScoutDirection + " | current: " + this.islandData.arenaMap.isAlreadyScouted(this.explorerCurrentState.x, this.explorerCurrentState.y) + " | N: " + this.islandData.arenaMap.isAlreadyScouted(this.explorerCurrentState.x, this.explorerCurrentState.y + 1) + " | E: " + this.islandData.arenaMap.isAlreadyScouted(this.explorerCurrentState.x + 1, this.explorerCurrentState.y) + " | S: " + this.islandData.arenaMap.isAlreadyScouted(this.explorerCurrentState.x, this.explorerCurrentState.y - 1) + " | W: " + this.islandData.arenaMap.isAlreadyScouted(this.explorerCurrentState.x - 1, this.explorerCurrentState.y));
            }
            else {
                this.explorerCurrentState.lastScoutDirection = 0;
                this.explorerCurrentState.lastDecision = "glimpse";
                return Glimpse.glimpse(this.islandData.arenaMap.firstDirectionToGlimpse(this.explorerCurrentState.x, this.explorerCurrentState.y), 2, "No suitable resources found. Start glimpsing, direction: " + this.explorerCurrentState.lastGlimpseDirection + " | current: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x, this.explorerCurrentState.y) + " | N: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x, this.explorerCurrentState.y + 1) + " | E: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x + 1, this.explorerCurrentState.y) + " | S: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x, this.explorerCurrentState.y - 1) + " | W: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x - 1, this.explorerCurrentState.y));
            }

        }

        if (this.explorerCurrentState.lastDecision.equals("glimpse")) {
            while (this.explorerCurrentState.lastGlimpseDirection < 4) {
                if (this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x + ResultsComputing.xOffset(this.explorerCurrentState.lastGlimpseDirection), this.explorerCurrentState.y + ResultsComputing.yOffset(this.explorerCurrentState.lastGlimpseDirection))) {
                    this.explorerCurrentState.lastGlimpseDirection++;
                }
                else {
                    break;
                }
            }

            if (this.explorerCurrentState.lastGlimpseDirection < 4) {
                return Glimpse.glimpse(this.explorerCurrentState.lastGlimpseDirection, 2, "Continuing to glimpsing, direction: " + this.explorerCurrentState.lastGlimpseDirection + " | current: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x, this.explorerCurrentState.y) + " | N: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x, this.explorerCurrentState.y + 1) + " | E: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x + 1, this.explorerCurrentState.y) + " | S: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x, this.explorerCurrentState.y - 1) + " | W: " + this.islandData.arenaMap.isAlreadyGlimpsed(this.explorerCurrentState.x - 1, this.explorerCurrentState.y));
            }
            else {

                if (this.islandData.arenaMap.getInformation(this.explorerCurrentState.x, this.explorerCurrentState.y).getTileVisits() > 1) {
                    int escapeDirection = this.islandData.arenaMap.firstDirectionToGlimpse(this.explorerCurrentState.x, this.explorerCurrentState.y);

                    if (escapeDirection != -1) {
                        this.explorerCurrentState.inMovement = true;
                        this.explorerCurrentState.escapeMovementCount = 0;
                        this.explorerCurrentState.lastGlimpseDirection = escapeDirection;
                        this.explorerCurrentState.lastDecision = "glimpse";
                        return Glimpse.glimpse(this.explorerCurrentState.lastGlimpseDirection, 4, "Trying to escape loop movement.");
                    }
                }
                this.explorerCurrentState.lastMoveDirection = this.islandData.arenaMap.lessWaterDirection(this.explorerCurrentState.x, this.explorerCurrentState.y, this.explorerCurrentState.lastMoveDirection);
                if (this.explorerCurrentState.lastScoutDirection == -1) {
                    this.explorerCurrentState.lastDecision = "land";
                    return Land.land(this.explorerCurrentState.startCreek, 2, "Best tile was more than 90% OCEAN, so better reland");
                }
                this.explorerCurrentState.lastGlimpseDirection = 0;
                this.explorerCurrentState.lastDecision = "move";
                return Move.move(this.explorerCurrentState.lastMoveDirection, debugString);
            }
        }

        if (this.explorerCurrentState.lastDecision.equals("move")) {
            if (this.explorerCurrentState.hasObjective) {
                this.explorerCurrentState.lastDecision = "explore";
                return Explore.explore("Afer moving to a possible objective, exploring it.");
            }
            else {
                this.explorerCurrentState.lastDecision = "scout";
                this.explorerCurrentState.lastScoutDirection = this.islandData.arenaMap.firstDirectionToScout(this.explorerCurrentState.x, this.explorerCurrentState.y);
                return Scout.scout(this.explorerCurrentState.lastScoutDirection, "After moving because nothing interesting was found, restart scouting.");
            }
        }

        if ((this.explorerCurrentState.lastDecision.equals("explore") || this.explorerCurrentState.lastDecision.equals("exploit")) && this.explorerCurrentState.hasObjective) {
            for (Resource currentResource : this.islandData.arenaMap.getInformation(this.explorerCurrentState.x, this.explorerCurrentState.y).hasResources(this.explorerCurrentState.objectives)) {
                if (ResultsComputing.parseAmount(currentResource.getAmount()) + ResultsComputing.parseCondition(currentResource.getCondition()) >= 3) {
                    this.explorerCurrentState.exploitedObjective = currentResource.getType();
                    this.explorerCurrentState.lastDecision = "exploit";
                    return Exploit.exploit(this.explorerCurrentState.exploitedObjective, "After exploring, the objective is exploitable.");
                }
            }
            this.islandData.arenaMap.getInformation(this.explorerCurrentState.x, this.explorerCurrentState.y).setAlreadyExploited(true);
            this.explorerCurrentState.hasObjective = false;
        }

        if (this.explorerCurrentState.lastDecision.equals("exploit") ||this.explorerCurrentState.lastDecision.equals("explore")) {
            this.explorerCurrentState.lastDecision = "scout";
            this.explorerCurrentState.lastScoutDirection = this.islandData.arenaMap.firstDirectionToScout(this.explorerCurrentState.x, this.explorerCurrentState.y);
            return Scout.scout(this.explorerCurrentState.lastScoutDirection, "After exploring and the resource was not exploitable or after exploiting it, restart scouting.");
        }



        this.explorerCurrentState.lastDecision = "exit";
        return Exit.exit("FINAL EXIT");

    }
}
