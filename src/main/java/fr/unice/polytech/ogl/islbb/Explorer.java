package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;

import fr.unice.polytech.ogl.islbb.actions.*;
import fr.unice.polytech.ogl.islbb.reports.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe qui implemente les différentes actions du robot
 */
public class Explorer implements IExplorerRaid {

    private Initialization startInformation;

    private int budget;

    private String objectiveResource;
    private int objectiveAmount;
    private int objectiveRank;

    private int moveBudget;

    private IslandMap arenaMap;
    private int currentX, currentY;
    private int currentAltitude;
    private int scoutedX, scoutedY;

    private String lastDecision;
    private int lastScoutDirection;

    private Data directions;

    private Boolean hasObjective;
    private int objectiveX, objectiveY;
    private boolean movedToSearch;

    private int landedMen;
    private int currentAmount;
    private boolean reachingLastObjective;


    /**
     * Initialize the explorer with the given objectives.
     *
     * @param context Objectives given by the arena.
     */
    @Override
    public void initialize(String context) {

        this.startInformation = new Initialization(context);
        this.budget = this.startInformation.getBudget();
        this.arenaMap = new IslandMap();
        this.currentX = 0;
        this.currentY = 0;
        this.lastDecision = null;
        this.lastScoutDirection = 0;
        this.directions = new Data();
        this.hasObjective = false;
        this.movedToSearch = false;
        this.landedMen = 0;
        this.reachingLastObjective = false;
        this.currentAmount = 0;
        this.currentAltitude = 0;
        this.moveBudget = 0;

        // TODO 4 DUP : Duplication de la boucle pour déterminer la ressource à ramener.
        int min = this.startInformation.getAmount(0);

        for (int i = 0 ; i < this.startInformation.getAmounts().size() ; i++) {
            if (this.startInformation.getAmount(i) <= min) {
                this.objectiveResource = this.startInformation.getResource(i);
                this.objectiveAmount = this.startInformation.getAmount(i);
                this.objectiveRank = i;
                min = this.objectiveAmount;
            }
        }

    }

