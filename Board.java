import java.util.Arrays;

public class Board {
	// dimensions for board
	private int width, height;
	// grid for board
	private char[][] grid;
	// store last move made
	private int lastCol = -1, lastTop = -1;
	
	public Board(int w, int h) {
	    width = w;
	    height = h;
	    grid = new char[h][w];

	    // initialize grid with periods
	    for (int i = 0; i < h; i++) {
	      Arrays.fill(grid[i] = new char[w], '.');
	    }
	  }
	public char getMove(int r, int c) {
		return grid[r][c];
	}
	
	public void applyMove(Move m) {
		if(m.getPlayer() == 0) {
			grid[m.getRow()][m.getCol()] = (char)48;
		} else
			grid[m.getRow()][m.getCol()] = (char)49;
		
	}
	
	public void unapplyMove(Move m) {
		grid[m.getRow()][m.getCol()] =  '.';
	}
	
	public void setLastCol(int l) {
		lastCol = l;
	}
	
	public void setLastTop(int t) {
		lastTop = t;
	}
	
	public int getLastCol() {
		return lastCol;
	}
	
	public int getLastTop() {
		return lastTop;
	}
	
	public char getLastMove() {
		return grid[lastTop][lastCol];
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
		  System.out.print("   ");
		  for(int a = 0; a < width; a++) {
			  if(a < 10) {
				  System.out.print(a + "  ");
			  } else
				  System.out.print(a + " ");
		  }
		  System.out.println();
		  for(int i = 0; i < width; i++)
		  {
			  if(i < 10) {
				  System.out.print(i + "  ");
			  } else 
				  System.out.print(i + " ");
			  
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
	  
	  public Move[] allAvailableMoves() {
		  Move availableMoves[] = new Move[height * width];
		  int a = 0;
		  for(int i = 0; i < width; i++) {
		      for(int j = 0; j < height; j++)
		      {
		          if(grid[i][j] == '.') {
		        	  availableMoves[a] = new Move(j, i);
		        	  a++;
		          }
		      }
		  }
		  return availableMoves;
	  }
}
