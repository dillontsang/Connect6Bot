import java.util.Arrays;
import java.util.Scanner;

public class Connect6Game {

  private char player;
  // dimensions for board
  private int width, height;
  // grid for board
  private char[][] grid;
  // store last move made
  private int lastCol = -1, lastTop = -1;

  public Connect6Game(int w, int h) {
    width = w;
    height = h;
    grid = new char[h][w];

    // initialize grid with periods
    for (int i = 0; i < h; i++) {
      Arrays.fill(grid[i] = new char[w], '.');
    }
  }
  
  // getter and setter methods
  public char getPlayer() {
	  return player;
  }
  
  public void setPlayer(char playerInput) {
	  player = playerInput;
  }
  
  public int getWidth() {
	  return width;
  }
  
  public int getHeight() {
	  return height;
  }
  
  public char[][] getGrid() {
	  return grid;
  }

  public void displayBoard() {
	  System.out.println("0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18");
	  for(int i = 0; i < width; i++)
	  {
	      for(int j = 0; j < height; j++)
	      {
	          System.out.print(grid[i][j] + "  ");
	      }
	      System.out.println();
	  }
  }

  // returns string of row containing the last play of the user
  public String horizontal() {
    return new String(grid[lastTop]);
  }

  // returns string of column containing the last play of the user
  public String vertical() {
    StringBuilder sb = new StringBuilder(height);

    for (int h = 0; h < height; h++) {
      sb.append(grid[h][lastCol]);
    }
    return sb.toString();
  }

  // returns string of / diagonal containing the last play of the user
  public String slashDiagonal() {
    StringBuilder sb = new StringBuilder(height);

    for (int h = 0; h < height; h++) {
      int w = lastCol + lastTop - h;

      if (0 <= w && w < width) {
        sb.append(grid[h][w]);
      }
    }
    return sb.toString();
  }

  // returns string of \ diagonal containing the last play of the user
  public String backslashDiagonal() {
    StringBuilder sb = new StringBuilder(height);

    for (int h = 0; h < height; h++) {
      int w = lastCol - lastTop + h;

      if (0 <= w && w < width) {
        sb.append(grid[h][w]);
      }
    }
    return sb.toString();
  }

  // check if a substring is in str
  public static boolean contains(String str, String substring) {
    return str.indexOf(substring) >= 0;
  }

  // check if last play is a winning play
  public boolean isWinningPlay() {
    char sym = grid[lastTop][lastCol];
    // winning streak with the last play symbol
    String streak = String.format("%c%c%c%c%c%c", sym, sym, sym, sym, sym, sym);

    // check if streak is in row, col, diagonal or backslash diagonal
    return contains(horizontal(), streak) || 
           contains(vertical(), streak) || 
           contains(slashDiagonal(), streak) || 
           contains(backslashDiagonal(), streak);
  }

  // prompt user for column
  public void chooseAndDrop(char symbol, Scanner input) {
    do {
      System.out.println("\nPlayer " + symbol + " turn: ");
      int col = input.nextInt();

      if (!(0 <= col && col < width)) {
        System.out.println("Column must be between 0 and " + (width - 1));
        continue;
      }

      // places symbol in first available row in column that player picked
      for (int h = height - 1; h >= 0; h--) {
        if (grid[h][col] == '.') {
          grid[h][col] = symbol;
          lastTop = h;
          lastCol = col;
          return;
        }
      }

      // ask for new input if column is full
      System.out.println("Column " + col + " is full.");
    } while (true);
  }
  
  public void switchPlayers() {
	  if(player == 'R') {
		  player = 'Y';
	  } else {
		  player = 'R';
	  }
  }
}