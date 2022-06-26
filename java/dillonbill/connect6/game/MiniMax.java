import java.util.HashSet;

public class MiniMax {
	
	private BoardAI _ai1,_ai2;
	
	public MiniMax(BoardAI ai) {
		_ai1 = ai;
		_ai2 = ai;
	}
	
	public MiniMax (BoardAI ai1, BoardAI ai2) {
		_ai1 = ai1;
		_ai2 = ai2;
	}
	
	public Move minimax(Board board, int player, int depth, int turnNumber) {
		HashSet<Move> allAvailableMoves = board.allRelevantMoves();
	    if(depth == 0) {
	    	return null;
	    }
	    
	    Move bestMove = new Move();
	    Move opponentMove = new Move();
	    Move nextMove = new Move();
	    int boardScore = 0;
	    int moveScore = 0;
	    
	    if(player == 1) {
	    	moveScore = Integer.MIN_VALUE;
	    	for(Move m: allAvailableMoves) {
	    		m.setPlayer(player);
	    		board.applyMove(m);
	    		if(depth != 1) {
	    			if(turnNumber == 1) {
	    				opponentMove = minimax(board, 0, depth - 1, 0);
	    				board.applyMove(opponentMove);
	    				boardScore = _ai1.evaluate(board, 0);
			    		if (boardScore > moveScore) {
			    			bestMove = m;
			    			moveScore = boardScore;
			    		}
			    		board.unapplyMove(opponentMove);
	    			} else {
	    				nextMove = minimax(board, 1, depth, 1);
	    				board.applyMove(nextMove);
	    				boardScore = _ai1.evaluate(board, 0);
			    		if (boardScore > moveScore) {
			    			bestMove = m;
			    			moveScore = boardScore;
			    		}
			    		board.unapplyMove(nextMove);
	    			}
		    		board.unapplyMove(m);
		    		
	    		} else {
	    			boardScore = _ai1.evaluate(board, 0);
		    		if (boardScore > moveScore) {
		    			bestMove = m;
		    			moveScore = boardScore;
		    		}
		    		board.unapplyMove(m);
	    		}
	    	}
	    	return bestMove;
	    	
	    } else {
	    	moveScore = Integer.MIN_VALUE;
	    	for(Move m: allAvailableMoves) {
	    		m.setPlayer(player);
	    		board.applyMove(m);
	    		if(depth != 1) {
	    			if(turnNumber == 1) {
	    				opponentMove = minimax(board, 1, depth - 1, 0);
	    				board.applyMove(opponentMove);
	    				boardScore = _ai2.evaluate(board, 0);
	    				if (boardScore > moveScore) {
			    			bestMove = m;
			    			moveScore = boardScore;
			    		}
			    		board.unapplyMove(opponentMove);
	    			} else {
	    				nextMove = minimax(board, 0, depth, 1);
	    				board.applyMove(nextMove);
	    				boardScore = _ai2.evaluate(board, 0);
	    				if (boardScore > moveScore) {
			    			bestMove = m;
			    			moveScore = boardScore;
			    		}
			    		board.unapplyMove(nextMove);
	    			}
		    	
		    		board.unapplyMove(m);
	    		} else {
	    			boardScore = _ai2.evaluate(board, 0);
		    		if (boardScore > moveScore) {
		    			bestMove = m;
		    			moveScore = boardScore;
		    		}
		    		board.unapplyMove(m);
	    		}
	    		/* boardScore = _ai.evaluate(board, 0);
	    		if (boardScore > moveScore) {
	    			bestMove = m;
	    			moveScore = boardScore;
	    		}
	    	
	    		board.unapplyMove(m);
	    		board.unapplyMove(opponentMove);
	    		board.unapplyMove(nextMove); */
	    	}
	    	return bestMove;
	    	
	    }
	}
}
