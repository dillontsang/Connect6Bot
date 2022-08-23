package dillonbill.connect6.test;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

import dillonbill.connect6.game.Board;
import dillonbill.connect6.game.BotvBotGame;
import dillonbill.connect6.genetic.GeneticSolver;
import dillonbill.connect6.net.BoardAI1;
import dillonbill.connect6.net.BoardNN;
import dillonbill.connect6.net.Network;
import dillonbill.connect6.net.Weights;

public class ReadTest3{
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Board board = new Board (9,9);
		Network _network1 = new Network();
		Network _network2 = new Network();
		BoardNN boardnn = new BoardNN(board);
		
		int player0Gen = 10;
		int player1Gen = 10;
		int genePoolSize = 50;
		int games = 100;
		int player1Wins = 0;
		double player1PercentWins = 0;
		
		var gs1 = new GeneticSolver (50,boardnn._network);
		var gs2 = new GeneticSolver (50,boardnn._network);
		
		List<Weights> _weights = new ArrayList<Weights>();
		List<Weights> _finals = new ArrayList<Weights>();
		List<Weights> _winners = new ArrayList<Weights>();
		
		File csvFile = new File("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\SecondExperimentChampionsTestSix.csv");
		PrintWriter out = new PrintWriter(csvFile);
		out.printf("%s, %s, %s\n", "Player 0 Generation", "Player 1 Generation", "Percent Wins For Player 1");
		
		// for(int a = 10; a <= 200; a += 20) {
			// player0Gen = ;
			// player1Gen = a;
			ReadTest1("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\data", _network1, board, gs1, player0Gen);
			ReadTest1("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\data", _network2, board, gs2, player1Gen);
			
			for(int b = 0; b < 4; b++) {
				for(int i = 0; i != genePoolSize - 1; i++) {
					
					_network1.setWeights(gs1.getWeights(i));
					_network2.setWeights(gs1.getWeights(i + 1));
					switch(playGame(board, _network1, _network2)) {
						case 0:
							gs1.swapWeights(i);
							// gs2.swapWeights(i);
							break;
						case 1:
							break;
					}
					board.reset();
				
				}
			}
			
			for(int j = genePoolSize - 1; j > genePoolSize - 5; j--) {
				_weights.add(gs1.getWeights(j));
			}
			
			
			
			for(int k = 0; k != _weights.size(); k += 2) {
				
				_network1.setWeights(_weights.get(k));
				_network2.setWeights(_weights.get(k + 1));
				switch(playGame(board, _network1, _network2)) {
				case 0:
					_finals.add(_weights.get(k));
					break;
				case 1:
					_finals.add(_weights.get(k + 1));
					break;
				}
				board.reset();
			}
			
			_network1.setWeights(_finals.get(0));
			_network2.setWeights(_finals.get(1));
			switch(playGame(board, _network1, _network2)) {
			case 0:
				_finals.remove(1);
				break;
			case 1:
				_finals.remove(0);			
				break;
			}
			board.reset();
			
			_winners.add(_finals.get(0));
			
			_weights.clear();
			_finals.clear();
		// }
		
		player0Gen = 10;
		ReadTest1("\\Users\\dillo\\Code\\Connect6Bot\\data\\SecondNetwork\\data", _network1, board, gs1, player0Gen);
		
		for(int c = 0; c < _winners.size(); c++) {
			_network1.setWeights(gs1.getWeights(0));
			_network2.setWeights(_winners.get(c));
			player1Wins = 0;
			
			for(int j = 0; j < games; j++) {
				switch(BotvBotGame.playGame(board, _network1, _network2)) {
					case TIE:
						// draws++;
						break;
					case BLACK:
						// player0Wins++;
						break;
					case WHITE:	
						player1Wins++;
						break;
				}
				board.reset();
			}
			
			player1PercentWins = (double) player1Wins / games;
			player1Gen = 10 + (20*c);
			out.printf("%d, %d, %f\n", player0Gen, player1Gen, player1PercentWins);
		}
		
		out.close();
		// BotvBotGame.playGame(board,_network1);
	}
	
	public static int playGame(Board b, Network n1, Network n2) {
		b.reset();
		BoardAI1 boardAI = new BoardAI1();
		switch(BotvBotGame.playGame(b, n1, n2)) {
		case TIE:
			if(boardAI.evaluate(b, 0) > boardAI.evaluate(b, 1)) {
				return 0;
			} else return 1;
		case BLACK:
			return 0;
		case WHITE:
			return 1;
		}
		return -1;
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