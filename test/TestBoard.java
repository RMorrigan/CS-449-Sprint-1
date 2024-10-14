package sprint2.test;

import sprint2.product.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBoard {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(); // Initializes a 3x3 board by default
        board.initializeBoard(); // Ensures the board is initialized
    }
    
    @Test
	public void testSetAndGetBoardSize() {
		int size = 8;
		board.setBoardSize(size);
		assertEquals(size, board.getBoardSize());
		
		size = 1900;
		board.setBoardSize(size);
        boolean result = (size == board.getBoardSize()); // Cell is already occupied
        assertFalse(result);
	}
    
    @Test
    public void testSetAndGetGameMode() {
        // Default game mode is simple (true)
        assertTrue(board.getGameMode());

        // Set game mode to general (false)
        board.setGameMode(false);
        assertFalse(board.getGameMode());

        // Set game mode back to simple (true)
        board.setGameMode(true);
        assertTrue(board.getGameMode());
    }

    @Test
	public void testDefaultConstructor() {
		assertEquals(3, board.getBoardSize());
		assertTrue(board.getGameMode());
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
	public void testConstructorWithSize() {
		int size = 9;
		board = new Board(size);
		assertEquals(size, board.getBoardSize());
		assertTrue(board.getGameMode());
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
	public void testConstructorWithSizeAndMode() {
		int size = 15;
		boolean mode = false;
		board = new Board(size, mode);
		assertEquals(size, board.getBoardSize());
		assertEquals(mode, board.getGameMode());
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
	public void testMakeMove() {
		board.makeMove(0, 0, 'S');
		assertEquals('S', board.getCell(0, 0));
		assertEquals('R', board.getCurrentPlayer());
		assertTrue(board.makeMove(1, 1, 'O'));
		assertEquals('O', board.getCell(1, 1));
		assertEquals('B', board.getCurrentPlayer());
	}
	
	@Test
    public void testMakeMoveOnInvalidCell() {
        // Set up the board
        int size = 3;
        board.setBoardSize(size);
        board.initializeBoard();

        // Make a move on an invalid cell (out of bounds)
        boolean result = board.makeMove(-1, 0, 'S'); // Row is out of bounds
        assertFalse(result);

        // Make a move on an invalid cell (already occupied)
        board.makeMove(0, 0, 'S');
        result = board.makeMove(0, 0, 'O'); // Cell is already occupied
        assertFalse(result);
    }
	
	//CHATGPT GENERATED TESTS
    @Test
    void testMakeMove_ValidMove() {
        // Arrange
        int row = 0;
        int column = 0;
        char symbol = 'B'; // Blue player's symbol

        // Act
        boolean result = board.makeMove(row, column, symbol);

        // Assert
        assertTrue(result, "The move should be valid");
        assertEquals(symbol, board.getCell(row, column), "The cell should contain the symbol 'B'");
        assertEquals('R', board.getCurrentPlayer(), "The current player should now be 'R'");
    }

    @Test
    void testMakeMove_InvalidMove() {
        // Arrange
        int row = 0;
        int column = 0;
        char symbol = 'B';
        board.makeMove(row, column, symbol); // Make the first move

        // Act
        boolean result = board.makeMove(row, column, symbol); // Try to make an invalid move on the same cell

        // Assert
        assertFalse(result, "The move should be invalid because the cell is already occupied");
        assertEquals(symbol, board.getCell(row, column), "The cell should still contain the symbol 'B'");
    }
}
