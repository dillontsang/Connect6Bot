import java.util.Set;

public class AggressiveAI implements BoardAI {
	private int value;
	private char sym;
	
	public AggressiveAI() {
		value = 0;
	}
	
	public static boolean contains(String str, String substring) {
	    return str.indexOf(substring) >= 0;
	  }
	
	public int evaluate(Board b, int player) {
		if(player == 0) {
			sym = (char)48;
		} else {
			sym = (char)49;
		}	
		String two = String.format("%c%c", sym, sym);
		String three = String.format("%c%c%c", sym, sym, sym);
		String four = String.format("%c%c%c%c", sym, sym, sym, sym);
		String five = String.format("%c%c%c%c%c", sym, sym, sym, sym, sym);
		String six = String.format("%c%c%c%c%c%c", sym, sym, sym, sym, sym, sym);
		Set<Move> movesMade = b.movesMade(player);
		int pointsPerStone;
		for(Move m: movesMade) {
			pointsPerStone = 0;
		  	
		  	if(contains(b.horizontal(m.getRow()), six)) {
		  		value = Integer.MAX_VALUE;
		  		break;
		  	} else if(contains(b.horizontal(m.getRow()), five)) {
		  		pointsPerStone += 256;
		  	} else if(contains(b.horizontal(m.getRow()), four)) {
		  		pointsPerStone += 64;
		  	} else if(contains(b.horizontal(m.getRow()), three)) {
		  		pointsPerStone += 16;
		  	} else if(contains(b.horizontal(m.getRow()), two)) {
		  		pointsPerStone += 4;
		  	}
		  	
		  	if(contains(b.vertical(m.getCol()), six)) {
		  		value = Integer.MAX_VALUE;
		  		break;
		  	} else if(contains(b.vertical(m.getCol()), five)) {
		  		pointsPerStone += 256;
		  	} else if(contains(b.vertical(m.getCol()), four)) {
		  		pointsPerStone += 64;
		  	} else if(contains(b.vertical(m.getCol()), three)) {
		  		pointsPerStone += 16;
		  	} else if(contains(b.vertical(m.getCol()), two)) {
		  		pointsPerStone += 4;
		  	}
		  	
		  	if(contains(b.slashDiagonal(m.getCol(), m.getRow()), six)) {
		  		value = Integer.MAX_VALUE;
		  		break;
		  	} else if(contains(b.slashDiagonal(m.getCol(), m.getRow()), five)) {
		  		pointsPerStone += 256;
		  	} else if(contains(b.slashDiagonal(m.getCol(), m.getRow()), four)) {
		  		pointsPerStone += 64;
		  	} else if(contains(b.slashDiagonal(m.getCol(), m.getRow()), three)) {
		  		pointsPerStone += 16;
		  	} else if(contains(b.slashDiagonal(m.getCol(), m.getRow()), two)) {
		  		pointsPerStone += 4;
		  	}
		  	
		  	if(contains(b.backslashDiagonal(m.getCol(), m.getRow()), six)) {
		  		value = Integer.MAX_VALUE;
		  		break;
		  	} else if(contains(b.backslashDiagonal(m.getCol(), m.getRow()), five)) {
		  		pointsPerStone += 256;
		  	} else if(contains(b.backslashDiagonal(m.getCol(), m.getRow()), four)) {
		  		pointsPerStone += 64;
		  	} else if(contains(b.backslashDiagonal(m.getCol(), m.getRow()), three)) {
		  		pointsPerStone += 16;
		  	} else if(contains(b.backslashDiagonal(m.getCol(), m.getRow()), two)) {
		  		pointsPerStone += 4;
		  	}
		  	
		    value += pointsPerStone;
		}
		return value;
	}
 }
