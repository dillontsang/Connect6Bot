
public class Move {
	private int col;
	private int row;
	private int player;
	
	public Move() {
		col = 0;
		row = 0;
		player = 0;
	}
	
	public Move(int c, int r) {
		col = c;
		row = r;		
	}
	
	public Move(int c, int r, int p) {
		col = c;
		row = r;		
		player = p;
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
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getPlayer() {
		return player;
	}
	
}
