import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Deer extends Animal implements LongJumpable {

    // Deer-specific attributes
    private boolean hasAntlers;
    private boolean lookingForPartner;

    // Constructor
    public Deer(String name, Game game) {
        super(name, game);
        this.hasAntlers = true; 
        this.lookingForPartner = true; 
    }
    public boolean hasAntlers() {
        return this.hasAntlers;
    }
    public boolean isLookingForPartner() {
        return this.lookingForPartner;
    }
    

    // Implementation of move for walking one square in any direction
    @Override
    public boolean move(int dstRow, int dstCol) {
        Square destinationSquare = getBoardSquare(dstRow, dstCol);
        int currentRow = this.getRow();
        int currentCol = this.getCol();

        // Check if the move is within one square distance in any direction
        if (Math.abs(dstRow - currentRow) <= 1 && Math.abs(dstCol - currentCol) <= 1) {
            // Check if the destination is not occupied
            if (!destinationSquare.isOccupied()) {
                setCurrentSquare(destinationSquare);
                return true;
            }
        }
        return false;
    }

    // Implement the jump method from Jumpable interface
    @Override
    public void jump(int fromRow, int fromCol, int toRow, int toCol) {

        jumpLong(fromRow, fromCol, toRow, toCol);
    }

    // Implement the jumpLong method from LongJumpable interface
    @Override
    public void jumpLong(int fromRow, int fromCol, int toRow, int toCol) {
        Square destinationSquare = getBoardSquare(toRow, toCol);
        int distanceRow = Math.abs(toRow - fromRow);
        int distanceCol = Math.abs(toCol - fromCol);

        // Check if the jump is up to three squares in any direction
        boolean isLongJump = (distanceRow <= 3 && distanceCol <= 3);

        if (isLongJump) {
            // Check if the destination square is occupied by a mythical creature
            if (destinationSquare.hasCreature()) {
                // Land in the creature's square and get attacked
                this.attacked();
            }
            // The deer can jump over any other animal or mythical creature
            setCurrentSquare(destinationSquare);
        }
    }

    // Helper method to get the Square object from the board
    private Square getBoardSquare(int row, int col) {
        Square[][] board = game.getBoard(); // Use the game reference to get the board
        if (row >= 0 && row < Game.ROW && col >= 0 && col < Game.COL) {
            return board[row][col];
        } else {
            // Handle invalid coordinates (e.g., throw an exception or return null)
            return null;
        }
    }
    // Override getDescription from Animal class to include deer-specific attributes
    @Override
    public String getDescription() {
        String description = super.getDescription();
        description += String.format("%nHas Antlers: %b%nLooking for Partner: %b",
            hasAntlers,
            lookingForPartner);
        return description;
    }
       // Implement the generateJSON method to serialize Deer-specific attributes
    @Override
    public JsonObjectBuilder generateJSON() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("name", getName());
        builder.add("health", getHealth());
        builder.add("row", getRow());
        builder.add("col", getCol());
        builder.add("hasAntlers", hasAntlers());
        builder.add("lookingForPartner", isLookingForPartner());
        // Add other Deer-specific details...
        return builder;
    }
}

