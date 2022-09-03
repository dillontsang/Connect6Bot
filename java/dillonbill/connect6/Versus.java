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

        int blackGen;
        try{
        	blackGen = Integer.parseInt(args[0]);
        } catch (RuntimeException e) {
        	blackGen = 10;
        }
        
		blackNet.readNet(filebase + ".net", board);
		whiteNet.readNet(filebase + ".net", board);
		
		blackNet.buildNodeList(blackNet.getOutputNode());
		blackNet.buildWeightMap();
		whiteNet.buildNodeList(whiteNet.getOutputNode());
		
		var blackGS = new GeneticSolver (GENE_POOL_SIZE, blackNet, whiteNet);
		var whiteGS = new GeneticSolver(GENE_POOL_SIZE, blackNet, whiteNet);
		
		blackGS.ReadTest1(filebase,blackNet,blackGen);
		whiteGS.ReadTest1(filebase,whiteNet,blackGen);

		// This should be relatively good against random
		blackNet.setWeights(blackGS.getWeights(0));
		whiteNet.setWeights(blackNet.getWeights().clone());
		
		int tieCount = 0;
		int blackCount = 0;
		int whiteCount = 0;

		for (int i = 0; i != GENE_POOL_SIZE*5; i++) {
			blackNet.setWeights(blackGS.getWeights(i%GENE_POOL_SIZE));
			whiteNet.getWeights().randomize(4);
			switch (BotvBotGame.playGame(board, blackNet, whiteNet)) {
			case TIE:  tieCount++;break;
			case BLACK:  blackCount++;break;
			case WHITE:  whiteCount++;break;
			}
			
		}
		
		System.out.println(""+tieCount+" "+blackCount+" "+whiteCount);
		
		
	}

}
