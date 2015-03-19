package fr.unice.polytech.ogl.islbb;

import eu.ace_design.island.bot.*;
import fr.unice.polytech.ogl.islbb.actions.*;
import fr.unice.polytech.ogl.islbb.reports.IslandMap;
import fr.unice.polytech.ogl.islbb.reports.IslandTile;
import fr.unice.polytech.ogl.islbb.reports.POI;
import fr.unice.polytech.ogl.islbb.reports.Resource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui implemente les différentes actions du robot
 */
public class Explorer implements IExplorerRaid {

/*    public Boolean hasMoved;
    public Initialization explorerInitialization;
    public int decision;
    public Data data;
    public Boolean hasScouted;
    public int card;
    public Comportement comport;
    String direction;
    static public int NB_MOVE_MAX = 20;
    public String lastDirection;
    public int resultExploit = 0;
    public Initialization init;
    //Montant de la première ressource a exploiter pour finir la mission*/

    private Initialization startInformation;

    private int budget;

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

/*        data = new Data();
        hasMoved=false;
        decision = 0;
        hasScouted = false;
        comport = new Comportement();
        card = 0;
        this.explorerInitialization = new Initialization(context);
        this.decision = 0;
        direction="N";*/
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

        // TODO 3 OPT : Définir les seuils dans le pourcentage du budget à partir desquels il faut décider de rentrer.
        // Si les objectifs ont étés remplis (lol) on peut rentrer.
        if ((this.currentAmount >= this.startInformation.getAmount().get(0)) || ((this.budget < 150) && (this.landedMen > 1)) || ((this.budget < 50) && (this.landedMen == 1))) {
            this.lastDecision = "exit";
            return Exit.exit();
        }

        // On Scout dans toutes les directions au lancement
        if (((this.lastDecision == "land") && (this.hasObjective == false) && (this.reachingLastObjective == false)) || ((this.lastDecision == "scout") && (this.lastScoutDirection < 4))) {

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
        if ((this.lastDecision == "scout") && (this.lastScoutDirection >= 4)) {
            this.lastScoutDirection = 0;
            while (this.lastScoutDirection < 4) {
                this.scoutedX = this.currentX + ResultsComputing.xOffset(lastScoutDirection);
                this.scoutedY = this.currentY + ResultsComputing.yOffset(lastScoutDirection);

                // Une case avec la ressource adapté a été trouvée, on débarque avec 10 personnes dans le but de l'exploiter.
                if (this.arenaMap.getInformation(this.scoutedX, this.scoutedY).hasResource(this.startInformation.getResource().get(0)) != null) {
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
        if (((this.lastDecision == "land") || (this.lastDecision == "move")) && (this.hasObjective == true)) {
            // On avance jusqu'à être aux bonnes coordonnées.
            // TODO 1 DUP : Il faut une méthode qui renvoie les Move (un par un) à faire pour se déplacer d'une case (x1, y1) à une autre case (x2, y2).
            while ((this.currentX != this.objectiveX) && (this.currentY != this.objectiveY)) {
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
            return Exploit.exploit(this.startInformation.getResource().get(0));
        }

        // Après une exploitation, on redébarque avec un explorateur.
        if (this.lastDecision == "exploit") {
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
            while ((this.currentX != this.objectiveX) && (this.currentY != this.objectiveY)) {
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
            this.lastScoutDirection = 0;
        }

        // Quand on arrive sur une case dans le but d'explorer.
        if (this.movedToSearch == true) {
            this.movedToSearch = false;

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

/*        //Si nous avons récolté toutes les ressources demandées
        if(resultExploit == exploitMission1) {
            return Exit.exit().toString();
        }
        if (this.decision == 0) {
            this.decision++;
            return Land.land(this.explorerInitialization.getCreek(), 1).toString();
        }
        if(hasMoved&&comport.hasWood(direction)){
            hasMoved=false;
            decision++;
            return Exploit.exploit("WOOD").toString();
        }
        hasMoved=false;
        if(card<4){
            hasScouted =false;
        }
        if (this.decision == NB_MOVE_MAX) {
            return Exit.exit().toString();
        }
        if (hasScouted) {
            decision++;
            card = 0;
            hasMoved=true;
            //On stock la dernière direction
            lastDirection = direction;
            return Move.move(direction).toString();
        }
        if (!hasScouted) {
            decision++;
            card++;
            *//*
            if(lastDirection.equals((data.getCardinaux(card)))) {
                return null;
            }
            *//*
            return Scout.scout(data.getCardinaux(card - 1));
        }
        return Exit.exit().toString();*/


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
            IslandTile scoutedTile;

            // On récupère la liste des ressources et leurs conditions.
            List<Resource> tileResources = new ArrayList<Resource>();
            JSONArray resourcesArray = JSONResult.getJSONArray("resources");
            for (int i = 0 ; i < resourcesArray.length() ; i++) {
                JSONObject resourceCondition = resourcesArray.getJSONObject(i);
                tileResources.add(new Resource(resourceCondition.getString("resource"), resourceCondition.getString("amount"), resourceCondition.getString("cond")));
            }

            // On récupère la liste des POI.
            List<POI> tilePOIs = new ArrayList<POI>();
            JSONArray poisArray = JSONResult.getJSONArray("pois");
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
            updatedTile.removeResource(this.startInformation.getResource().get(0));
            this.currentAmount += JSONResult.getJSONObject("extras").getInt("amount");
        }

        // Après un déplacement, on met à jour l'altitude, si on la connaît.
        else if ((this.lastDecision.equals("move")) && (ResultsComputing.getStatus(results))) {
            if (this.arenaMap.isAlreadyScouted(this.currentX, this.currentY)) {
                this.currentAltitude += this.arenaMap.getInformation(this.currentX, this.currentY).getAltitude();
            }
        }

        // On enlève le coût de l'action au budget.
        this.budget -= JSONResult.getInt("cost");
/*
        if (this.decision != 1) {
            if(!hasScouted) {
                comport.setObj(results);
                comport.getScout(results,data.getCardinaux(card - 1));
                if(card>=4){
                    hasScouted =true;
                }
            }
            if(hasScouted &&!hasMoved){
                direction=comport.takeDirection();
            }
            */
/*
            if(hasmoove) {
                comport.setObj(results);
                //Vrai, c'est bien déplacé
                if(comport.getStatus()) {
                    setClassDirection(direction);
                }
            }
            *//*

            //Compteur de ressource exploité au total
            if (comport.getExploitAmount() > 0 ) {
                resultExploit = resultExploit + comport.getExploitAmount();
            }
        }
*/


    }


}