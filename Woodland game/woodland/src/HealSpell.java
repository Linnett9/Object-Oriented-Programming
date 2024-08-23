public class HealSpell extends Spell {
    public HealSpell(String name, int effect) {
        super(name, Type.HEAL, effect, "The heal spell allows the animal to heal 10 life points.");
    }

    @Override
    public void castSpell(Animal animal, Square square) {
        animal.setHealth(Math.min(animal.getHealth() + this.getEffect(), 100));
    }
}