package dillonbill.connect6;

import java.io.IOException;

import dillonbill.connect6.game.Board;
import dillonbill.connect6.game.BotvBotGame;
import dillonbill.connect6.genetic.GeneticSolver;
import dillonbill.connect6.net.BoardNN;
import dillonbill.connect6.net.Network;

public class Versus {
	
	public static void main (String[] args) throws IOException {
		Board board = new Board (9,9);
		Network _network1 = new Network();
		Network _network2 = new Network();
		BoardNN boardnn = new BoardNN(board);
        String filebase = "/Users/williamcochran/Code/dillon/Connect6Bot/data/another";

        int laterGen = 90;
		int earlierGen = 20;
		
		var earlierGS = new GeneticSolver (20,boardnn._network);
		var laterGS = new GeneticSolver(20,boardnn._network);
		
		_network1.readNet(filebase + ".net", board);
		_network2.readNet(filebase + ".net", board);
		
		earlierGS.ReadTest1(filebase,_network1,earlierGen);
		laterGS.ReadTest1(filebase, _network2, laterGen);
		_network2.setWeights(laterGS.getWeights(9));
		for (int i = 1; i != 20; i++) {
			_network1.setWeights(laterGS.getWeights(i));
			if (BotvBotGame.playGameWithBoardPrints(board, _network1, _network2) == 1) {
				_network2.setWeights(laterGS.getWeights(i));
				System.out.println ("Goer");
			}
			System.exit(0);
		}
		
		int winCount = 0;
		for (int i = 0; i !=20; i++) {
			_network1.setWeights(earlierGS.getWeights(i));
			int winner = BotvBotGame.playGame(board, _network1, _network2);
			if (winner == 2) winCount++;
		}
		
		System.out.println(""+winCount);
		
		
	}

}
