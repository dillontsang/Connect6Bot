
public class BoardTest extends Board{
	public BoardTest(int h, int w) {
		super(h, w);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		BoardTest1();
	}
	
	public static void BoardTest1() {
		Board board = new Board(3, 3);
		Move a = new Move(0, 1);
		Move b = new Move(0, 2);
		board.applyMove(a);
		board.applyMove(b);
		if(board.getGrid()[0][1] != '0') {
			throw new RuntimeException("Failed applymove");
		}
		if(board.getGrid()[0][2] != '0') {
			throw new RuntimeException("Failed applymove");
		}
		
		board.unapplyMove(a);
		board.unapplyMove(b);
		if(board.getGrid()[0][1] != '.') {
			throw new RuntimeException("Failed unapplymove");
		}
		if(board.getGrid()[0][2] != '.') {
			throw new RuntimeException("Failed unapplymove");
		}
		
		board.reset(3, 3);
		
		board.applyMove(a);
		board.applyMove(b);
		board.unapplyMove(b);
		if(board.getGrid()[0][1] != '0') {
			throw new RuntimeException("Failed unapplymove");
		}
		if(board.getGrid()[0][2] != '.') {
			throw new RuntimeException("Failed unapplymove");
		}
		
	}
}
