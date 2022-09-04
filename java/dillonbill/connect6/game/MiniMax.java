package dillonbill.connect6.game; 
import java.util.HashSet;

import dillonbill.connect6.game.Connect6Game.Stone;
import dillonbill.connect6.net.*;
public class MiniMax {
	
	private BoardAI _blackAI,_whiteAI;
	
	public MiniMax(BoardAI ai) {
		_blackAI = ai;
		_whiteAI = ai;
	}
	
	public MiniMax (BoardAI blackAI, BoardAI whiteAI) {
		_blackAI = blackAI;
		_whiteAI = whiteAI;
	}
	
	public BoardAI getBoardAI (Stone stone) {
		if (stone == Stone.BLACK) {
			return _blackAI;
		}
		return _whiteAI;
	}
	
	public Move minimax(Board board, Connect6Game.Stone player, int depth, int turnNumber) {
		return minimax(board,player,depth,turnNumber, null);
	}
	
	public Move minimax(Board board, Connect6Game.Stone player, int depth, int turnNumber, HashSet<Move> done) {
		if(depth == 0) {
	    	return null;
	    }

		HashSet<Move> allAvailableMoves = board.allRelevantMoves();
	    done = done == null ? new HashSet<>() : done;
	    
	    Move bestMove = new Move();
	    Move nextMove = new Move();
	    int bestMoveScore = Integer.MIN_VALUE;
	    
        for (Move m: allAvailableMoves) {
        	if (done.contains(m)) {
        		continue;
        	}
        	if (turnNumber == 0) {
        		done.add(m);
        	}
        	m.setPlayer(player.getValue());
        	board.applyMove(m);
        	switch (turnNumber) {
        	case 0: nextMove = minimax(board,player,depth,1,done); break;
        	case 1: nextMove = minimax(board,player.otherPlayer(),depth-1,0,null); break;
        	default: throw new RuntimeException ("?!??!?");
        	}
        	if (nextMove != null) {
        		board.applyMove(nextMove);
        	}
        	int rawScore = getBoardAI(player).evaluate(board);
        	if (player == Stone.WHITE) rawScore *= -1;
        	if (rawScore > bestMoveScore) {
        		bestMoveScore = rawScore;
        		bestMove.dup(m);
        	}
        	if (nextMove != null) {
        		board.unapplyMove(nextMove);
        	}
        	board.unapplyMove(m);
        }
        return bestMove;
	}
}
