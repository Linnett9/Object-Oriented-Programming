import java.util.Map;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public abstract class Spell {
    public enum Type {
        DETECT, HEAL, SHIELD, CONFUSE, CHARM
    }

    private String name;
    private Type type;
    private int effect; // This can represent different things depending on the spell
    private String description;

    // Constructor
    public Spell(String name, Type type, int effect, String description) {
        this.name = name;
        this.type = type;
        this.effect = effect;
        this.description = description;
    }

    // Abstract method to be implemented by subclasses
    public abstract void castSpell(Animal animal, Square square);

    // Getters
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getEffect() {
        return effect;
    }

    public String getDescription() {
        return description;
    }

    // Generate JSON representation of the spell
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("type", getType().toString());
        builder.add("effect", getEffect());
        builder.add("description", getDescription());
        return builder;
    }
}
