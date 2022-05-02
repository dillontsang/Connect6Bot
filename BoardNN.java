
public class BoardNN implements BoardAI {
	Board _board;
	  Network _network;
	  Node  _outputNode;

		public BoardNN (Board board) {
			// just something to play around with
			Layer inputLayer = new Layer(0);
			Layer h1Layer = new Layer(10);
			// Layer h2Layer = new Layer();
			Layer outputLayer = new Layer(0);
	     //copy over from Network
	     _board = board;
	     for (int i = 0; i != _board.getWidth(); i++) {
	     	 for (int j = 0; j != _board.getHeight(); j++) {
	          inputLayer.addNode (new InputNode(_board,i,j));
	       }
	     }
	     _outputNode = new OutputNode();
	     outputLayer.addNode(_outputNode);
	     inputLayer.connectLayer(h1Layer);
	     h1Layer.connectLayer(outputLayer);
	    //  h2Layer.connectLayer(outputLayer);
	     _network = new Network ();
	     _network.buildNodeList(_outputNode);
	  }
	  
	  public int evaluate(Board b, int player) {
	  	_network.evaluateNetwork();
	    return player * (int) Math.round(_outputNode.getValue()) * 10000;
	  }
}
