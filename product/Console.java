package sprint2.product;

public class Console {
	private Board board;

	public Console(Board board) {
		this.board = board;
	}

	public void displayBoard() {
		char symbol;
		for (int i = 0; i < board.getBoardSize(); i++) {
			for (int j = 0; j < board.getBoardSize(); j++) {
				symbol = board.getCell(i, j);
				System.out.println("-------");
				System.out.print("|"+ symbol);
				System.out.println("|");
			}
			System.out.println("-------");
		}
	}

	public static void main(String[] args) {
		new Console(new Board()).displayBoard();; 

	}
}
