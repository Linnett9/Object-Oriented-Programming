import java.util.ArrayList;
import java.util.List;

public class Game {
    // Constants
    public static final int ROW = 20;
    public static final int COL = 20;
    public static final int MOVE_SUCCESS = 0;
    public static final int MOVE_INTERRUPTED = 1;
    public static final int MOVE_INVALID = 2;
    public static final int SPELL_SUCCESS = 3;
    public static final int SPELL_INVALID = 4;
    public static final int GAME_WIN = 5;
    public static final int GAME_LOST = 6;

    // Attributes
    private Square[][] board;
    private int turn;
    private List<Animal> animals;
    private boolean isGameOver;
    private List<Creature> creatures;
    private int lastOutcome;

    // Constructor
    public Game() {
        this.board = new Square[ROW][COL];
        this.animals = new ArrayList<>();
        this.isGameOver = false;
        this.turn = 0;
        this.creatures = new ArrayList<>();
        this.lastOutcome = MOVE_SUCCESS;

        // Initialize the board with empty squares
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = new Square(i, j);
            }
        }

    // Initialize animals in their turn order with the required name and reference to `this` game
    this.animals.add(new Rabbit("Rabbit", this));
    this.animals.add(new Fox("Fox", this));
    this.animals.add(new Deer("Deer", this));
    this.animals.add(new Owl("Owl", this));
    this.animals.add(new Badger("Badger", this));
}
public List<Animal> getAnimals() {
    return animals;
}

    // Get the game board
    public Square[][] getBoard() {
        return board;
    }
    public int getLastOutcome() {
        return lastOutcome;
        
    }

    // Move an animal on the board and handle the pickup of spells and detection of creatures
    public void moveAnimal(Animal animal, int dstRow, int dstCol) {
        if (animal.move(dstRow, dstCol)) {
            Square destinationSquare = board[dstRow][dstCol];
            if (destinationSquare.hasSpell()) {
                animal.pickUpSpell(destinationSquare.getSpell());
            }
            animal.detectCreature(); // Assuming this method will check for adjacent creatures
            this.lastOutcome = MOVE_SUCCESS;
        } else {
            this.lastOutcome = MOVE_INVALID;
        }
        }

    // Attack an animal with a creature
    public void attackAnimal(Animal animal) {
        if (animal.getCurrentSquare().hasCreature()) {
            animal.attacked(); // This now calls attacked without parameters
            if (animal.getHealth() <= 0) {
                isGameOver = true;
            }
        }
    }

    // Use a spell by an animal
    public void useSpell(Animal animal, Spell spell) {
        animal.update(spell);

    }

    // Check if the game is over
    public boolean isGameOver() {
        return isGameOver;
    }

    // Process the end of a turn for an animal
    public void endTurn() {
        attackAnimal(animals.get(turn));
        turn = (turn + 1) % animals.size(); // Proceed to the next animal's turn
        // Check if all animals reached the top row or any animal's health is 0
        checkEndConditions();
    }

    // Check the conditions to end the game
    private void checkEndConditions() {
        boolean allAnimalsAtTop = true;
        for (Animal animal : animals) {
            if (animal.getHealth() <= 0) {
                isGameOver = true;
                return;
            }
            if (animal.getRow() != 0) {
                allAnimalsAtTop = false;
            }
        }
        isGameOver = allAnimalsAtTop;
    }
    public int getTurn() {
        return this.turn;
    }
    
        // Method to get adjacent squares of a given square
        public List<Square> getAdjacentSquares(Square square) {
            List<Square> adjacentSquares = new ArrayList<>();
            int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};
    
            for (int i = 0; i < rowOffsets.length; i++) {
                int newRow = square.getRow() + rowOffsets[i];
                int newCol = square.getCol() + colOffsets[i];
    
                // Check if the new position is valid and within the board boundaries
                if (newRow >= 0 && newRow < ROW && newCol >= 0 && newCol < COL) {
                    adjacentSquares.add(board[newRow][newCol]);
                }
            }
    
            return adjacentSquares;
        }
        public List<Creature> getCreatures() {
            return creatures;
        }
        
        // Add a method in the Game class to set the list of creatures
        public void setCreatures(List<Creature> creatures) {
            this.creatures = creatures;
        }
        public Animal getCurrentAnimal() {
            return animals.get(turn);
        }
        public Animal getNextAnimal() {
            return animals.get((turn + 1) % animals.size());
        }
    
}
