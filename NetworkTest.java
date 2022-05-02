
public class NetworkTest extends Network {
	public static void main(String[] args) {
		evaluateOneNode();
		// evaluateTrivialNetwork();
		// test2Board();
		test3Board();
	}
	
	private static void evaluateOneNode() {
		OutputNode outputNode = new OutputNode();
		outputNode.reset();
		if(Math.abs(outputNode.getValue() - OutputNode.NEUTRAL) > 0.00001) {
			throw new RuntimeException("Failed reset");
		}
	}
	
	private static void evaluateTrivialNetwork() {
		Board board = new Board(1, 1);
		InputNode iNode = new InputNode(board, 0, 0);
		OutputNode oNode = new OutputNode();
		iNode.addDownstreamNode(oNode);
		Network network = new Network();
		if(oNode.getUpstreamNodes().get(0) != iNode) {
			throw new RuntimeException("Failed addDownstreamNode");
		}
		network.buildNodeList(oNode);
		Weights weights = network.getWeights();
		if(network.getWeights().getWeights().length != 3) {
			throw new RuntimeException("Failed buildNodeList");
		}
		weights.set(iNode, iNode, 1);
		weights.set(iNode, oNode, 2);
		weights.set(oNode, oNode, 3);
		if(Math.abs(weights.getWeights()[0] - 1.0 ) > 0.00001) {
			throw new RuntimeException("Failed setWeights");
		}
		if(Math.abs(weights.getWeights()[1] - 2.0) > 0.00001) {
			throw new RuntimeException("Failed setWeights");
		}
		if(Math.abs(weights.getWeights()[2] - 3.0) > 0.00001) {
			throw new RuntimeException("Failed setWeights");
		}
		
		if(Math.abs(iNode.getValue() - 0.0) > 0.00001) {
			throw new RuntimeException("Failed getValue");
		}
		
		board.applyMove(new Move(0, 0, 0));
		
		if(Math.abs(iNode.getValue() + 1.0) > 0.00001) {
			throw new RuntimeException("Failed getValue");
		}
		
		board.applyMove(new Move(0, 0, 1));
		
		if(Math.abs(iNode.getValue() - 1.0) > 0.00001) {
			throw new RuntimeException("Failed getValue");
		}
		
		network.evaluateNetwork();
		
		if(Math.abs(oNode.getValue() - OutputNode.squash(2.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork");
		}

		board.applyMove(new Move(0, 0, 0));
		
		network.evaluateNetwork();
		
		if(Math.abs(oNode.getValue() - OutputNode.squash(-2.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork " + oNode.getValue());
		}
		
		board.unapplyMove(new Move(0, 0, 0));
		
		network.evaluateNetwork();
		
		if(Math.abs(oNode.getValue() - OutputNode.squash(0.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork " + oNode.getValue());
		}
		
		
	}
	
	private static void test2Board() {
		Board board = new Board(1, 2);
		InputNode iNode = new InputNode(board, 0, 0);
		InputNode iNode2 = new InputNode(board, 0, 1);
		OutputNode oNode = new OutputNode();
		iNode.addDownstreamNode(oNode);
		iNode2.addDownstreamNode(oNode);
		
		Network network = new Network();
		network.buildNodeList(oNode);
		network.getWeights().resetWeights(1.0);
		
		board.applyMove(new Move(0, 0, 1));
		network.evaluateNetwork();
		if(Math.abs(oNode.getValue() - OutputNode.squash(1.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork" + oNode.getValue());
		}
		
		board.applyMove(new Move(0, 1, 1));
		network.evaluateNetwork();
		if(Math.abs(oNode.getValue() - OutputNode.squash(2.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork"  + oNode.getValue());
		}
		
		board.applyMove(new Move(0, 0, 0));
		network.evaluateNetwork();
		if(Math.abs(oNode.getValue() - OutputNode.squash(0.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork" + oNode.getValue());
		}
		
		Weights weights = network.getWeights();
		
		weights.set(iNode, oNode, 2);
		if(Math.abs(oNode.getValue() - OutputNode.squash(0.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork" + oNode.getValue());
		}
		
		
	}
	
	public static void test3Board() {
		Board board = new Board(1, 1);
		InputNode iNode = new InputNode(board, 0, 0);
		Layer h1Layer = new Layer(3);
		OutputNode oNode = new OutputNode();
		
		h1Layer.addUpstreamNode(iNode);
		h1Layer.addDownstreamNode(oNode);
		
		Network network = new Network();
		network.buildNodeList(oNode);
		network.getWeights().resetWeights(1.0);
		
		board.applyMove(new Move(0, 0, 1));
		network.evaluateNetwork();
		if(Math.abs(oNode.getValue() - OutputNode.squash(3)) > 0.00001) {
			throw new RuntimeException("Failed evaluate Network" + oNode.getValue());
		}
		
		Weights weights = network.getWeights();
		h1Layer.getNode(0).setThreshold(10.0);
		
		network.evaluateNetwork();
		if(Math.abs(oNode.getValue() - OutputNode.squash(2.0)) > 0.00001) {
			throw new RuntimeException("Failed evaluateNetwork");
		}
		
		
	}
	
	
	
	
}
