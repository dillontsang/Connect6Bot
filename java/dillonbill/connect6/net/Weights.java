package dillonbill.connect6.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weights {
	private double _weights[];
	private Map<Integer,Map<Integer,Integer>> _weightMap;

	
	public void writeWeights (DataOutputStream os) throws IOException {
		os.writeInt(_weights.length);
		for (int i=0; i != _weights.length; i++) {
			os.writeDouble(_weights[i]);
		}
	}
	
	public void readWeights (DataInputStream os, Network n) throws IOException {
		int size = os.readInt();
		_weights = new double [size];
		for (int i =0 ; i != size; i++) {
			_weights[i] = os.readDouble();
		}
	}
	
	public void setWeightMap (Map<Integer,Map<Integer,Integer>> m) {
		_weightMap = m;
	}
	
	public void checkForMap () {
		if (_weightMap == null) {
			throw new RuntimeException ("Somehow weightMap isn't there");
		}
	}


	public Weights clone () {
		Weights w = new Weights();
		w._weights = new double[_weights.length];
		for (int i = 0 ; i != _weights.length; i++) {
			w._weights[i] = _weights[i];
		}
		
		w._weightMap = _weightMap; 
		
		return w;
	}
	
	public double get(Node upstreamNode, Node downstreamNode) {
		int index = _weightMap.get(upstreamNode.getID()).get(downstreamNode.getID());
		return _weights[index];
		// return _weights[_weightMap.get(upstreamNode).get(downstreamNode)];
	}
	
	public void set (Node upstreamNode, Node downstreamNode, double d) {
		_weights[_weightMap.get(upstreamNode.getID()).get(downstreamNode.getID())] = d;
	}
	
	public void allocateWeights (List <Node> nodes) {
		int totalSize = 0;
		
		_weightMap = new HashMap<>();
		for (Node n: nodes) {
			Map<Integer,Integer> weightMap = new HashMap<>();
			weightMap.put(n.getID(),totalSize);
			_weightMap.put(n.getID(),weightMap);
			totalSize++;
			for (Node m: n.getDownstreamNodes()) {
				weightMap.put(m.getID(),totalSize++);
			}
		}
		_weights = new double [totalSize];
		
	}
	
	public void randomize(List<Node> nodes, double max) {
		allocateWeights(nodes);
		randomize(max);
	}
	
	public void randomize(double max) {
		for(int i = 0; i < _weights.length; i++) {
			_weights[i] = 2.0 * Math.random() * max - max;
		}
	}
	
	public double[] getWeights() {
		return _weights;
	}
	
	public void resetWeights(double d) {
		for(int i = 0; i < _weights.length; i++) {
			_weights[i] = d;
		}
	}
	
}
