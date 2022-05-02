
public class InputNode extends EvaluateNode {
	private Board _board;
	private int _row;
	private int _col;
	
	public InputNode(Board board, int r, int c) {
		_board = board;
		_row = r;
		_col = c;
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
	
}
