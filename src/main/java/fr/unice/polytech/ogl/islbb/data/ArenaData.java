package fr.unice.polytech.ogl.islbb.data;

import fr.unice.polytech.ogl.islbb.Initialization;
import fr.unice.polytech.ogl.islbb.ResultsComputing;
import fr.unice.polytech.ogl.islbb.reports.IslandMap;
import fr.unice.polytech.ogl.islbb.reports.IslandTile;
import fr.unice.polytech.ogl.islbb.reports.Objective;
import org.json.JSONObject;

import java.util.List;

public class ArenaData {

    protected Initialization contract;
    protected IslandMap arenaMap;
    protected ExplorerData explorerInformation;
    protected ExplorerDecision explorerAI;

    public ArenaData(String givenContract) {

        this.contract = new Initialization(givenContract);
        this.arenaMap = new IslandMap();
        this.explorerInformation = new ExplorerData(this.contract);
        this.explorerAI = new ExplorerDecision(this.explorerInformation, this);

    }

    /**
     * A chaque acknowledgeDecision, on met à jour la carte de l'île, ainsi que ExplorerData.
     *
     * TESTABLE :
     *  - Après un Explore, un Scout ou un Glimpse, les IslandTile sont rajoutés dans la IslandMap.
     *    Commencer par tester si les cases sont rajoutées (bonne coordonnées).
     *    Tester si les cases rajoutées sont équivalentes au résultat des méthodes de TileProcessing pour le même JSON.
     *
     *  - Après chaque action le budget de ExplorerData décroît correctement.
     *
     *  - Après chaque Move, la position de ExplorerData est bien mise-à-jour.
     *
     *  - D'autres possibilitées sûrement...
     */
    public void update(String returnedJSON) {

        int x, y, altitude, direction;

        x = this.explorerInformation.x;
        y = this.explorerInformation.y;

        JSONObject JSONResults = new JSONObject(returnedJSON);

        if (this.explorerInformation.lastDecision.equals("land")) {
            this.explorerInformation.x = 0;
            this.explorerInformation.y = 0;
        }

        if (this.explorerInformation.lastDecision.equals("exit")) {}

        if (this.explorerInformation.lastDecision.equals("scout")) {

            direction = this.explorerInformation.lastScoutDirection;
            altitude = this.explorerInformation.altitude;

            int scoutedX = x + ResultsComputing.xOffset(direction);
            int scoutedY = y + ResultsComputing.yOffset(direction);

            if (this.arenaMap.isRegistered(scoutedX, scoutedY)) {
                this.arenaMap.getInformation(scoutedX, scoutedY).scoutTile(TileProcessing.scoutTile(JSONResults, altitude));
            } else {
                this.arenaMap.addTile(scoutedX, scoutedY, TileProcessing.scoutTile(JSONResults, altitude));
            }

        }

        if (this.explorerInformation.lastDecision.equals("move")) {

            this.explorerInformation.updatePosition();
            this.arenaMap.addVisit(this.explorerInformation.x, this.explorerInformation.y);

            if (this.arenaMap.isAlreadyScouted(this.explorerInformation.x, this.explorerInformation.y)) {
                this.explorerInformation.updateAltitude(this.arenaMap.getInformation(this.explorerInformation.x, this.explorerInformation.y).getAltitude());
            }

        }

        if (this.explorerInformation.lastDecision.equals("explore")) {

            if (this.arenaMap.isRegistered(x, y)) {
                this.arenaMap.getInformation(x, y).exploreTile(TileProcessing.exploreTile(JSONResults));
            }
            else {
                this.arenaMap.addTile(x, y, TileProcessing.exploreTile(JSONResults));
            }


        }

        if (this.explorerInformation.lastDecision.equals("exploit")) {

            for (Objective currentObjective : this.explorerInformation.objectives) {

                if (currentObjective.getObjective().equals(this.explorerInformation.exploitedObjective)) {

                    this.arenaMap.getInformation(this.explorerInformation.x, this.explorerInformation.y).removeResource(currentObjective.getObjective());
                    if (currentObjective.updateAmount(JSONResults.getJSONObject("extras").getInt("amount"))) {
                        this.explorerInformation.objectives.remove(currentObjective);
                    }

                    break;
                }

            }

        }

        if (this.explorerInformation.lastDecision.equals("glimpse")) {

            direction = this.explorerInformation.lastGlimpseDirection;

            List<IslandTile> glimpseResults = TileProcessing.glimpseTile(JSONResults);

            for (int i = 0 ; i < glimpseResults.size() ; i++) {

                int glimpsedX = this.explorerInformation.x + ResultsComputing.xOffset(direction) * i;
                int glimpsedY = this.explorerInformation.y + ResultsComputing.yOffset(direction) * i;

                if (this.arenaMap.isRegistered(glimpsedX, glimpsedY)) {
                    this.arenaMap.getInformation(glimpsedX, glimpsedY).glimpseTile(glimpseResults.get(i));
                }
                else {
                    this.arenaMap.addTile(glimpsedX, glimpsedY, glimpseResults.get(i));
                }

            }
        }

        this.explorerInformation.decreaseCost(JSONResults.getInt("cost"));

    }

    public ExplorerDecision getExplorerAI() {
        return explorerAI;
    }

    public ExplorerData getExplorerInformation() {
        return explorerInformation;
    }

    public IslandMap getArenaMap() {
        return arenaMap;
    }

}
