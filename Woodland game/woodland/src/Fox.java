import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Fox extends Animal implements LongJumpable {

    // Fox-specific attributes
    private boolean hasBushyTail;
    private boolean enjoysButterflies;

    // Constructor
    public Fox(String name, Game game) {
        super(name, game);
        this.hasBushyTail = true;
        this.enjoysButterflies = true;
    }

    // Fox-specific methods
    public boolean hasBushyTail() {
        return hasBushyTail;
    }
    
    public boolean enjoysButterflies() {
        return enjoysButterflies;
    }

    // Implementation of move for walking one square horizontally or vertically
    @Override
    public boolean move(int dstRow, int dstCol) {
        Square destinationSquare = getBoardSquare(dstRow, dstCol);
        if (destinationSquare == null) {
            return false; // Move is not possible if the square doesn't exist
        }

        int currentRow = this.getRow();
        int currentCol = this.getCol();

        // Check if the move is horizontal or vertical and within one square distance
        boolean isHorizontalMove = dstRow == currentRow && Math.abs(dstCol - currentCol) == 1;
        boolean isVerticalMove = dstCol == currentCol && Math.abs(dstRow - currentRow) == 1;

        if (isHorizontalMove || isVerticalMove) {
            // Check if the destination is not occupied or only has a spell
            if (!destinationSquare.isOccupied() || destinationSquare.hasSpell()) {
                setCurrentSquare(destinationSquare);
                return true;
            }
        }
        return false; // Move was not allowed
    }

    // Implement the jump method from Jumpable interface
    @Override
    public void jump(int fromRow, int fromCol, int toRow, int toCol) {
        // Delegating the jump method to jumpLong, but you can implement different logic if needed
        jumpLong(fromRow, fromCol, toRow, toCol);
    }

    // Implement the jumpLong method from LongJumpable interface
    @Override
    public void jumpLong(int fromRow, int fromCol, int toRow, int toCol) {
        // Implement the logic for a long jump according to the game rules
        // Placeholder for actual implementation
    }

    // Override getDescription from Animal class to include fox-specific attributes
    @Override
    public String getDescription() {
        String description = super.getDescription();
        description += String.format("%nHas Bushy Tail: %b%nEnjoys Butterflies: %b",
            hasBushyTail,
            enjoysButterflies);
        return description;
    }

    // Helper method to get the Square object from the board
    private Square getBoardSquare(int row, int col) {
        Square[][] board = game.getBoard();
        if (row >= 0 && row < board.length && col >= 0 && col < board[row].length) {
            return board[row][col];
        } else {
            return null; // Handle invalid coordinates appropriately
        }
    }

    // Implement the generateJSON method to serialize Fox-specific attributes
    @Override
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("health", getHealth());
        builder.add("row", getRow());
        builder.add("col", getCol());
        builder.add("hasBushyTail", hasBushyTail);
        builder.add("enjoysButterflies", enjoysButterflies);
        // Add other Fox-specific details...
        return builder;
    }
}
