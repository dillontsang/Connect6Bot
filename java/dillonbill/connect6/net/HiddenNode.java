package dillonbill.connect6.net;

import dillonbill.connect6.game.Board;

public class HiddenNode extends EvaluateNode{

    @Override
    public int getType() {
        return Node.HIDDEN_TYPE;
    }

    public Node cloneForThreads (Board b) {
    	HiddenNode answer = new HiddenNode();
    	answer.copyFrom (this);
    	return answer;
    }
}
