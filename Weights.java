import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weights {
	private double _weights[];
	private Map<Node,Map<Node,Integer>> _weightMap;

	
	public void write (OutputStream os) {
		
	}
	
	public void read (InputStream os) {
		
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
		for(int i = 0; i < totalSize; i++) {
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
