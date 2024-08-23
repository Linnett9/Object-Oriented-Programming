import java.util.List;

public class CharmSpell extends Spell {
    private Game game; // Hold a reference to the Game object

    public CharmSpell(String name, int effect, Game game) {
        super(name, Type.CHARM, effect, "The charm spell allows the animal to charm a mythical creature on a square adjacent to the animal but not the square the animal is occupying. The mythical creature will not attack the charming animal for the next three turns");
        this.game = game; // Store the game reference
    }

    @Override
    public void castSpell(Animal animal, Square square) {
        List<Square> adjacentSquares = game.getAdjacentSquares(square);
        for (Square adjSquare : adjacentSquares) {
            Creature creature = adjSquare.getCreature();
            if (creature != null && !creature.isCharmedBy(animal)) {
                // Assuming there is a method to charm the creature for 3 turns
                creature.charm(animal, this.getEffect());
            }
        }
    }
}
