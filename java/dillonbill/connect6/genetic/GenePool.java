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
	
	public void showAngleStats() {
		System.out.println ("Printing stats");
		List<Double> lengths = new ArrayList<>(_weights.size());
		List<Double> angles = new ArrayList<>(_weights.size()*_weights.size());
		double mean_length = 0.0;
		double mean_angle = 0.0;
		for (Weights w: _weights) {
			double cum = 0.0;
			double len = 0.0;
			for (double d: w.getWeights()) {
				cum += d*d;
			}
			double leng = Math.pow(cum, 0.5);
			lengths.add(leng);
			mean_length += leng;
		}
		mean_length = mean_length / (double) lengths.size(); 
		for (int i = 0; i != _weights.size(); i++) {
			for (int j = 0; j != _weights.size(); j++) {
				double angle=0.0;
				for (int k = 0;k != _weights.get(i).getWeights().length;k++) {
					for(int l = 0;l != _weights.get(j).getWeights().length;l++) {
						angle += _weights.get(i).getWeights()[k]*_weights.get(j).getWeights()[l];
					}
				}
				System.out.println ("--- " + i + "  " + j);
				angle = angle / lengths.get(i) / lengths.get(j);
				angles.add(angle);
				mean_angle += angle;
			}
		}
		mean_angle = mean_angle / (double)angles.size();
		System.out.println ("Mean length: " + mean_length);
		System.out.println ("Mean angle: " + mean_angle);
		
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
		int randVal = (int)(Math.random()*(_totalScore/2));
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
		}
		_weights = newWeights;
		resetScores();
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
	
	public synchronized void incrementScore (int i) {
		_weightScores.set (i,1+_weightScores.get(i));
	}
	
	public synchronized void decrementScore (int i) {
		_weightScores.set (i,_weightScores.get(i)-1);
	}
	
	public Weights merge (int mom, int dad) {
		Weights w = _weights.get(mom).clone();
		
		List<Integer> crossPoints = new ArrayList<> (NUMBER_OF_CROSSES);
		for (int k=0; k != NUMBER_OF_CROSSES; k++) {
			int val = (int) Math.floor(Math.random()*w.getWeights().length);
			crossPoints.add(val);
		}
		crossPoints.add(w.getWeights().length);
		Collections.sort(crossPoints);
		int lastSpot = crossPoints.get(0);
		int side = dad;
		for (int k = 1; k != crossPoints.size(); k++) {
			for (int l=lastSpot; l != crossPoints.get(k); l++) {
				w.getWeights()[l] = _weights.get(side).getWeights()[l];
			}
			side = (side == mom) ? dad:mom;
			lastSpot = crossPoints.get(k);
		}
		
		double transcriptionErrorProbability = 1.0/(EXPECTATION_OF_ERROR * w.getWeights().length);
		for (int k=0;k!=w.getWeights().length;k++) {
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
				_weightScores.add(dos.readInt());
				Weights w = new Weights ();
				w.readWeights(dos, n);
				_weights.add(w);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
