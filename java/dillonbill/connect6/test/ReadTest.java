package dillonbill.connect6.test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import dillonbill.connect6.game.Board;
import dillonbill.connect6.game.BotvBotGame;
import dillonbill.connect6.genetic.GeneticSolver;
import dillonbill.connect6.net.BoardNN;
import dillonbill.connect6.net.Network;

public class ReadTest{
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Board board = new Board (9,9);
		Network _network1 = new Network();
		Network _network2 = new Network();
		BoardNN boardnn = new BoardNN(board);
		
		int player0Gen = 10;
		int player1Gen = 10;
		int games = 500;
		// int player0Wins = 0;
		int player1Wins = 0;
		// int draws = 0;
		// double player0PercentWins = 0;
		double player1PercentWins = 0;
		
		var gs1 = new GeneticSolver (50,boardnn._network);
		var gs2 = new GeneticSolver (50,boardnn._network);
		File csvFile = new File("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\DataGraph.csv");
		PrintWriter out = new PrintWriter(csvFile);
		out.printf("%s, %s, %s\n", "Player 0 Generation", "Player 1 Generation", "Percent Wins For Player 1");
		
		for(int i = 10; i <= 200; i += 20) {
			player1Gen = i;
			player1Wins = 0;
			ReadTest1("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\data", _network1, board, gs1, player0Gen);
			ReadTest1("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\data", _network2, board, gs2, player1Gen);
		
			for(int j = 0; j < games; j++) {
				_network1.setWeights(gs1.getWeights((int)(Math.random() * 50)));
				_network2.setWeights(gs2.getWeights((int)(Math.random() * 50)));
				switch(BotvBotGame.playGame(board, _network1, _network2)) {
					case 0:
						// draws++;
						break;
					case 1:
						// player0Wins++;
						break;
					case 2:
						player1Wins++;
						break;
				}
				board.reset();
			}
		
			// player0PercentWins = (double) player0Wins / games;
			player1PercentWins = (double) player1Wins / games;
		
			out.printf("%d, %d, %f\n", player0Gen, player1Gen, player1PercentWins);
		}
		
		out.close();
		
		/* System.out.println("Generation for player 0: " + player0Gen);
		System.out.println("Generation for player 1: " + player1Gen);
		System.out.println("Player 0 wins: " + player0Wins);
		System.out.println("Player 1 wins: " + player1Wins);
		System.out.println("Percent wins for player 0: " + player0PercentWins);
		System.out.println("Percent wins for player 1: " + player1PercentWins);
		System.out.println("Draws: " + draws); */
		
		// BotvBotGame.playGame(board,_network1);
	}
	
	public static void ReadTest1(String filename, Network n, Board b, GeneticSolver gs, int generation) {
		
		try {
			n.readNet(filename + ".net" , b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		gs.ReadTest1(filename, n, generation);
		
	}	
}