package dillonbill.connect6.game;

import java.util.Arrays;
import java.util.HashSet;

public class Board {
	// dimensions for board
	private int width, height;
	// grid for board
	private char[][] grid;
	// store last move made
	private int lastCol = -1, lastRow = -1;
	private Move _lastMove;
	
	public Board(int h, int w) {
	    reset(h, w);
	}
	
	public Board (Board b) {
		reset (b.height,b.width);
	}
	
	public Move getLast () {
		if (_lastMove == null) throw new RuntimeException();
		return _lastMove;
	}
	
	public void reset(int h, int w) {
		width = w;
	    height = h;
	    grid = new char[h][w];

	    // initialize grid with periods
	    for (int i = 0; i < h; i++) {
	      Arrays.fill(grid[i] = new char[w], '.');
	    }
	}
	
	public void reset () {
		reset (grid.length,grid[0].length);
	}
	
	public char getMove(int r, int c) {
		return grid[r][c];
	}
	
	public void applyMove(Move m) {
		if(grid[m.getRow()][m.getCol()] != '.') {
			throw new RuntimeException();
		}
		
		if(m.getPlayer() == 0) {
			grid[m.getRow()][m.getCol()] = (char)48;
		} else
			grid[m.getRow()][m.getCol()] = (char)49;
		_lastMove = m;		
	}
	
	public void unapplyMove(Move m) {
		switch (grid[m.getRow()][m.getCol()]) {
			case '0': if (m.getPlayer() != 0) throw new RuntimeException ();
	  				  break;
			case '1': if (m.getPlayer() != 1) throw new RuntimeException ();
	  		      break;
	  		default:  throw new RuntimeException ();
		}
		
		grid[m.getRow()][m.getCol()] =  '.';
	}
	
	public void setLastCol(int l) {
		lastCol = l;
	}
	
	public void setLastRow(int t) {
		lastRow = t;
	}
	
	public int getLastCol() {
		return lastCol;
	}
	
	public int getLastRow() {
		return lastRow;
	}
	
	public char getLastMove() {
		return grid[lastRow][lastCol];
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
		public String horizontal(int row) {
			return new String(grid[row]);
		}

	  // returns string of column containing the last play of the user
	  public String vertical(int col) {
	    StringBuilder sb = new StringBuilder(height);

	    for (int h = 0; h < height; h++) {
	      sb.append(grid[h][col]);
	    }
	    return sb.toString();
	  }

	  // returns string of / diagonal containing the last play of the user
	  public String slashDiagonal(int col, int row) {
	    StringBuilder sb = new StringBuilder(height);

	    for (int h = 0; h < height; h++) {
	      int w = col + row - h;

	      if (0 <= w && w < width) {
	        sb.append(grid[h][w]);
	      }
	    }
	    return sb.toString();
	  }

	  // returns string of \ diagonal containing the last play of the user
	  public String backslashDiagonal(int col, int row) {
	    StringBuilder sb = new StringBuilder(height);

	    for (int h = 0; h < height; h++) {
	      int w = col - row + h;

	      if (0 <= w && w < width) {
	        sb.append(grid[h][w]);
	      }
	    }
	    return sb.toString();
	  }
	  
	  public HashSet<Move> allRelevantMoves() {
		  HashSet<Move> relevantMoves = new HashSet<Move>();
		  HashSet<Move> availableMoves = allAvailableMoves();
		  // double average = 0;	  
		  int neighbors = 0;
		  for(Move m: availableMoves) {
			  	neighbors = 0;
		  		if(m.getCol() == 0 && m.getRow() == 0) {
		  			for(int i = 0; i <= 1; i++) {
					  	for(int a = 0; a <= 1; a++) {
					  		if(grid[m.getRow() + a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
		  			
			  	} else if (m.getCol() == 0 && m.getRow() == height - 1) {
			  		for(int i = 0; i <= 1; i++) {
					  	for(int a = -1; a <= 0; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else if (m.getCol() == width - 1 && m.getRow() == 0) {
			  		for(int i = -1; i <= 0; i++) {
					  	for(int a = 0; a <= 1; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else if (m.getCol() == width - 1 && m.getRow() == height - 1) {
			  		for(int i = -1; i <= 0; i++) {
					  	for(int a = -1; a <= 0; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else if(m.getCol() == 0) {
			  		for(int i = 0; i <= 1; i++) {
					  	for(int a = -1; a <= 1; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else if(m.getCol() == width - 1) {
			  		for(int i = -1; i <= 0; i++) {
					  	for(int a = -1; a <= 1; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else if(m.getRow() == 0) {
			  		for(int i = -1; i <= 1; i++) {
					  	for(int a = 0; a <= 1; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else if(m.getRow() == height - 1) {
			  		for(int i = -1; i <= 1; i++) {
					  	for(int a = -1; a <= 0; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	} else {
			  		for(int i = -1; i <= 1; i++) {
					  	for(int a = -1; a <= 1; a++) {
					  		if(grid[m.getRow()+ a][m.getCol() + i] == '0' || grid[m.getRow() + a][m.getCol() + i] == '1') {
						  		neighbors++;
			  				}	
			  			}
			  		}
			  		
			  	}
		  		
		  		m.setNeighbors(neighbors);
		  		// average += neighbors;
		  }
		  
		  // average /= availableMoves.size();
		  
		  
		  for(Move m: availableMoves) {
				  if(m.getNeighbors() >= 1) {
					  relevantMoves.add(m);
				  }
		  }
		  return relevantMoves;
	  }
	  
	  public HashSet<Move> allAvailableMoves() {
		  HashSet<Move> availableMoves = new HashSet<Move>();
		  Move tempMove = new Move();
		  for(int i = 0; i < width; i++) {
		      for(int j = 0; j < height; j++)
		      {
		          if(grid[i][j] == '.') {
		        	  tempMove = new Move(i, j);
		        	  availableMoves.add(tempMove);
		          }
		      }
		  }
		  return availableMoves;
	  }
	  
	  public HashSet<Move> movesMade(int player) {
		  char sym;
		  if(player == 0) {
				sym = (char)48;
		  } else {
				sym = (char)49;
		  }	
		  HashSet<Move> movesMade = new HashSet<Move>();
		  Move tempMove = new Move();
		  for(int i = 0; i < width; i++) {
		      for(int j = 0; j < height; j++)
		      {
		          if(grid[i][j] == sym) {
		        	  tempMove = new Move(j, i);
		        	  movesMade.add(tempMove);
		          }
		      }
		  }
		  return movesMade;
	  }
}
