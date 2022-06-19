
public class Main {
  public static void main(String[] args) {
	  Game.BOARD = new Board (9,9);
	  // The network is constructed here...
	  BoardNN boardnn = new BoardNN(Game.BOARD);
	  var gs = new GeneticSolver (5,boardnn._network);
	  gs.optimize();
  }
}