package dillonbill.connect6.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import dillonbill.connect6.game.Board;


public class Network {
	
	public Network() {
		_nodes = new LinkedList<Node> ();
	}
	
	List<Node> _nodes;
	Weights _weights;


	public void writeNet(String filename) throws IOException {
		DataOutputStream os = new DataOutputStream(new FileOutputStream(filename));
		var nodeToInteger = new HashMap<Node,Integer>();
		os.writeInt(_nodes.size());
		for (int i = 0; i != _nodes.size(); i++) {
			Node n = _nodes.get(i);
			nodeToInteger.put(n,i);
		    Node.writeNode(os, n, nodeToInteger);	
		}
	}

	public void readNet(String filename, Board board) throws IOException {
		DataInputStream is = new DataInputStream(new FileInputStream(filename));
		var integerToNode = new HashMap<Integer,Node>();
		int num = is.readInt();
		_nodes = new ArrayList<>(num);
		for (int i = 0; i != num; i++) {
			Node n = Node.readNode(is, board, integerToNode);
			integerToNode.put(i,n);
			_nodes.add(n);
		}
		buildNodeList(_nodes.get(0));
	}
	
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
	
	public OutputNode getOutputNode() {
		return (OutputNode) _nodes.get(_nodes.size()-1);
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
	
	public Weights getWeights() {
		return _weights;
	}
	
	public void setWeights(Weights weights) {
		_weights = weights;
	}
}

