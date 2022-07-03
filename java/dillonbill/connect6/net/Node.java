package dillonbill.connect6.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dillonbill.connect6.game.Board;

public interface Node {

	public static int INPUT_TYPE = 0;
	public static int HIDDEN_TYPE = 1;
	public static int OUTPUT_TYPE = 2;
	
	public void evaluate();
	
	public void fire(double d);
	
	public void addDownstreamNode (Node node);
	
	public void addUpstreamNode (Node node);
	
	public void reset();
	
	public void setWeights (Weights weights);
	
	public void setThreshold (double d);
	
	public double getValue();
	
	public List<Node> getDownstreamNodes();
	
	public List<Node> getUpstreamNodes();

	public int getType();

	static Node readNode(DataInputStream is, Board board, Map<Integer,Node> nodeMap) throws IOException {
		int t = is.readInt();
		Node n = null;
		switch (t) {
			case INPUT_TYPE: 	n = new InputNode(board,is);
								break;
			case OUTPUT_TYPE: 	n = new OutputNode();
								break;
			case HIDDEN_TYPE: 	n = new HiddenNode();
								break;
			default: throw new IOException("Don't understand node type " + t);
		}
		int toRead = is.readInt();
		for (int i = 0 ; i != toRead; i++) {
			n.addUpstreamNode(nodeMap.get(is.readInt()));
		}
		return n;
	}

	static public void writeNode(DataOutputStream os, Node n, Map<Node,Integer> nodeMap) throws IOException {
		os.writeInt(n.getType());
		os.writeInt(n.getUpstreamNodes().size());
		for (Node m: n.getUpstreamNodes()) {
			os.writeInt(nodeMap.get(m));
		}
	}
}

