package dillonbill.connect6.game;


import dillonbill.connect6.net.*;
import java.util.Scanner;

public class Connect6Game {

	private Board board;
	private MiniMax minimax;
	// getter and setter methods

	public enum Stone {BLACK(1), WHITE (0);

		private int value;
		private Stone (int i) { value = i; }
		int getValue() { return value; }
		Stone otherPlayer() {
			if (value == 1) return WHITE;
			return BLACK;
		}
	};



	public Connect6Game (Board b, Network blackAI, Network whiteAI) {
		board = b;
		minimax = new MiniMax(new BoardNN(board,blackAI),new BoardNN(board,blackAI));
	}

	public Connect6Game (Board b) {
		board = b;
		minimax = new MiniMax(new BoardNN(board));
	}

	public Connect6Game (Board b, Network ai) {
		board = b;
		minimax = new MiniMax(new BoardNN(board,ai));
	}


	public void displayBoard() {
		board.displayBoard();
	}

	// check if a substring is in str
	public static boolean contains(String str, String substring) {
		return str.indexOf(substring) >= 0;
	}

	// check if last play is a winning play
	public boolean isWinningPlay() {
		char sym = board.getLastMove();
		int lastRow = board.getLastRow();
		int lastCol = board.getLastCol();
		// winning streak with the last play symbol
		String streak = String.format("%c%c%c%c%c%c", sym, sym, sym, sym, sym, sym);

		// check if streak is in row, col, diagonal or backslash diagonal
		return contains(board.horizontal(lastRow), streak) || 
				contains(board.vertical(lastCol), streak) || 
				contains(board.slashDiagonal(lastCol, lastRow), streak) || 
				contains(board.backslashDiagonal(lastCol, lastRow), streak);
	}

	// prompt user for column
	public void chooseMove(int player, Scanner input) {
		int col;
		int row;

		do {
			col = input.nextInt();
			row = input.nextInt();
			Move move = new Move(col, row, player);

			if (!(0 <= move.getCol() && move.getCol() < board.getWidth())) {
				System.out.println("Column must be between 0 and " + (board.getWidth() - 1));
				continue;
			}

			if (!(0 <= move.getRow() && move.getRow() < board.getHeight())) {
				System.out.println("Column must be between 0 and " + (board.getHeight() - 1));
				continue;
			}

			if(board.getMove(move.getRow(), move.getCol()) == '.') {
				board.applyMove(move);
				board.setLastRow(move.getRow());
				board.setLastCol(move.getCol());
				return;
			}

			// ask for new input if column is full
			System.out.println("That space is full.");
		} while (true);
	}

	public void doMinimaxMove(Stone player, int turnNumber) {
		Move minimaxMove = new Move();
		minimaxMove = minimax.minimax(board, player, 1, turnNumber);
		board.applyMove(minimaxMove);
		board.setLastRow(minimaxMove.getRow());
		board.setLastCol(minimaxMove.getCol());
	}

	public void applyFirstMove(Stone player) {
		int middle = (board.getHeight()-1) / 2;
		Move move = new Move(middle, middle, player);
		board.applyMove(move);
		board.setLastRow(middle);
		board.setLastCol(middle);
	}
}