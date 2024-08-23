import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Rabbit extends Animal implements ShortJumpable {

    // Rabbit-specific attributes
    private boolean hasFluffyEarsAndTail;
    private boolean likesGrass;

    // Constructor
    public Rabbit(String name, Game game) {
        super(name, game);
        this.hasFluffyEarsAndTail = true;
        this.likesGrass = true;
    }

    public boolean hasFluffyEarsAndTail() {
        return hasFluffyEarsAndTail;
    }
    
    public boolean likesGrass() {
        return likesGrass;
    }

    // Implementation of move for walking one square in any direction
    @Override
    public boolean move(int dstRow, int dstCol) {
        Square destinationSquare = getBoardSquare(dstRow, dstCol);
        int currentRow = this.getRow();
        int currentCol = this.getCol();

        // Check if the move is within one square distance
        if (Math.abs(dstRow - currentRow) <= 1 && Math.abs(dstCol - currentCol) <= 1) {
           // Check if the destination is not occupied or only has a spell
           if (destinationSquare != null && (!destinationSquare.isOccupied() || destinationSquare.hasSpell())) {
                setCurrentSquare(destinationSquare);
                return true;
            }
        }
        return false;
    }

    // Implement the jump method from Jumpable interface
    @Override
    public void jump(int fromRow, int fromCol, int toRow, int toCol) {
        // Logic for jumping two squares in any direction
        // This should be implemented according to the game rules for jumping
    }

    // Implement the jumpShort method from ShortJumpable interface
    @Override
    public void jumpShort(int fromRow, int fromCol, int toRow, int toCol) {
        // Logic for jumping one square in any direction
        // This should be implemented according to the game rules for short jumping
    }

    private Square getBoardSquare(int row, int col) {
        Square[][] board = game.getBoard();
        if (row >= 0 && row < board.length && col >= 0 && col < board[row].length) {
            return board[row][col];
        } else {
            // Handle invalid coordinates (e.g., throw an exception or return null)
            return null;
        }
    }
    // Implement the generateJSON method to serialize Rabbit-specific attributes
    @Override
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("health", getHealth());
        builder.add("row", getRow());
        builder.add("col", getCol());
        builder.add("hasFluffyEarsAndTail", hasFluffyEarsAndTail());
        builder.add("likesGrass", likesGrass());
        // Add other Rabbit-specific details...
        return builder;
    }
}