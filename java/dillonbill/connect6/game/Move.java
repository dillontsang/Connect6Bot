package dillonbill.connect6.game;

public class Move {
	private int col;
	private int row;
	private int player;
	private int neighbors;
	
	public Move() {
		col = 0;
		row = 0;
		player = 0;
	}
	
	public Move(int r, int c) {
		col = c;
		row = r;		
	}
	
	public Move(int r, int c, int p) {
		col = c;
		row = r;		
		player = p;
	}
	
	public Move (int r, int c, Connect6Game.Stone stone) {
		this(r,c,stone.getValue());
	}
	
	public void dup (Move m) {
		col = m.col;
		row = m.row;
		player = m.player;
		neighbors = m.neighbors;
	}
	
	public void setCol(int c) {
		col = c;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public void setPlayer(int p) {
		player = p;
	}
	
	public void setNeighbors(int n) {
		neighbors = n;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public int getNeighbors() {
		return neighbors;
	}
	
}
