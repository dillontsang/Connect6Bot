import java.util.HashSet;

public class BoardAI {
	private int value;
	private char sym;
	public BoardAI() {
		value = 0;
	}
	
	public int evaluate(Board b, int player) {
		if(player == 0) {
			sym = (char)48;
		} else {
			sym = (char)49;
		}	
		HashSet<Move> movesMade = b.movesMade(player);
		int similarNeighbors;
		for(Move m: movesMade) {
		  	similarNeighbors = 0;
	  		if(m.getCol() == 0 && m.getRow() == 0) {
	  			for(int i = 0; i <= 1; i++) {
				  	for(int a = 0; a <= 1; a++) {
					  	if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
	  			
		  	} else if (m.getCol() == 0 && m.getRow() == b.getHeight() - 1) {
		  		for(int i = 0; i <= 1; i++) {
				  	for(int a = -1; a <= 0; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else if (m.getCol() == b.getWidth() - 1 && m.getRow() == 0) {
		  		for(int i = -1; i <= 0; i++) {
				  	for(int a = 0; a <= 1; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else if (m.getCol() == b.getWidth() - 1 && m.getRow() == b.getHeight() - 1) {
		  		for(int i = -1; i <= 0; i++) {
				  	for(int a = -1; a <= 0; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else if(m.getCol() == 0) {
		  		for(int i = 0; i <= 1; i++) {
				  	for(int a = -1; a <= 1; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else if(m.getCol() == b.getWidth() - 1) {
		  		for(int i = -1; i <= 0; i++) {
				  	for(int a = -1; a <= 1; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else if(m.getRow() == 0) {
		  		for(int i = -1; i <= 1; i++) {
				  	for(int a = 0; a <= 1; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else if(m.getRow() == b.getHeight() - 1) {
		  		for(int i = -1; i <= 1; i++) {
				  	for(int a = -1; a <= 0; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	} else {
		  		for(int i = -1; i <= 1; i++) {
				  	for(int a = -1; a <= 1; a++) {
				  		if(b.getGrid()[m.getRow() + a][m.getCol() + i] == sym) {
					  		similarNeighbors++;
		  				}	
		  			}
		  		}
		  		
		  	}
	  		value += similarNeighbors;
		}
		return value;
	}
 }
