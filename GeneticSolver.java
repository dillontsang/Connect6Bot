

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
	
	private int playGame (int sideA, int sideB) {
		_netSideA.setWeights(_genePool.getWeights(sideA));
		_netSideB.setWeights(_genePool.getWeights(sideB));
		
		//TODO:  make the game plug into here
		//return side that wins, -1 on draw;
	}
	
	private int getRandomElement() {
		return (int) Math.floor(Math.random()*_genePool.numberOfGenes());
	}
	
	private void determineRelativeFitness(int gamesToPlay) {
		for (int i=0; i != gamesToPlay; i++) {
			int sideA = getRandomElement();
			int sideB;
			while (sideA != (sideB = getRandomElement()));
			int winner = playGame(sideA,sideB);
			if (winner == sideA) {
				_genePool.incrementScore(sideA);
				_genePool.decrementScore(sideB);
			} else {
				_genePool.incrementScore(sideB);
				_genePool.decrementScore(sideA);
			}
		}
	}
	
	public void optimize () {
		while (true) {
			determineRelativeFitness(10000);  //TODO:  Another hyperparameter
		}
	}
}
