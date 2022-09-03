package dillonbill.connect6.game;

import java.util.Scanner;
import dillonbill.connect6.game.Connect6Game.Stone;

import dillonbill.connect6.net.Network;

public class BotvBotGame {
	public enum Result {TIE, BLACK, WHITE};
	
	static public Result playGame (Board board, Network blackAI, Network whiteAI) {
		return playGame (board,blackAI,whiteAI,false);
	}

	static public Result getResult (Stone s) {
		if (s == Stone.BLACK) return Result.BLACK;
		return Result.WHITE;
	}
	
	static public Result playGame (Board board, Network blackAI, Network whiteAI, boolean print) {
	     int height = board.getHeight(); 
	     int width = board.getWidth();
	     int moves = (height * width)/2;
	     Result winner = Result.TIE;
	     board.reset();

	     blackAI.buildNodeList(blackAI.getOutputNode());
	     whiteAI.buildNodeList(whiteAI.getOutputNode());
		 blackAI.setWeights();
		 whiteAI.setWeights();
			

	     Connect6Game connect6game = new Connect6Game(board,blackAI,whiteAI);
	     

	     connect6game.applyFirstMove(Stone.BLACK);
	 
	     
	     for (Stone player = Stone.WHITE; moves-- > 0; player = player.otherPlayer()) {
	    	 if (print) {
	    		 board.displayBoard();
	    	 }
	    	 for(int i = 0; i < 2; i++) {
	    		 connect6game.doMinimaxMove(player, i);

	    		 if (connect6game.isWinningPlay()) {
	    			 winner = getResult(player);
	    			 break;
	    		 }
	    	 }
	    	 if (winner != Result.TIE)
	    		 break;
	     }
	     return winner;
	}	
	
	static public int playGame (Board board, Network ai) {
	 Scanner input = new Scanner(System.in);
     int height = board.getHeight(); 
     int width = board.getWidth();
     int moves = (height * width)/2;
     board.reset();

     Connect6Game connect6game = new Connect6Game(board, ai);
     
/*
     // instructions
     System.out.println("Use 0-" + (width - 1) + " to choose a column and use 0-" + (height - 1) + " to choose a row.");
     // display board
     connect6game.applyFirstMove(Stone.BLACK);
     connect6game.displayBoard();
     

     // change player turn
     for (int player = 0; moves-- > 0; player = 1 - player) {
   	  
   	  if(player == 0) {
   		  for(int i = 0; i < 2; i++) {
   			  System.out.println("\nPlayer " + player + " turn: ");
   			  connect6game.chooseMove(player, input);
   			  // display the board
   			  connect6game.displayBoard();
   			  // check if player won  
   			  if (connect6game.isWinningPlay()) {
   				  System.out.println("\nPlayer " + player + " wins!");
   		  		  return 1;
   			  }
   		 }
					
   	  } else if (player == 1) {
   		  for(int i = 0; i < 2; i++) {
   			  System.out.println("\nPlayer " + player + " turn: ");
   			  connect6game.doMinimaxMove(player, i);
   			  
   			  connect6game.displayBoard();
   			  System.out.println();
   			  
   			  if (connect6game.isWinningPlay()) {
   				  System.out.println("\nPlayer " + player + " wins!");
   		  		  return 2;
   			  }
   		  }
   	  }
     }
  */ 	 
     return 0;
	}
	
}
