import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Network {
	
	public Network() {
		_nodes = new LinkedList<Node> ();
	}
	
	List<Node> _nodes;
	Weights _weights;
	
	public void evaluateNetwork() {
		for(Node n: _nodes) {
			n.reset();
		}
		for (Node n: _nodes) {
			n.evaluate();
		}
	}
	
	public Network createAdversary () {
		Network retval = new Network();
		retval._nodes = _nodes;
		retval._weights = _weights.clone();
		return retval;
	}
	
	public List<Node> getNodes () {
		return _nodes;
	}
	
	public void buildNodeList (Node root) {
		List<Node> _activeQueue = new LinkedList<Node> ();
	    Set<Node> _coveredNodes = new HashSet<Node> ();
	    _weights = new Weights();
	    _activeQueue.add(root);
	    while (!_activeQueue.isEmpty()) {
	    	Node currentNode = _activeQueue.remove(0);
	        if (!_coveredNodes.contains(currentNode)) {
	        	boolean readyToProcess = true;
	        	for (Node n: currentNode.getUpstreamNodes()) {
	        		if (!_coveredNodes.contains(n)) {
	        			_activeQueue.add(0, n);
	        			readyToProcess = false;
	        			break;
	        		}
	        	}
	        	if (readyToProcess) {
	        		currentNode.setWeights(_weights);
	        		_nodes.add(currentNode);
	        		_coveredNodes.add(currentNode);
	        			for (Node n: currentNode.getDownstreamNodes()) {
	        				_activeQueue.add(n);
	        			}
	        	} else {
	        		_activeQueue.add(currentNode);
	        	}
	        }
	    }
	    
	    _weights.randomize(_nodes, 5.0);
	}
	
	protected Weights getWeights() {
		return _weights;
	}
	
	protected void setWeights(Weights weights) {
		_weights = weights;
	}
}

