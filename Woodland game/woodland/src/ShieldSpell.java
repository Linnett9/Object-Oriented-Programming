public class ShieldSpell extends Spell {
    public ShieldSpell(String name, int effect) {
        super(name, Type.SHIELD, effect, "Creates a magical shield that prevents health loss for one turn.");
    }

    @Override
    public void castSpell(Animal animal, Square square) {
        // Logic to set a shield flag on the animal
    }
}
