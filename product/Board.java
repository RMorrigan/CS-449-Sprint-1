package sprint2.product;

public class Board {
	private char[][] grid;
	private char currentPlayer;
	private int boardSize;
	private boolean gameMode;

	// Default constructor initializes a 3x3 board in simple game mode with blue player starting
	public Board() {
		this(3, true);
	}

	// Constructor to initialize a board with a specified size in simple game mode with blue player starting
	public Board(int size) {
		this(size, true);
	}

	// Constructor to initialize a board with specified size and game mode with blue player starting
	public Board(int size, boolean mode) {
		this.boardSize = size;
		this.gameMode = mode;
		this.currentPlayer = 'B';
		this.grid = new char[size][size];
	}
	
	public void setBoardSize(int size) {
        if(size > 2 && size <= 20)
        {
        	this.boardSize = size;
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
            	grid[i][j] = 0;
            }
        }
    }

    public char[][] getBoard() {
        return grid;
    }

    /*
    public void displayBoard() {
    	for (int i = 0; i < boardSize; i++) {
    		for (int j = 0; j < boardSize; j++) {
    			System.out.println("-------");
    			System.out.print("|"+ grid[i][j]);
    			System.out.println("|");
    		}
    		System.out.println("-------");
		}
	}
    */
    
    public char getCell(int row, int column) {
        if (row >= 0 && row < boardSize && column >= 0 && column < boardSize)
            return grid[row][column];
        else
            return 'X';
    }
    
    public void setGameMode(boolean mode) {
        this.gameMode = mode; // true for simple game, false for general game
    }

    public boolean getGameMode() {
        return gameMode;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getSymbol(int row, int column) {
        return grid[row][column];
    }
    
    public boolean makeMove(int row, int column, char symbol) {
        boolean isValid = isValid(row, column);
        if (isValid) {
        	grid[row][column] = symbol;
            changeTurn();
        }
        return isValid;
    }

    private boolean isValid(int row, int column) {
        return row >= 0 && row < boardSize && column >= 0 && column < boardSize && grid[row][column] == 0;
    }

    private void changeTurn() {
        currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
    }
}