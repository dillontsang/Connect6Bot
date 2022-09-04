package dillonbill.connect6.net;

import dillonbill.connect6.game.Board;

public class OutputNode extends EvaluateNode{

    @Override
    public int getType() {
        return Node.OUTPUT_TYPE;
    }
	
    public Node cloneForThreads(Board b) {
    	OutputNode node = new OutputNode();
    	node.copyFrom(this);
    	return node;
    }
}
