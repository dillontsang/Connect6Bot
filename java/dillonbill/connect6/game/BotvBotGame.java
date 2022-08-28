package dillonbill.connect6.game;

import java.util.Scanner;

import dillonbill.connect6.net.Network;

public class BotvBotGame {
	
	static public int playGame (Board board, Network net1, Network net2) {
	     int height = board.getHeight(); 
	     int width = board.getWidth();
	     int moves = (height * width)/2;
	     board.reset();

	     Connect6Game connect6game = new Connect6Game(board,net1,net2);
	     

	     connect6game.applyFirstMove(1);
	     

	     // change player turn
	     for (int player = 0; moves-- > 0; player = 1 - player) {
	   	  
	   	  if(player == 0) {
	   		  for(int i = 0; i < 2; i++) {
	   			  connect6game.doMinimaxMove(player, i);
	   			  
	   			  if (connect6game.isWinningPlay()) {
	   				  // System.out.println("\nPlayer " + player + " wins!");
	   		  		  return 1;
	   			  }
	   		 }
						
	   	  } else if (player == 1) {
	   		  for(int i = 0; i < 2; i++) {
	   			  connect6game.doMinimaxMove(player, i);
	   			  if (connect6game.isWinningPlay()) {
	   				  // System.out.println("\nPlayer " + player + " wins!");
	   		  		  return 2;
	   			  }
	   		  }
	   	  }
	     }
		return 0;
	}
	
	static public int playGameWithBoardPrints (Board board, Network net1, Network net2) {
	     int height = board.getHeight(); 
	     int width = board.getWidth();
	     int moves = (height * width)/2;
	     board.reset();

	     Connect6Game connect6game = new Connect6Game(board,net1,net2);
	     

	     connect6game.applyFirstMove(1);
	     connect6game.displayBoard();
	     

	     // change player turn
	     for (int player = 0; moves-- > 0; player = 1 - player) {
	   	  
	   	  if(player == 0) {
	   		  for(int i = 0; i < 2; i++) {
	   			  connect6game.doMinimaxMove(player, i);
	   			  connect6game.displayBoard();
	   			  System.out.println();
	   			  if (connect6game.isWinningPlay()) {
	   				  // System.out.println("\nPlayer " + player + " wins!");
	   		  		  return 1;
	   			  }
	   		 }
						
	   	  } else if (player == 1) {
	   		  for(int i = 0; i < 2; i++) {
	   			  connect6game.doMinimaxMove(player, i);
	   			  connect6game.displayBoard();
	   			  System.out.println();
	   			  if (connect6game.isWinningPlay()) {
	   				  // System.out.println("\nPlayer " + player + " wins!");
	   		  		  return 2;
	   			  }
	   		  }
	   	  }
	     }
		return 0;
	}
	
	static public int playGame (Board board, Network net1) {
	 Scanner input = new Scanner(System.in);
     int height = board.getHeight(); 
     int width = board.getWidth();
     int moves = (height * width)/2;
     board.reset();

     Connect6Game connect6game = new Connect6Game(board, net1);
     

     // instructions
     System.out.println("Use 0-" + (width - 1) + " to choose a column and use 0-" + (height - 1) + " to choose a row.");
     // display board
     connect6game.applyFirstMove(1);
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
   	 return 0;
	}
	
}
