import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardInitializer {
    // Constants
    public static final int ROWS = 20;
    public static final int COLS = 20;

    // Attributes
    private Square[][] board;
    private Random random;
    private Game game; 

    // Constructor
    public BoardInitializer(Square[][] board, Game game, Random random) {
        this.board = board;
        this.game = game; 
        this.random = random;
        // Clear the board before initialization
        clearBoard();
    }

    // Helper method to clear the board
    private void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Square(i, j);
            }
        }
    }

    // Initialize animals on the bottom row of the board
    public void initializeAnimals() {
        List<Animal> animals = game.getAnimals(); 
        int bottomRow = ROWS - 1;
        for (Animal animal : animals) {
            int col;
            do {
                col = random.nextInt(COLS);
            } while (board[bottomRow][col].isOccupied());

            board[bottomRow][col].setAnimal(animal);
            animal.setCurrentSquare(board[bottomRow][col]);
        }
    }

// Initialize creatures on the board, not on the top or bottom row
public void initializeCreatures() {
    // Create a list of one of each creature type
    List<Creature> creaturesImmutable = List.of(
        new UnderAppreciatedUnicorn(),
        new ComplicatedCentaur(),
        new DeceptiveDragon(),
        new PrecociousPhoenix(),
        new SassySphinx()
    );
    
    // Create a mutable list from the immutable list
    List<Creature> creatures = new ArrayList<>(creaturesImmutable);

    // Shuffle the list to ensure random placement
    Collections.shuffle(creatures, random);

    // Iterate over the shuffled list and place each creature
    for (Creature creature : creatures) {
        int row, col;
        do {
            row = random.nextInt(ROWS - 2) + 1; // +1 to exclude the top row, and -2 to exclude the bottom
            col = random.nextInt(COLS);
        } while (board[row][col].isOccupied());

        board[row][col].setCreature(creature);
        game.setCreatures(creatures);
    }
}
// Initialize spells on the board, not on the top or bottom row
public void initializeSpells() {
    // Define the spells to be added to the board
    List<Spell> spells = Arrays.asList(
        new DetectSpell("Detect Spell", 0, game), // Removed game argument, assuming it's not needed
        new HealSpell("Heal Spell", 10),
        new ShieldSpell("Shield Spell", 0),
        new ConfuseSpell("Confuse Spell", 0, game),
        new CharmSpell("Charm Spell", 3, game)
    );

    // Shuffle the list to ensure random placement
    Collections.shuffle(spells, random);

    // Place each spell on the board
    for (Spell spell : spells) {
        int row, col;
        do {
            row = random.nextInt(ROWS - 2) + 1; // Exclude top and bottom row
            col = random.nextInt(COLS);
        } while (board[row][col].isOccupied() || board[row][col].hasSpell());

        board[row][col].setSpell(spell);
    }
}

    public Square[][] getBoard(){
        return board;
    }
}