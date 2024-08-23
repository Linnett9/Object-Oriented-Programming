import javax.json.JsonObjectBuilder;

public class ComplicatedCentaur extends Creature {

    public ComplicatedCentaur() {
        super("Complicated Centaur", 36);
    }

    @Override
    public JsonObjectBuilder generateJSON() {
        // Just call the superclass method, no extra attributes to add
        return super.generateJSON();
    }
}
