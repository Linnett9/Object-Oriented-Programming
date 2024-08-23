import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;
//import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameState {
    private Game game;
    private BoardInitializer boardInitializer;
    private Random random;

    public GameState() {
        this.random = new Random();
        this.game = new Game();
        this.boardInitializer = new BoardInitializer(game.getBoard(), game, random);
        boardInitializer.initializeAnimals();
        boardInitializer.initializeCreatures();
        boardInitializer.initializeSpells();
    }

    // Method to serialize the game state to JSON
    public String toJson() {
        JsonObjectBuilder gameBuilder = Json.createObjectBuilder();

        gameBuilder.add("turn", game.getTurn());
        gameBuilder.add("isGameOver", game.isGameOver());


        JsonObjectBuilder boardBuilder = Json.createObjectBuilder();
        Square[][] board = boardInitializer.getBoard();
        JsonArrayBuilder rowArray = Json.createArrayBuilder();
        for (int row = 0; row < board.length; row++) {
            JsonArrayBuilder colArray = Json.createArrayBuilder();
            for (int col = 0; col < board.length; col++) {
                Square sq = board[row][col];
                
                // If the square has an animal, serialize the animal and add to the column array
                if (sq.hasAnimal()) {
                    Animal animal_ = sq.getAnimal();
                    JsonObjectBuilder animalJSON = animal_.generateJSON();
                    colArray.add(animalJSON);
                }
        
                // If the square has a creature, serialize the creature and add to the column array
                if (sq.hasCreature()) {
                    Creature creature_ = sq.getCreature();
                    JsonObjectBuilder creatureJSON = creature_.generateJSON();
                    colArray.add(creatureJSON);
                }
            }
            rowArray.add(colArray);
        }

        boardBuilder.add("board", rowArray);
        boardBuilder.add("gameOver", game.isGameOver());
        boardBuilder.add("currentAnimalTurn", game.getCurrentAnimal().getName());
        boardBuilder.add("nextAnimalTurn", game.getNextAnimal().getName());
        // Status setting based on game outcome
String statusMessage = "";
switch(game.getLastOutcome()) {
    case Game.MOVE_SUCCESS:
        statusMessage = "The last move was successful.";
        break;
    case Game.MOVE_INTERRUPTED:
        statusMessage = "The last move was interrupted by a creature.";
        break;
    case Game.MOVE_INVALID:
        statusMessage = "The last move was invalid.";
        break;
    case Game.SPELL_SUCCESS:
        statusMessage = "The last spell was successful.";
        break;
    case Game.SPELL_INVALID:
        statusMessage = "The last spell was invalid.";
        break;
    case Game.GAME_WIN:
        statusMessage = "You have won the game.";
        break;
    case Game.GAME_LOST:
        statusMessage = "You have lost the game.";
        break;
    default:
        statusMessage = "Unknown status.";
}

boardBuilder.add("status", statusMessage);
        boardBuilder.add("currentAnimalTurnType", "Move"); 
        boardBuilder.add("extendedStatus", ""); 

        // Serialize animals
        JsonArrayBuilder animalsBuilder = Json.createArrayBuilder();
        for (Animal animal : game.getAnimals()) {

            JsonObjectBuilder animalBuilder = Json.createObjectBuilder();
            animalBuilder.add("type", animal.getClass().getSimpleName());
            animalBuilder.add("name", animal.getName());
            animalBuilder.add("health", animal.getHealth());
            animalBuilder.add("row", animal.getRow());
            animalBuilder.add("col", animal.getCol());

            // Serialize spells of the animaljavax.json
            JsonArrayBuilder spellsBuilder = Json.createArrayBuilder();
            for (Map.Entry<Spell, Integer> entry : animal.getSpells().entrySet()) {
                JsonObjectBuilder spellBuilder = Json.createObjectBuilder();
                spellBuilder.add("name", entry.getKey().getName());
                spellBuilder.add("count", entry.getValue());
                spellsBuilder.add(spellBuilder);
            }
            animalBuilder.add("spells", spellsBuilder);

// Serialize subclass-specific properties
if (animal instanceof Badger) {
    Badger badger = (Badger) animal;
    animalBuilder.add("hasBlackAndWhiteFace", badger.hasBlackAndWhiteFace());
    animalBuilder.add("oftenMistakenForPanda", badger.isOftenMistakenForPanda());
    animalBuilder.add("tshirtSlogan", badger.getTshirtSlogan());
} else if (animal instanceof Deer) {
    Deer deer = (Deer) animal;
    animalBuilder.add("hasAntlers", deer.hasAntlers());
    animalBuilder.add("lookingForPartner", deer.isLookingForPartner());
} else if (animal instanceof Owl) { // Changed to 'else if'
    Owl owl = (Owl) animal;
    animalBuilder.add("hasWings", owl.hasWings());
    animalBuilder.add("hasPrescriptionContactLenses", owl.hasPrescriptionContactLenses());
} else if (animal instanceof Rabbit) { // Changed to 'else if'
    Rabbit rabbit = (Rabbit) animal;
    animalBuilder.add("hasFluffyEarsAndTail", rabbit.hasFluffyEarsAndTail());
    animalBuilder.add("likesGrass", rabbit.likesGrass());
}else if (animal instanceof Fox) { // Add serialization for Fox
    Fox fox = (Fox) animal;
    animalBuilder.add("hasBushyTail", fox.hasBushyTail()); 
    animalBuilder.add("enjoysButterflies", fox.enjoysButterflies()); 
}

//  The code below was aided by professor Fahrurrozi Rahman 2023
            animalsBuilder.add(animalBuilder);
        }
        gameBuilder.add("animals", animalsBuilder);

        JsonArrayBuilder creaturesBuilder = Json.createArrayBuilder();
        
        for (int row = 0; row < BoardInitializer.ROWS; row++) {
            for (int col = 0; col < BoardInitializer.COLS; col++) {
                Square square = board[row][col];
                if (square.hasCreature()) { 
                    Creature creature = square.getCreature(); 
                    JsonObjectBuilder creatureBuilder = Json.createObjectBuilder();
                    creatureBuilder.add("type", creature.getClass().getSimpleName());
                    creatureBuilder.add("name", creature.getName());
                    creatureBuilder.add("attackValue", creature.getAttackValue());
                    creatureBuilder.add("confused", creature.isConfused());
                    creatureBuilder.add("shortname", creature.getShortname());
                    creatureBuilder.add("description", creature.getDescription());
                    creatureBuilder.add("row", row);
                    creatureBuilder.add("col", col);
                    creaturesBuilder.add(creatureBuilder);
                }
            }
        }
        gameBuilder.add("creatures", creaturesBuilder);
        
// end of assistance
        JsonObject gameJson = gameBuilder.build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(gameJson);
        }

        return stringWriter.toString();
    }

    // Method to apply an action from a JSON string
    public void applyActionFromJson(String jsonAction) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonAction))) {
            JsonObject actionObject = jsonReader.readObject();

            // Determine action type and apply action to the game state
            String actionType = actionObject.getString("action");
            switch (actionType) {
                case "move":
                    String animalName = actionObject.getString("animal");
                    int newRow = actionObject.getInt("newRow");
                    int newCol = actionObject.getInt("newCol");

                    // Find the animal by name and move it
                    for (Animal animal : game.getAnimals()) {
                        if (animal.getName().equals(animalName)) {
                            game.moveAnimal(animal, newRow, newCol);
                            break;
                        }
                    }
                    break;
                // Handle other actions...
            }
        }
    }

    // Reset the game state
    public void reset() {
        this.game = new Game();
        this.boardInitializer = new BoardInitializer(game.getBoard(), game, random);
        boardInitializer.initializeAnimals();
        boardInitializer.initializeCreatures();
        boardInitializer.initializeSpells();
    }

    public Game getGame() {
        return game;
    }
}
