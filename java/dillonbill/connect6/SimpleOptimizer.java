package dillonbill.connect6;


import dillonbill.connect6.game.*;
import dillonbill.connect6.genetic.GeneticSolver;
import dillonbill.connect6.net.*;


public class SimpleOptimizer {
  public static void main(String[] args) {
	  Board board = new Board (9,9);
	  // The network is constructed here...
	  BoardNN boardnn = new BoardNN(board);
	  var gs = new GeneticSolver (24,boardnn._network);
	  gs.optimize(board,10,"/Users/williamcochran/Code/dillon/Connect6Bot/data/another");
  }
}