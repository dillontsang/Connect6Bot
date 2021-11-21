import java.util.Random;

public class BoardAI {
	private int value;
	public BoardAI() {
		value = 0;
	}
	public int evaluate(Board b, int player) {
		Random random = new Random();
		value = random.nextInt(100);
		return value;
	}
 }