    /**
     * The AI of the explorer, makes his decisions.
     *
     * @return Decision taken (JSON format).
     */
    @Override
    public String takeDecision() {

        // Première décision (aucune dernière décision) on débarque avec une personne.
        if (this.lastDecision == null) {
            this.landedMen = 1;
            this.lastDecision = "land";
            return Land.land(this.startInformation.getCreek(), 1);
        }

        if (this.currentAmount >= this.objectiveAmount) {
            List<String> newResources = this.startInformation.getResources();
            List<Integer> newAmounts = this.startInformation.getAmounts();
            newResources.remove(this.objectiveRank);
            newAmounts.remove(this.objectiveRank);

            this.startInformation.setResources(newResources);
            this.startInformation.setAmounts(newAmounts);

            this.currentAmount = 0;

            if (this.startInformation.getResources().isEmpty()) {
                this.lastDecision = "exit";
                return Exit.exit();
            }

            // TODO 4 DUP : Duplication de la boucle pour déterminer la ressource à ramener.
            int min = this.startInformation.getAmount(0);

            for (int i = 0 ; i < this.startInformation.getAmounts().size() ; i++) {
                if (this.startInformation.getAmount(i) < min) {
                    this.objectiveResource = this.startInformation.getResource(i);
                    this.objectiveAmount = this.startInformation.getAmount(i);
                    this.objectiveRank = i;
                    min = this.objectiveAmount;
                }
            }
        }

        // TODO 3 OPT : Définir les seuils dans le pourcentage du budget à partir desquels il faut décider de rentrer.
        // Si les objectifs ont étés remplis (lol) on peut rentrer.
        if (this.moveBudget >= this.budget) {
            this.lastDecision = "exit";
            return Exit.exit();
        }

        // On Scout dans toutes les directions au lancement
        if (((Objects.equals(this.lastDecision, "land")) && (this.hasObjective == false) && (this.reachingLastObjective == false)) || ((Objects.equals(this.lastDecision, "scout")) && (this.lastScoutDirection < 4))) {

            // On essaye de trouver une direction dans laquelle Scout.
            // Tant que la direction a déjà été Scout on regarde la prochaine.
            // Si la direction n'a pas été Scout, on interrompt la boucle pour la choisir.
            // TODO 2 DUP : Il faut une méthode qui sorte la prochaine direction à Scout en fonction de la case (x, y) donnée.
            while (this.lastScoutDirection < 4) {
                if (this.arenaMap.isAlreadyScouted(this.currentX + ResultsComputing.xOffset(this.lastScoutDirection), this.currentY + ResultsComputing.yOffset(this.lastScoutDirection))) {
                    this.lastScoutDirection++;
                }
                else {
                    break;
                }
            }

            // On Scout la première direction disponible (sens horaire)
            // Si toutes les directions ont été Scout, rien ne se passe dans cette condition et on passe à la suivante.
            // Sinon on Scout celle trouvée dans la boucle précédente.
            if (this.lastScoutDirection < 4) {
                // On définit les coordonnées de la case qu'on va Scout en fonction de l'orientation, pour ensuite enregistrer les informations dans la carte.
                this.scoutedX = this.currentX + ResultsComputing.xOffset(lastScoutDirection);
                this.scoutedY = this.currentY + ResultsComputing.yOffset(lastScoutDirection);
                this.lastDecision = "scout";
                return Scout.scout(this.directions.getCardinaux(this.lastScoutDirection));
            }
        }

        // Après avoir Scout dans les 4 directions, on choisit la première case adapté à nos besoins, s'il y en a une.
        if ((Objects.equals(this.lastDecision, "scout")) && (this.lastScoutDirection >= 4)) {
            this.lastScoutDirection = 0;
            while (this.lastScoutDirection < 4) {
                this.scoutedX = this.currentX + ResultsComputing.xOffset(lastScoutDirection);
                this.scoutedY = this.currentY + ResultsComputing.yOffset(lastScoutDirection);

                // Une case avec la ressource adapté a été trouvée, on débarque avec 10 personnes dans le but de l'exploiter.
                if (this.arenaMap.getInformation(this.scoutedX, this.scoutedY).hasResource(this.objectiveResource) != null) {
                    this.objectiveX = this.scoutedX;
                    this.objectiveY = this.scoutedY;
                    this.hasObjective = true;

                    // TODO 4 IMP : Attention à la position du débarquement lors de la gestion de multiples criques.
                    this.currentX = 0;
                    this.currentY = 0;

                    this.lastScoutDirection = 0;
                    this.landedMen = 10;
                    this.lastDecision = "land";
                    return Land.land(this.startInformation.getCreek(), 10);
                }

                this.lastScoutDirection++;
            }

            // Aucune des cases aux alentours contient la ressource appropriée ? On avance vers la première case qui n'est pas de l'eau (sens horaire).
            this.lastScoutDirection = 0;
            while (this.lastScoutDirection < 4) {
                if (!this.arenaMap.isWater(this.currentX + ResultsComputing.xOffset(this.lastScoutDirection), this.currentY + ResultsComputing.yOffset(this.lastScoutDirection))) {
                    this.currentX += ResultsComputing.xOffset(this.lastScoutDirection);
                    this.currentY += ResultsComputing.yOffset(this.lastScoutDirection);
                    this.movedToSearch = true;
                    this.lastScoutDirection = 0;
                    this.lastDecision = "move";
                    return Move.move(this.directions.getCardinaux(this.lastScoutDirection));
                }
                this.lastScoutDirection++;
            }

        }

        // Quand on a choisi une case on avance jusqu'à celle-ci.
        if (((Objects.equals(this.lastDecision, "land")) || (Objects.equals(this.lastDecision, "move"))) && (this.hasObjective == true)) {
            // On avance jusqu'à être aux bonnes coordonnées.
            // TODO 1 DUP : Il faut une méthode qui renvoie les Move (un par un) à faire pour se déplacer d'une case (x1, y1) à une autre case (x2, y2).
            while ((this.currentX != this.objectiveX) || (this.currentY != this.objectiveY)) {
                this.lastDecision = "move";
                if (this.currentY < this.objectiveY) {
                    this.currentY++;
                    return Move.move("N");
                }
                if (this.currentY > this.objectiveY) {
                    this.currentY--;
                    return Move.move("S");
                }
                if (this.currentX < this.objectiveX) {
                    this.currentX++;
                    return Move.move("E");
                }
                if (this.currentX > this.objectiveX) {
                    this.currentX--;
                    return Move.move("W");
                }
            }
            // Une fois aux bonnes coordonnées, on exploite.
            this.hasObjective = false;
            this.lastDecision = "exploit";
            return Exploit.exploit(this.startInformation.getResources().get(0));
        }

        // Après une exploitation, on redébarque avec un explorateur.
        if (Objects.equals(this.lastDecision, "exploit")) {
            this.currentX = 0;
            this.currentY = 0;
            this.reachingLastObjective = true;
            this.landedMen = 1;
            this.lastDecision = "land";
            return Land.land(this.startInformation.getCreek(), 1);
        }

        // Après avoir débarqué l'explorateur post-exploitation, on le fait revenir au lieu d'exploitation comme point de départ pour son exploration.
        if (this.reachingLastObjective == true) {
            // TODO 1 DUP : Il faut une méthode qui renvoie les Move (un par un) à faire pour se déplacer d'une case (x1, y1) à une autre case (x2, y2).
            while ((this.currentX != this.objectiveX) || (this.currentY != this.objectiveY)) {
                this.lastDecision = "move";
                if (this.currentY < this.objectiveY) {
                    this.currentY++;
                    return Move.move("N");
                }
                if (this.currentY > this.objectiveY) {
                    this.currentY--;
                    return Move.move("S");
                }
                if (this.currentX < this.objectiveX) {
                    this.currentX++;
                    return Move.move("E");
                }
                if (this.currentX > this.objectiveX) {
                    this.currentX--;
                    return Move.move("W");
                }
            }
            // L'explorateur est arrivé à l'ancien lieu d'exploitation et a avancé pour arrivé là (movedToSearch), il est en position pour recommencer à explorer.
            this.reachingLastObjective = false;
            this.movedToSearch = true;
        }

        // Quand on arrive sur une case dans le but d'explorer.
        if (this.movedToSearch == true) {
            this.movedToSearch = false;
            this.lastScoutDirection = 0;

            // On essaye de trouver une direction dans laquelle Scout.
            // Tant que la direction a déjà été Scout on regarde la prochaine.
            // Si la direction n'a pas été Scout, on interrompt la boucle pour la choisir.
            // TODO 2 DUP : Il faut une méthode qui sorte la prochaine direction à Scout en fonction de la case (x, y) donnée.
            while (this.lastScoutDirection < 4) {
                if (this.arenaMap.isAlreadyScouted(this.currentX + ResultsComputing.xOffset(this.lastScoutDirection), this.currentY + ResultsComputing.yOffset(this.lastScoutDirection))) {
                    this.lastScoutDirection++;
                }
                else {
                    break;
                }
            }

            // On Scout la première direction disponible (sens horaire)
            // Si toutes les directions ont été Scout, rien ne se passe dans cette condition et on passe à la suivante.
            // Sinon on Scout celle trouvée dans la boucle précédente.
            if (this.lastScoutDirection < 4) {
                // On définit les coordonnées de la case qu'on va Scout en fonction de l'orientation, pour ensuite enregistrer les informations dans la carte.
                this.scoutedX = this.currentX + ResultsComputing.xOffset(lastScoutDirection);
                this.scoutedY = this.currentY + ResultsComputing.yOffset(lastScoutDirection);
                this.lastDecision = "scout";
                return Scout.scout(this.directions.getCardinaux(this.lastScoutDirection));
            }
        }

        // Dans le cas où aucune décision n'est prise, on arrête.
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
        JSONObject JSONResult = new JSONObject(results);
        // Si la dernière action a été un Scout.
        if ((this.lastDecision.equals("scout")) && (ResultsComputing.getStatus(results))) {

            List<Resource> tileResources = new ArrayList<Resource>();
            JSONArray resourcesArray = JSONResult.getJSONObject("extras").getJSONArray("resources");
            for (int i = 0 ; i < resourcesArray.length() ; i++) {
                tileResources.add(new Resource(resourcesArray.getString(i)));
            }

            IslandTile newTile = new IslandTile(JSONResult.getJSONObject("extras").getInt("altitude") + this.currentAltitude, tileResources);
            this.arenaMap.addTile(this.scoutedX, this.scoutedY, newTile);
        }

        // Si la dernière action a été un Explore.
        else if ((this.lastDecision.equals("explore")) && (ResultsComputing.getStatus(results))) {
            JSONResult = new JSONObject(results);

            // On récupère la liste des ressources et leurs conditions.
            List<Resource> tileResources = new ArrayList<Resource>();
            JSONArray resourcesArray = JSONResult.getJSONObject("extras").getJSONArray("resources");
            for (int i = 0 ; i < resourcesArray.length() ; i++) {
                JSONObject resourceCondition = resourcesArray.getJSONObject(i);
                tileResources.add(new Resource(resourceCondition.getString("resource"), resourceCondition.getString("amount"), resourceCondition.getString("cond")));
            }

            // On récupère la liste des POI.
            List<POI> tilePOIs = new ArrayList<POI>();
            JSONArray poisArray = JSONResult.getJSONObject("extras").getJSONArray("pois");
            for (int i = 0 ; i < resourcesArray.length() ; i++) {
                JSONObject poiInformation = poisArray.getJSONObject(i);
                tilePOIs.add(new POI(poiInformation.getString("kind"), poiInformation.getString("id")));
            }

            // On met à jour la case dans la Map si elle a déjà été Scout, sinon on crée une nouvelle entrée.
            if (this.arenaMap.isRegistered(this.currentX, this.currentY)) {
                this.arenaMap.getInformation(this.currentX, this.currentY).exploreTile(tileResources, tilePOIs);
            }
            else {
                this.arenaMap.addTile(this.currentX, this.currentY, new IslandTile(tileResources, tilePOIs));
            }

        }

        // Après une exploitation, on enlève la ressource de la case.
        else if ((this.lastDecision.equals("exploit")) && (ResultsComputing.getStatus(results))) {
            IslandTile updatedTile = this.arenaMap.getInformation(this.currentX, this.currentY);
            updatedTile.removeResource(this.objectiveResource);
            this.currentAmount += JSONResult.getJSONObject("extras").getInt("amount");
        }

        // Après un déplacement, on met à jour l'altitude, si on la connaît.
        else if ((this.lastDecision.equals("move")) && (ResultsComputing.getStatus(results))) {
            if (this.arenaMap.isAlreadyScouted(this.currentX, this.currentY)) {
                this.currentAltitude += this.arenaMap.getInformation(this.currentX, this.currentY).getAltitude();
            }
            this.moveBudget += JSONResult.getInt("cost");
        }

        else if ((this.lastDecision.equals("land")) && (ResultsComputing.getStatus(results))) {
            this.moveBudget = 0;
        }

        // On enlève le coût de l'action au budget.
        this.budget -= JSONResult.getInt("cost");

    }


}