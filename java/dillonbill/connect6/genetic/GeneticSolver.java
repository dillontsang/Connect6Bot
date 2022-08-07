package dillonbill.connect6.genetic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import dillonbill.connect6.game.Board;
import dillonbill.connect6.game.BotvBotGame;
import dillonbill.connect6.net.Network;
import dillonbill.connect6.net.Weights;

/*
 * 
 * Algorithm:
 * 
 * 1)  Determine relative fitness of each gene
 * 		a)  
 * 2)  Select pairs of high fitness for merging
 * 
 * 3)  Create new gene pool
 * 
 * 4)  Write to disk
 * 
 * 5)  Goto 1
 * 
 */

public class GeneticSolver {
	private GenePool _genePool;
	private Network _netSideA;
	private Network _netSideB;
	
	
	public GeneticSolver (int count, Network net) {
		_netSideA = net;
		_netSideB = net.createAdversary();
		_genePool = new GenePool(count,net.getWeights());
	}
	
	public Weights getWeights(int n) {
		return _genePool.getWeights(n);
	}
	
	private int playGame (Board board, int sideA, int sideB) {
		_netSideA.setWeights(_genePool.getWeights(sideA));
		_netSideB.setWeights(_genePool.getWeights(sideB));
		
		//TODO:  make the game plug into here
		//return side that wins, -1 on draw;
		int retval = BotvBotGame.playGame(board,_netSideA,_netSideB)==1?sideA:sideB;
		board.reset();
		return retval;
	}
	
	private void evolveNextGeneration() {
		_genePool.sortPool();
		_genePool.evolve();
	}
	
	private int getRandomElement() {
		return (int) Math.floor(Math.random()*_genePool.numberOfGenes());
	}
	
	
	private void determineRelativeFitness(Board board, int gamesToPlay) {
		for (int i=0; i != gamesToPlay; i++) {
			if (i%100 == 0) System.out.println ("iteration " + i);
			int sideA = getRandomElement();
			int sideB;
			while (sideA == (sideB = getRandomElement()));
			int winner = playGame(board, sideA,sideB);
			if (winner == sideA) {
				_genePool.incrementScore(sideA);
			} else {
				_genePool.incrementScore(sideB);
			}
		}
	}
	
	public void optimize (Board board, int skip, String experimentName) {
		int j=0;
		try {
			_netSideA.writeNet(experimentName+".net");
		} catch (IOException ioe) {
			System.err.println(ioe);
			System.exit(1);
		}
		System.out.println ("Beginning optimization");
		while (true) {
			_genePool.resetScores();
			determineRelativeFitness(board, 2000);  //TODO:  Another hyperparameter
			evolveNextGeneration();
			System.out.println ("Evolved generation " + j);
			j = j + 1;
			if (j%skip == 0) {
				System.out.println ("-----  Generation " + j);
				_genePool.writeState(experimentName + "." + j);
			}	
		}
	}

	public void optimize (Board board, String experimentName) {
		optimize(board,10,experimentName);
	}
	
	public void ReadTest1(String filename, Network n, int generation) {
		_genePool.readState(filename + "." + generation, n);
	}	
}
