package dillonbill.connect6.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weights {
	private double _weights[];
	private Map<Node,Map<Node,Integer>> _weightMap;

	
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

	public void writeWeightMap (DataOutputStream os, Map<Node,Integer> nodeMap) throws IOException {
		os.writeInt(_weightMap.keySet().size());
		for(Node n: _weightMap.keySet()) {
			os.writeInt(nodeMap.get(n));
			Map<Node,Integer> curRow = _weightMap.get(n);
			os.writeInt(curRow.keySet().size());
			for(Node m: curRow.keySet()) {
				os.writeInt(nodeMap.get(n));
				os.writeInt(curRow.get(m));
			}
		}
	}

	public void readWeightMap (DataInputStream is, Map<Integer,Node> nodeMap) throws IOException {
		int numNodes = is.readInt();
		for (int i = 0; i != numNodes; i++) {
			Node n = nodeMap.get(is.readInt());
			_weightMap.put(n,new HashMap<Node,Integer>());
			int numCols = is.readInt();
			for (int j = 0; j != numCols; j++) {
				Node m = nodeMap.get(is.readInt());
				_weightMap.get(n).put(m,is.readInt());
			}
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
		int index = _weightMap.get(upstreamNode).get(downstreamNode);
		return _weights[index];
		// return _weights[_weightMap.get(upstreamNode).get(downstreamNode)];
	}
	
	public void set (Node upstreamNode, Node downstreamNode, double d) {
		_weights[_weightMap.get(upstreamNode).get(downstreamNode)] = d;
	}
	
	public void randomize(List<Node> nodes, double max) {
		int totalSize = 0;
	
		_weightMap = new HashMap<>();
		for (Node n: nodes) {
			Map<Node,Integer> weightMap = new HashMap<>();
			weightMap.put(n,totalSize);
			_weightMap.put(n,weightMap);
			totalSize++;
			for (Node m: n.getDownstreamNodes()) {
				weightMap.put(m,totalSize++);
			}
		}
		_weights = new double [totalSize];
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
