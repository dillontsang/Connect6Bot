package dillonbill.connect6.net;

import dillonbill.connect6.game.*;

public class BoardNN implements BoardAI {
	Board _board;
	public Network _network;
	Node _outputNode;

	public BoardNN(Board board) {
		// just something to play around with
		Layer inputLayer = new Layer(0);
		Layer h1Layer = new Layer(10);
		Layer h2Layer = new Layer(10);
		Layer outputLayer = new Layer(0);
		// copy over from Network
		_board = board;
		for (int i = 0; i != _board.getWidth(); i++) {
			for (int j = 0; j != _board.getHeight(); j++) {
				inputLayer.addNode(new InputNode(_board, i, j));
			}
		}
		_outputNode = new OutputNode();
		outputLayer.addNode(_outputNode);
		inputLayer.connectLayer(h1Layer);
		h2Layer.connectLayer(outputLayer);
		_network = new Network();
		_network.buildNodeList(_outputNode);
	}

	public BoardNN(Board board, Network net) {
		_board = board;
		_network = net;
		_outputNode = net.getOutputNode();
	}

	public int evaluate(Board b, int player) {
		_network.evaluateNetwork();
		return player * (int) Math.round(_outputNode.getValue()) * 10000;
	}
}
