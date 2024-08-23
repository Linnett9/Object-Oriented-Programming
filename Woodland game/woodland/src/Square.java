// Square.java
public class Square {
    private int row;
    private int col;
    private boolean visible;
    private boolean occupied;
    private Animal animal;
    private Creature creature;
    private Spell spell;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.visible = false;
        this.occupied = false;
        this.animal = null;
        this.creature = null;
        this.spell = null;
    }

    // Check if the square is visible
    public boolean isVisible() {
        return visible;
    }

    // Get row of the square
    public int getRow() {
        return row;
    }

    // Get column of the square
    public int getCol() {
        return col;
    }
    // Set the visibility of the square
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Check if the square has a visible creature
    public boolean hasVisibleCreature() {
        return hasCreature() && isVisible();
    }
    // Check if the square is occupied
    public boolean isOccupied() {
        return occupied;
    }
    // Check if the square has an animal 
    public boolean hasAnimal() {
        return this.animal != null;
    }

    // Check if there is a creature on the square
    public boolean hasCreature() {
        return creature != null;
    }

    // Check if there is a spell on the square
    public boolean hasSpell() {
        return spell != null;
    }

    // Set an animal on the square
    public void setAnimal(Animal animal) {
        this.animal = animal;
        this.occupied = (animal != null);
    }

    // Set a creature on the square
    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    // Get the animal on the square
    public Animal getAnimal() {
        return animal;
    }

    // Get the creature on the square
    public Creature getCreature() {
        return creature;
    }

    // Set a spell on the square
    public void setSpell(Spell spell) {
        this.spell = spell;
    }
        // Get the spell on the square
        public Spell getSpell() {
            return spell;
        }
}
