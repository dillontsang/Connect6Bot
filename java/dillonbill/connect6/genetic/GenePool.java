package dillonbill.connect6.genetic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dillonbill.connect6.net.Network;
import dillonbill.connect6.net.Weights;

public class GenePool {
	private List<Weights> _weights;
	private List<Integer> _weightScores;
	
	private class Pair {
		public Weights _weights;
		public Integer _weightScores;
	}
	private List<Pair> sortedPool;
	private int _totalScore;
	
	public static int NUMBER_OF_CROSSES = 1;
	public static int EXPECTATION_OF_ERROR = 1;
	
	public GenePool (int count, Weights weightsToClone) {
		_weights = new ArrayList<>(count);
		_weightScores = new ArrayList<>(count);
		for (int i = 0; i!= count; i++) {
			_weightScores.add(0);
			_weights.add(weightsToClone.clone());
			_weights.get(i).randomize(5.0);
		}
	}
	
	public void sortPool() {
		sortedPool = new ArrayList<>(_weights.size());
		_totalScore = 0;
		for (int i=0; i != _weights.size();i++) {
			Pair p =  new Pair();
			p._weights = _weights.get(i);
			p._weightScores = _weightScores.get(i);
			_totalScore += p._weightScores;
			sortedPool.add(p);
		}
		sortedPool.sort((Pair p1,Pair p2)-> p2._weightScores.compareTo(p1._weightScores));
		_weights = new ArrayList<> (_weights.size());
		_weightScores = new ArrayList<> (_weights.size());
		for (Pair p: sortedPool) {
			_weights.add(p._weights);
			_weightScores.add(p._weightScores);
		}
	}

	public int getPreferedGenes () {
		int randVal = (int)(Math.random()*_totalScore);
		for (int i = 0;i != _weights.size();i++) {
			if (_weightScores.get(i) > randVal) {
				return i;
			}
			randVal -= _weightScores.get(i);
		}
		throw new RuntimeException();
	}
	
	public void evolve () {
		var newWeights = new ArrayList<Weights> (_weights.size());
		for (int i = 0; i != _weights.size(); i++) {
			int a = getPreferedGenes();
			int b = getPreferedGenes();
			newWeights.add(merge(a,b));
			_weightScores.add(0);
		}
	}
	
	public int numberOfGenes() {
		return _weights.size();
	}
	
	public void resetScores() {
		for (int i = 0; i != _weightScores.size(); i++) {
			_weightScores.set(i, 0);
		}
	}
	
	public Weights getWeights (int i) {
		return _weights.get(i);
	}
	
	public Integer getScore (int i) {
		return _weightScores.get(i);
	}
	
	public void incrementScore (int i) {
		_weightScores.set (i,1+_weightScores.get(i));
	}
	
	public void decrementScore (int i) {
		_weightScores.set (i,_weightScores.get(i)-1);
	}
	
	public Weights merge (int i, int j) {
		Weights w = _weights.get(i).clone();
		
		List<Integer> crossPoints = new ArrayList<> (NUMBER_OF_CROSSES);
		for (int k=0; k != NUMBER_OF_CROSSES; k++) {
			int val = (int) Math.floor(Math.random()*w.getWeights().length);
			crossPoints.add(val);
		}
		Collections.sort(crossPoints);
		int lastSpot = 0;
		int side = i;
		for (Integer curCross: crossPoints) {
			for (int k=lastSpot; k != curCross; k++) {
				w.getWeights()[k] = _weights.get(side).getWeights()[k];
			}
			side = (side == i) ? j:i;
			lastSpot = curCross;
		}
		
		double transcriptionErrorProbability = 1.0/(EXPECTATION_OF_ERROR * w.getWeights().length);
		for (int k=0;i!=w.getWeights().length;i++) {
			if (Math.random() < transcriptionErrorProbability) {
				w.getWeights()[k] = Math.random()*4.0 - 2.0;  // TODO:  Formalize the randomness
			}
		}
		
		return w;
	}
	
	public void writeState (String filename) {
		try {
			DataOutputStream dos = new DataOutputStream (new FileOutputStream (filename));
		
			dos.writeInt(_weights.size());
			for (int i = 0; i != _weights.size(); i++ ) {
				dos.writeInt(_weightScores.get(i));
				_weights.get(i).writeWeights(dos);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void readState (String filename, Network n) {
		try {
			DataInputStream dos = new DataInputStream (new FileInputStream (filename));
			
			int size = dos.readInt();
			_weights = new ArrayList<> (size);
			_weightScores = new ArrayList<> (size);
			for (int i = 0; i != size; i++) {
				_weightScores.set(i,dos.readInt());
				Weights w = new Weights ();
				w.readWeights(dos, n);
				_weights.set(i,w);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
