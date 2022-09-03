package dillonbill.connect6.genetic;

import java.io.IOException;


import dillonbill.connect6.game.Board;
import dillonbill.connect6.game.BotvBotGame;
import dillonbill.connect6.game.BotvBotGame.Result;
import dillonbill.connect6.net.Network;
import dillonbill.connect6.net.Node;
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
	private static final int NUM_THREADS = 6;
	Network _blackNet;
	Network _whiteNet;
	
	public void printStats() {
		_genePool.showAngleStats();
	}
	
	private class LoopThread extends Thread {
		int _start,_finish;
		boolean _printer;
		Board _board;
		Network _blackNetThread;
		Network _whiteNetThread;
		
		public void run () {
			for (int i=_start; i != _finish; i++) {
				if (_printer) {
					int num = 100 * (i-_start);
					int den = _finish - _start;
					System.out.println ("  " + (num/den) + "% done");
				}
				for (int j=0; j != _genePool.numberOfGenes(); j++) {
					if (i==j) continue;
					int black = i;
					int white = j;
					switch (playGame (black,white,_blackNetThread,_whiteNetThread))
					{
					case BLACK:  _genePool.incrementScore(black);
						break;
					case TIE:	  
						break;
					case WHITE:  _genePool.incrementScore(white);
						break;
					default:
						break;
					
					}
				}
			}
		}

		private Result playGame (int black, int white, Network blackNet, Network whiteNet) {
			blackNet.setWeights(_genePool.getWeights(black));
			whiteNet.setWeights(_genePool.getWeights(white));
			
			//TODO:  make the game plug into here
			//return side that wins, -1 on draw;
			Result retval = BotvBotGame.playGame(_board, blackNet, whiteNet, false);
			_board.reset();
			return retval;
		}
	}
	
	public GeneticSolver (int count, Network net) {
		_blackNet = net;
		_whiteNet = net.createAdversary();
		_blackNet.getWeights().allocateWeights(_blackNet.getNodes());
		_whiteNet.getWeights().allocateWeights(_whiteNet.getNodes());
		_genePool = new GenePool(count,net.getWeights());
	}
	
	public GeneticSolver (int count, Network blackNet, Network whiteNet) {
		_blackNet = blackNet;
		_whiteNet = whiteNet;
		_blackNet.getWeights().allocateWeights(_blackNet.getNodes());
		_whiteNet.getWeights().allocateWeights(_whiteNet.getNodes());
		_genePool = new GenePool(count,blackNet.getWeights());
	}
	
	public Weights getWeights(int n) {
		return _genePool.getWeights(n);
	}
	
	
	private void evolveNextGeneration() {
		_genePool.sortPool();
		_genePool.evolve();
	}
	
	private int getRandomElement() {
		return (int) Math.floor(Math.random()*_genePool.numberOfGenes());
	}
	
	
	private void determineRelativeFitness(Board board, int gamesToPlay) {
		LoopThread[] threads = new LoopThread[NUM_THREADS];
		for (int i = 0; i != NUM_THREADS; i++) {
			threads[i] = new LoopThread();
			threads[i]._printer = i == 0;
			threads[i]._board = new Board (board);
			threads[i]._blackNetThread = _blackNet.cloneForThreads(threads[i]._board);
			threads[i]._whiteNetThread = _whiteNet.cloneForThreads(threads[i]._board);
			threads[i]._start = i*_genePool.numberOfGenes()/NUM_THREADS;
			threads[i]._finish = threads[i]._start + _genePool.numberOfGenes()/NUM_THREADS;
		}
		threads[NUM_THREADS-1]._finish= _genePool.numberOfGenes();
		for (int i = 0; i != NUM_THREADS;i++) {
			threads[i].start();
		}
		for (int i = 0; i != NUM_THREADS;i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public void optimize (Board board, int skip, String experimentName) {
		int j=0;
		try {
			_blackNet.writeNet(experimentName+".net");
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
