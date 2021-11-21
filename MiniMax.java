
public class MiniMax {
	private BoardAI _ai = new BoardAI();
	
	public Move minimax(Board board, int player, int depth) {
		Move availableMoves[] = board.allAvailableMoves();
	    if(depth == 0) {
	    	return null;
	    }
	    
	    Move bestMove = new Move();
	    Move opponentMove = new Move();
	    int boardScore = 0;
	    int moveScore = 0;
	    int count = 0;
	    
	    for(int a = 0; a < availableMoves.length; a++) {
	    	if(board.allAvailableMoves()[a] != null) {
	    		count++;
	    	}
	    }
	    
	    
	    if(player == '1') {
	    	moveScore = Integer.MIN_VALUE;
	    	for(int i = 0; i < count; i++) {
	    		availableMoves[i].setPlayer(0);
	    		board.applyMove(availableMoves[i]);
	    		if(depth != 1) {
	    			opponentMove = minimax(board, 0, depth - 1);
	    			board.applyMove(opponentMove);
	    		}
	    		boardScore = _ai.evaluate(board, 1);
	    		if (boardScore > moveScore) {
	    			bestMove = availableMoves[i];
	    			moveScore = boardScore;
	    		}
	    	
	    		board.unapplyMove(availableMoves[i]);
	    		board.unapplyMove(opponentMove);
	    	}
	    	return bestMove;
	    	
	    } else {
	    	moveScore = Integer.MAX_VALUE;
	    	for(int i = 0; i < count; i++) {
	    		availableMoves[i].setPlayer(1);
	    		board.applyMove(availableMoves[i]);
	    		if(depth != 1) {
	    			opponentMove = minimax(board, 1, depth - 1);
	    			board.applyMove(opponentMove);
	    		}
	    		boardScore = _ai.evaluate(board, 0);
	    		if (boardScore < moveScore) {
	    			bestMove = availableMoves[i];
	    			moveScore = boardScore;
	    		}
	    	
	    		board.unapplyMove(availableMoves[i]);
	    		board.unapplyMove(opponentMove);
	    	}
	    	return bestMove;
	    	
	    }
	}
}
