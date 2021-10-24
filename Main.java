import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
	  Scanner input = new Scanner(System.in);
      int height = 19; 
      int width = 19; 
      char player = 'R';

      Connect6Game board = new Connect6Game(width, height);
      
      board.setPlayer(player);

      // instructions
      System.out.println("Use 0-" + (width - 1) + " to choose a column");
      // display board
      board.displayBoard();

      // change player turn
      for (int moves = height * width; moves > 0; moves--) {
        // symbol for current player
        char symbol = board.getPlayer();
        
        // ask user to choose column
        board.chooseAndDrop(symbol, input);

        // display the board
        board.displayBoard();

        // check if player won
        if (board.isWinningPlay()) {
          System.out.println("\nPlayer " + symbol + " wins!");
          return;
        }
        // switch players
        board.switchPlayers();
      }
    
      System.out.println("Game over. No winner.");
  }
}