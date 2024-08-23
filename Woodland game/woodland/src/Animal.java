import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonArrayBuilder;
import javax.json.JsonArray;

public abstract class Animal {
    // Attributes
    private String name;
    private Map<Spell, Integer> spells;
    private String direction;
    private int health;
    private int row;
    private int col;
    private Square currentSquare;
    protected Game game; 
    private boolean shielded; 

    // Constructor
    public Animal(String name, Game game) {
        this.name = name;
        this.spells = new HashMap<>();
        this.health = 100; // initial health value
        this.game = game;
        this.shielded = false;
    }
    // New method to set the shielded status
    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    // New method to check if the animal is shielded
    public boolean isShielded() {
        return shielded;
    }

    // Abstract method to be implemented by subclasses, determining if a move is valid
    public abstract boolean move(int dstRow, int dstCol);

    // Attacked by a creature on the current square
    public void attacked() {
        if (currentSquare != null && currentSquare.hasCreature()) {
            Creature creature = currentSquare.getCreature();
            this.health -= creature.getAttackValue();
            if (this.health < 0) {
                this.health = 0;
            }
        }
    }

    // Checks if the animal has won
    public boolean hasWon() {
        // Assuming the top row of the board is row 0
        return this.row == 0;
    }

    // Adds a spell to the animal's collection
    public void addSpell(Spell spell) {
        spells.put(spell, spells.getOrDefault(spell, 0) + 1);
    }

    // Updates the animal's state based on the spell
    public void update(Spell spell) {
        // Placeholder for spell update logic
    }

    // Picks up a spell from the square
    public void pickUpSpell(Spell spell) {
        addSpell(spell);
    }

    // Detects creatures adjacent to the animal
    public void detectCreature() {
        // Placeholder for creature detection logic
    }

    // Sets the current square of the animal
    void setCurrentSquare(Square square) {
        if (this.currentSquare != null) {
            this.currentSquare.setAnimal(null);
        }
        this.currentSquare = square;
        if (square != null) {
            square.setAnimal(this);
            this.row = square.getRow();
            this.col = square.getCol();
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public Map<Spell, Integer> getSpells() {
        return spells;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    // Method to get a description of the animal's generic attributes
    public String getDescription() {
        return String.format("Name: %s%nHealth: %d%nRow: %d%nCol: %d",
            getName(),
            getHealth(),
            getRow(),
            getCol());
    }
    // Abstract method to generate a JSON representation of the animal
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("type", "Animal"); // Add the type field
        builder.add("description", getDescription());
        builder.add("life", getHealth());
        builder.add("row", getRow());
        builder.add("col", getCol());

             // Add the spells array
             JsonArrayBuilder spellsArrayBuilder = Json.createArrayBuilder();
             for (Map.Entry<Spell, Integer> entry : getSpells().entrySet()) {
                 // Here we call the generateJSON method of Spell and add the resulting JsonObjectBuilder
                 JsonObjectBuilder spellBuilder = entry.getKey().generateJSON();
                 spellBuilder.add("amount", entry.getValue());
                 spellsArrayBuilder.add(spellBuilder);
             }
             builder.add("spells", spellsArrayBuilder);
     
             // Add any additional fields that may be required
             // ...
     
             return builder;
         }
     
}