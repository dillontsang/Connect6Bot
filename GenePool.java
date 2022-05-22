import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenePool {
	private List<Weights> _weights;
	private List<Integer> _weightScores;
	
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
			crossPoints.set(k,val);
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
