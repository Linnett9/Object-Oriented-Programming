import java.util.List;
public class DetectSpell extends Spell {

    private Game game; // Store a reference to the Game object

    public DetectSpell(String name, int effect, Game game) {
        super(name, Type.DETECT, effect, "The detect spell allows the animal to detect the mythical creatures on the adjacent squares.");
        this.game = game; // Initialize the Game reference
    }

    @Override
    public void castSpell(Animal animal, Square square) {
        // Use the Game reference to call getAdjacentSquares
        List<Square> adjacentSquares = game.getAdjacentSquares(square);

        for (Square adjSquare : adjacentSquares) {
            // If there's a creature, make it visible
            if (adjSquare.hasCreature()) {
                adjSquare.setVisible(true);
            }
        }
    }
}