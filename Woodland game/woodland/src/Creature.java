import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObjectBuilder;
import javax.json.Json;
public abstract class Creature {
    // Attributes
    private String name;
    private int attackValue;
    private Map<Animal, Integer> canAttack;
    private boolean confused;
    private Animal charmedBy; 
    private int turnsCharmed; 
    private String shortname;
    private String description;

    // Constructor
    public Creature(String name, int attackValue) {
        this.name = name;
        this.attackValue = attackValue;
        this.canAttack = new HashMap<>();
        this.confused = false;
        this.charmedBy = null; 
        this.turnsCharmed = 0; 
        this.shortname = "";
        this.description = "";
    }

    // Methods
    public void attack(Animal animal) {
        // Calls the attacked method on the animal, which should take care of decreasing its health
        if (this.turnsCharmed == 0 && !this.confused) {
            animal.attacked();
        }
    }

    public void charm(Animal animal, int effect) {
        this.charmedBy = animal;
        this.turnsCharmed = effect;
    }

    public void confuse() {
        this.confused = true;
    }

    public void decrementCharmDuration() {
        if (this.turnsCharmed > 0) {
            this.turnsCharmed--;
            if (this.turnsCharmed == 0) {
                this.charmedBy = null;
            }
        }
    }

    // Check if the creature is charmed by the given animal
    public boolean isCharmedBy(Animal animal) {
        return this.charmedBy != null && this.charmedBy.equals(animal);
    }

    public boolean isConfused() {
        return confused;
    }

    public void setConfused(boolean confused) {
        this.confused = confused;
    }

    public Animal getCharmedBy() {
        return this.charmedBy;
    }

    public String getInitials() {
        String[] words = name.split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String word : words) {
            initials.append(word.charAt(0));
        }
        return initials.toString().toUpperCase();
    }

    // Additional getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public Map<Animal, Integer> getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(Map<Animal, Integer> canAttack) {
        this.canAttack = canAttack;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public JsonObjectBuilder generateJSON(){
            JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("attackValue", getAttackValue());
        builder.add("confused", isConfused());
        // Serialize charmedBy as the name of the charmer animal if not null
        if (getCharmedBy() != null) {
            builder.add("charmedBy", getCharmedBy().getName());
        } else {
            builder.addNull("charmedBy");
        }
        builder.add("turnsCharmed", turnsCharmed);
        builder.add("shortname", getShortname());
        builder.add("description", getDescription());
        // Add more attributes if necessary
        return builder;
    }
    
    }    

