package dillonbill.connect6.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import dillonbill.connect6.game.Board;

public class InputNode extends EvaluateNode {
	private Board _board;
	private int _row;
	private int _col;

	public InputNode(Board board, DataInputStream is) throws IOException {
		_board = board;
		_row = is.readInt();
		_col = is.readInt();
	}
	
	public Node cloneForThreads(Board b) {
		InputNode node = new InputNode(b,_row,_col);
		node.copyFrom(this);
		return node;
	}
	
	public void setBoard (Board b) {
		_board = b;
	}
	
	
	public InputNode(Board board, int r, int c) {
		_board = board;
		_row = r;
		_col = c;
	}
	
	public void writeSpecific (DataOutputStream os) throws IOException {
		os.writeInt(_row);
		os.writeInt(_col);
	}

	@Override
	public double getValue() {
		double retVal;
		switch (_board.getMove(_row,_col)) {
	    case '0': retVal = -1.0;
	    	break;
	    case '1': retVal = 1.0;
	    	break;
	    default: retVal = 0.0;
	    	break;
	    }
	    
	    return retVal;
		// return (double) _board.getMove(_row, _col);
	}
	
	public void evaluate() {
		setThreshold(-5.0);
		setAccumulator(getValue());
		super.evaluate();
	}

	@Override
	public int getType() {
		return Node.INPUT_TYPE;
	}
	
}
