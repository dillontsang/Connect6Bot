import java.util.Scanner;

public class Game {
	
	static public Board BOARD = null;

	static public int playGame (Network net1, Network net2) {
		 Scanner input = new Scanner(System.in);
	     int height = 9; 
	     int width = 9;
	     int moves = (height * width)/2;

	     Connect6Game connect6game = new Connect6Game(BOARD,net1,net2);
	     

	     // instructions
	     //System.out.println("Use 0-" + (width - 1) + " to choose a column and use 0-" + (height - 1) + " to choose a row.");
	     // display board
	     connect6game.applyFirstMove(1);
	     //connect6game.displayBoard();
	     

	     // change player turn
	     for (int player = 0; moves-- > 0; player = 1 - player) {
	   	  
	   	  if(player == 0) {
	   		  for(int i = 0; i < 2; i++) {
	   			  //System.out.println("\nPlayer " + player + " turn: ");
	   			  connect6game.doMinimaxMove(player, i);
	   			  
	   			  //connect6game.displayBoard();
	   			  //System.out.println();
	   			  	   			  
	   			  // check if player won  
	   			  if (connect6game.isWinningPlay()) {
	   				  System.out.println("\nPlayer " + player + " wins!");
	   		  		  return 1;
	   			  }
	   		 }
						
	   	  } else if (player == 1) {
	   		  for(int i = 0; i < 2; i++) {
	   			  //System.out.println("\nPlayer " + player + " turn: ");
	   			  connect6game.doMinimaxMove(player, i);
	   			  
	   			  //connect6game.displayBoard();
	   			  //System.out.println();
	   			  
	   			  if (connect6game.isWinningPlay()) {
	   				  System.out.println("\nPlayer " + player + " wins!");
	   		  		  return 2;
	   			  }
	   		  }
	   	  }
	     }
		return 0;
	}
	
	static public int playGame (Network net1) {
	 Scanner input = new Scanner(System.in);
     int height = 9; 
     int width = 9;
     int moves = (height * width)/2;

     Connect6Game connect6game = new Connect6Game(BOARD);
     

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
   			  /*connect6game.doMinimaxMove(player, i);
   			  
   			  connect6game.displayBoard();
   			  System.out.println();
   			  
   			  if (connect6game.isWinningPlay()) {
   				  System.out.println("\nPlayer " + player + " wins!");
   		  		  return;
   			  } */
   			  
   			    
   			  // ask user to choose column
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
