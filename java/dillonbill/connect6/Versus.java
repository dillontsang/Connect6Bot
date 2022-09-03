package dillonbill.connect6;

import java.io.IOException;


import dillonbill.connect6.game.Board;
import dillonbill.connect6.game.BotvBotGame;
import dillonbill.connect6.game.BotvBotGame.Result;
import dillonbill.connect6.game.Connect6Game;
import dillonbill.connect6.game.Connect6Game.Stone;
import dillonbill.connect6.genetic.GeneticSolver;
import dillonbill.connect6.net.BoardNN;
import dillonbill.connect6.net.Network;

public class Versus {
	
	public static void main (String[] args) throws IOException, InterruptedException {
		Board board = new Board (9,9);
		final int GENE_POOL_SIZE = 24;
		
		BoardNN maker = new BoardNN(board);
		
		Network blackNet = maker._network;
		Network whiteNet = maker._network.createAdversary();
        String filebase = "/Users/williamcochran/Code/dillon/Connect6Bot/data/another";

        int blackGen = Integer.parseInt(args[0]);
		int whiteGen = Integer.parseInt(args[1]);
        
		blackNet.readNet(filebase + ".net", board);
		whiteNet.readNet(filebase + ".net", board);
		
		blackNet.buildNodeList(blackNet.getOutputNode());
		blackNet.buildWeightMap();
		
		whiteNet.buildNodeList(blackNet.getOutputNode());
		whiteNet.buildWeightMap();
		
		var blackGS = new GeneticSolver (GENE_POOL_SIZE, blackNet, whiteNet);
		var whiteGS = new GeneticSolver(GENE_POOL_SIZE, blackNet, whiteNet);
		
		blackGS.ReadTest1(filebase,blackNet,blackGen);
		whiteGS.ReadTest1(filebase,whiteNet,whiteGen);
		
		blackNet.setWeights(blackGS.getWeights(0));
		
		for (int i = 1; i != GENE_POOL_SIZE; i++) {
			whiteNet.setWeights(blackGS.getWeights(i));
			if (BotvBotGame.playGame(board, blackNet, whiteNet) == Result.WHITE) {
				blackNet.setWeights(blackGS.getWeights(i));
			}
		}
		
		int tieCount = 0;
		int blackCount = 0;
		int whiteCount = 0;
		whiteNet.buildNodeList(whiteNet.getOutputNode());
		whiteNet.buildWeightMap();
		for (int i = 0; i !=GENE_POOL_SIZE; i++) {
			whiteNet.setWeights(whiteGS.getWeights(i));
			switch (BotvBotGame.playGame(board, blackNet, whiteNet)) {
			case TIE:  tieCount++;break;
			case BLACK:  blackCount++;break;
			case WHITE:  whiteCount++;break;
			}
			
		}
		
		System.out.println(""+tieCount+" "+blackCount+" "+whiteCount);
		
		
	}

}
