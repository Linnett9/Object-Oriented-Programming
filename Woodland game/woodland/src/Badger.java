import javax.json.JsonObjectBuilder;


public class Badger extends Animal {

    // Badger-specific attributes
    private boolean hasBlackAndWhiteFace;
    private boolean oftenMistakenForPanda;
    private String tshirtSlogan;

    // Constructor
    public Badger(String name, Game game) {
        super(name, game);
        this.hasBlackAndWhiteFace = true;
        this.oftenMistakenForPanda = true;
        this.tshirtSlogan = "I am not a panda";
    }

    // Badger-specific methods
    public boolean hasBlackAndWhiteFace() {
        return hasBlackAndWhiteFace;
    }
    
    public boolean isOftenMistakenForPanda() {
        return oftenMistakenForPanda;
    }

    public String getTshirtSlogan() {
        return tshirtSlogan;
    }

    // Override getDescription from Animal class to include badger-specific attributes
    @Override
    public String getDescription() {
        return "The badger has a distinctive black and white face and is often mistaken for a panda.";
    }

    // Implement the generateJSON method to serialize Badger-specific attributes
    @Override
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = super.generateJSON();
        builder.add("hasBlackAndWhiteFace", hasBlackAndWhiteFace());
        builder.add("oftenMistakenForPanda", isOftenMistakenForPanda());
        builder.add("tshirtSlogan", getTshirtSlogan());
        // You can add additional Badger-specific details here...

        return builder;
    }

    // Implementation of move for Badger
    @Override
    public boolean move(int dstRow, int dstCol) {
        // Implementation specific to Badger...
        return true; // or false based on some condition
    }
}
