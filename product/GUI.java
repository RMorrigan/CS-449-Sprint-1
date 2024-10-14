package sprint2.product;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class GUI extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final int CELL_SIZE = 40;
    public static final int GRID_WIDTH = 2;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    
    public final int CELL_PADDING = CELL_SIZE / 6;
    public final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;
    
    private int CANVAS_WIDTH;
    private int CANVAS_HEIGHT;
    
    private GameBoardCanvas gameBoardCanvas;
    private JTextField currentTurnText;
    private JSpinner spinBoardSize; // adjusts board size
    
    private JRadioButton rdbtnBlueS;
    private JRadioButton rdbtnBlueO;
    private ButtonGroup B; // For Blue Player’s radio buttons
    
    private JRadioButton rdbtnRedS;
    private JRadioButton rdbtnRedO;
    private ButtonGroup R; // For Red Player’s radio buttons
    
    private ButtonGroup G; // For game mode radio buttons
    
    private boolean gameMode;
 
    private char blueSymbol;
    private char redSymbol;
    
    private Board board;
    private int boardSize;
    
    public GUI(Board board) {
    	getContentPane().setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        this.board = board;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("SOS GAME");
        setVisible(true);
    }
   
    private void setContentPane() {
		gameBoardCanvas = new GameBoardCanvas();
    	gameBoardCanvas.setBorder(BorderFactory.createLineBorder(Color.BLACK, GRID_WIDTH));
    	updateCanvasSize();
    	
    	JPanel topPanel = new JPanel();
    	topPanel.setBackground(Color.WHITE);
    	JPanel rightPanel = new JPanel();
    	rightPanel.setBackground(Color.WHITE);
    	JPanel leftPanel = new JPanel();
    	leftPanel.setBackground(Color.WHITE);
    	JPanel bottomPanel = new JPanel();
    	bottomPanel.setBackground(Color.WHITE);
		
    	//top panel attributes
    	JLabel lblTitle = new JLabel("SOS");
    	lblTitle.setBackground(Color.WHITE);
    	JLabel lblBoardInput = new JLabel("Board Size: ");
    	lblBoardInput.setBackground(Color.WHITE);
    	
    	//BoardSize init:3 min:3 max:20 step:1
        spinBoardSize = new JSpinner(new SpinnerNumberModel(3, 3, 20, 1));
        spinBoardSize.addChangeListener(e -> {
        	int sizeSelected = (int) spinBoardSize.getValue();
        	setBoardSize(sizeSelected); // update board size
            gameBoardCanvas.repaint();
        });
    	
    	// simple or general game mode option
    	JRadioButton rdbtnSimpleGame = new JRadioButton("Simple Game", true);
    	rdbtnSimpleGame.setBackground(Color.WHITE);
    	JRadioButton rdbtnGeneralGame = new JRadioButton("General Game");
    	rdbtnGeneralGame.setBackground(Color.WHITE);
    	rdbtnSimpleGame.addActionListener(e -> {
			gameMode = true;
			});
		rdbtnGeneralGame.addActionListener(e -> {
			gameMode = false;
			});
		
		//ButtonGroup for game mode
		G = new ButtonGroup();
		G.add(rdbtnSimpleGame);
		G.add(rdbtnGeneralGame);
		
		topPanel.add(rdbtnSimpleGame);
		topPanel.add(rdbtnGeneralGame);
		topPanel.add(lblTitle);
		topPanel.add(lblBoardInput);
		topPanel.add(spinBoardSize);
        
		//Right Panel Attributes - Red Player
		//RED PLAYER'S LABEL AND BUTTONS
		JLabel lblRed = new JLabel("Red Player");
		rdbtnRedS = new JRadioButton("S");
		rdbtnRedS.setActionCommand("S");
		rdbtnRedS.setBackground(Color.WHITE);
		
		rdbtnRedO = new JRadioButton("O");
		rdbtnRedO.setActionCommand("O");
		rdbtnRedO.setBackground(Color.WHITE);
		
		//ButtonGroup for Red
		R = new ButtonGroup();
		R.add(rdbtnRedS);
		R.add(rdbtnRedO);
		
		rdbtnRedS.addActionListener(e -> {
			redSymbol = 'S';
			});
		rdbtnRedO.addActionListener(e -> {
			redSymbol = 'O';
			});
		
		rightPanel.add(lblRed);
		rightPanel.add(rdbtnRedS);
		rightPanel.add(rdbtnRedO);
			    
		//Left Panel Attributes - Blue Player
		//Blue Player's label and buttons
		JLabel lblBlue = new JLabel("Blue Player");
		rdbtnBlueS = new JRadioButton("S");
		rdbtnBlueS.setActionCommand("S");
		rdbtnBlueS.setBackground(Color.WHITE);
		rdbtnBlueO = new JRadioButton("O");
		rdbtnBlueO.setActionCommand("O");
		rdbtnBlueO.setBackground(Color.WHITE);
		
		//ButtonGroup for Blue
		B = new ButtonGroup();
		B.add(rdbtnBlueS);
		B.add(rdbtnBlueO);
		
		rdbtnBlueS.addActionListener(e -> {
			blueSymbol = 'S';
			});
		rdbtnBlueO.addActionListener(e -> {
			blueSymbol = 'O';
			});
		
		leftPanel.add(lblBlue);
		leftPanel.add(rdbtnBlueS);
		leftPanel.add(rdbtnBlueO);
		
		//Bottom Panel attributes
		currentTurnText = new JTextField("Current Turn: Blue");
        currentTurnText.setBorder(null);
        currentTurnText.setBackground(Color.WHITE);
        currentTurnText.setColumns(12);
        
        bottomPanel.add(currentTurnText);
        
        //set up contentPane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.EAST);
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(100, 100));
        bottomPanel.setPreferredSize(new Dimension(100, 100));
        leftPanel.setPreferredSize(new Dimension(100, 100));
        rightPanel.setPreferredSize(new Dimension(100, 100));
		
	}    
    
    private void updateCanvasSize() {
    	CANVAS_WIDTH = CELL_SIZE * board.getBoardSize();
    	CANVAS_HEIGHT = CELL_SIZE * board.getBoardSize();
    	gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    	pack();
    }
    
    public Board getBoard() {
        return board;
    }

    class GameBoardCanvas extends JPanel {
        private char currentPlayerSymbol;
        private char currentPlayerTurn;
        
        GameBoardCanvas() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int rowSelected = e.getY() / CELL_SIZE;
                    int columnSelected = e.getX() / CELL_SIZE;
                    currentPlayerTurn = board.getCurrentPlayer();
                    if (isValidMove(rowSelected, columnSelected)) {
                        updateCurrentTurnLabel();
                        updateCurrentPlayerSymbol(rowSelected, columnSelected);
                        board.makeMove(rowSelected, columnSelected, currentPlayerSymbol);
                        repaint();
                    }
                }
            });
        }

        private boolean isValidMove(int row, int column) {
            return row >= 0 && row < board.getBoardSize() && column >= 0 && column < board.getBoardSize() && board.getCell(row, column) == 0;
        }

        private void updateCurrentTurnLabel() {
            currentTurnText.setText("Current Turn: " + (currentPlayerTurn == 'B' ? "Red" : "Blue"));
        }

        private void updateCurrentPlayerSymbol(int row, int column) {
            ButtonModel selection = currentPlayerTurn == 'B' ? B.getSelection() : R.getSelection();
            currentPlayerSymbol = (selection != null && selection.getActionCommand() != null) ? selection.getActionCommand().charAt(0) : ' ';
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
        }
        
        private void drawGridLines(Graphics g) {
            g.setColor(Color.BLACK);
            for (int row = 1; row < board.getBoardSize(); row++) {
                int y = CELL_SIZE * row - GRID_WIDTH_HALF;
                g.fillRect(0, y, CANVAS_WIDTH, GRID_WIDTH);
            }
            for (int column = 1; column < board.getBoardSize(); column++) {
                int x = CELL_SIZE * column - GRID_WIDTH_HALF;
                g.fillRect(x, 0, GRID_WIDTH, CANVAS_HEIGHT);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int column = 0; column < board.getBoardSize(); column++) {
                    int x = column * CELL_SIZE + CELL_PADDING;
                    int y = row * CELL_SIZE + CELL_PADDING;
                    char cellValue = board.getCell(row, column);
                    drawSymbol(g2d, cellValue, x, y);
                }
            }
        }
    }
    
    private void drawSymbol(Graphics2D g2d, char symbol, int x, int y) {
        String letter = String.valueOf(symbol);
        Font font = new Font("Arial", Font.BOLD, SYMBOL_SIZE);
        FontMetrics fm = g2d.getFontMetrics(font);
        int textWidth = fm.stringWidth(letter);
        int textHeight = fm.getHeight();
        int xOffset = (CELL_SIZE - textWidth) / 2;
        int yOffset = (CELL_SIZE - textHeight) / 2 + fm.getAscent();
        g2d.setFont(font);
        g2d.drawString(letter, x + xOffset, y + yOffset);
    }

    public void setBoardSize(int size) {
    	boardSize = size;
    	board = new Board(boardSize, true);
    	updateCanvasSize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI(new Board(3, true));
            }
        });
    }
}