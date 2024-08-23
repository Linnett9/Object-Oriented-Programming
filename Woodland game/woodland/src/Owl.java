import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Owl extends Animal implements Flyable {

    // Owl-specific attributes
    private boolean hasWings;
    private boolean hasPrescriptionContactLenses;

    // Constructor
    public Owl(String name, Game game) {
        super(name, game);
        this.hasWings = true;
        this.hasPrescriptionContactLenses = true;
    }

    public boolean hasWings() {
        return hasWings;
    }

    public boolean hasPrescriptionContactLenses() {
        return hasPrescriptionContactLenses;
    }

    // Implementation of move for walking one square in any direction
    @Override
    public boolean move(int dstRow, int dstCol) {
        Square destinationSquare = getBoardSquare(dstRow, dstCol);
        if (destinationSquare != null && Math.abs(dstRow - this.getRow()) <= 1 && Math.abs(dstCol - this.getCol()) <= 1) {
            // Check if the destination is not occupied or only has a spell
            if (!destinationSquare.isOccupied() || destinationSquare.hasSpell()) {
                setCurrentSquare(destinationSquare);
                return true;
            }
        }
        return false; // Move was not allowed
    }

    // Implement the fly method from Flyable interface
    @Override
    public boolean fly(int oldRow, int oldCol, int newRow, int newCol) {
        Square destinationSquare = getBoardSquare(newRow, newCol);
        if (destinationSquare != null) {
            // If the destination square has a creature, land and get attacked
            if (destinationSquare.hasCreature()) {
                setCurrentSquare(destinationSquare);
                this.attacked();
            } else if (!destinationSquare.isOccupied()) {
                // If the destination square is free, land safely
                setCurrentSquare(destinationSquare);
            } else {
                // Can't land on a square occupied by another animal
                return false;
            }
            return true;
        }
        return false; // Cannot fly to an invalid location
    }

    // Helper method to get the Square object from the board
    private Square getBoardSquare(int row, int col) {
        Square[][] board = game.getBoard();
        if (row >= 0 && row < board.length && col >= 0 && col < board[row].length) {
            return board[row][col];
        } else {
            // Handle invalid coordinates (e.g., return null)
            return null;
        }
    }

    @Override
    public String getDescription() {
        // Including owl-specific attributes
        String description = super.getDescription();
        description += String.format("%nHas Wings: %b%nHas Prescription Contact Lenses: %b",
            hasWings,
            hasPrescriptionContactLenses);
        return description;
    }

    @Override
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("health", getHealth());
        builder.add("row", getRow());
        builder.add("col", getCol());
        builder.add("hasWings", hasWings());
        builder.add("hasPrescriptionContactLenses", hasPrescriptionContactLenses());
        // Add other Owl-specific details if needed...
        return builder;
    }
}
