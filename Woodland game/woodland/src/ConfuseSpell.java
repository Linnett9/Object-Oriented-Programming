import java.util.List;
public class ConfuseSpell extends Spell {
    private Game game; // Hold a reference to the Game object

    public ConfuseSpell(String name, int effect, Game game) {
        super(name, Type.CONFUSE, effect, "The confuse spell allows the animal to confuse a mythical creature on a square adjacent to the animal  but not the sqaure the animal is occupying. The mythical creature will not attack any animal for this turn.");
        this.game = game; // Store the game reference
    }

    @Override
    public void castSpell(Animal animal, Square square) {
        List<Square> adjacentSquares = game.getAdjacentSquares(square);
        for (Square adjSquare : adjacentSquares) {
            Creature creature = adjSquare.getCreature();
            if (creature != null && !creature.isConfused()) {
                creature.confuse();
            }
        }
    }
}
