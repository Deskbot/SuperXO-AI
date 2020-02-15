/*
 * Identical to Agent1Shallow except that it
 * does not substract the difference between the worth of the overall board for both players
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.Map;
import java.util.Set;

import static java.lang.Double.*;

public class Agent2Shallow extends Agent {

	public Agent2Shallow(Player symbol, int depth) {
		super(depth, symbol);
	}

	public Move chooseMove(Game game) {
		if (game.getTurnPlayer() != this.symbol) return null;

		GameTree gt = new GameTree(game);
		double topScore = 0.0;
		double moveScore;
		Move topMove = null;

		for (Move move : gt.getEdges()) {
			moveScore = this.minimax.getValue(gt.getChild(move), this.depth, false); //min due to this being the opponent's move
			if (moveScore > topScore || topMove == null) {
				topScore = moveScore;
				topMove = move;
			}
		}

		return topMove;
	}

	protected double heuristic(Game g) {
		if (g.isDraw())
			return 0.0;

		Player winner = g.getWinner();
		if (winner == null) {
			// if checking how good for the opp, that is negative how good for us
			double goodOrBadMult = g.getTurnPlayer() == this.symbol ? 1.0 : -1.0;
			return this.boardWorth(g.getBoard(), g.getTurnPlayer()) * goodOrBadMult;
		} else if (winner == this.symbol)
			return POSITIVE_INFINITY;
		else
			return NEGATIVE_INFINITY; // opponent
	}

	private double boardWorth(Board board, Player player) {
		double util = 0.0;

		double maxRestOfTrioUtil, firstofTrioUtil, restOfTrioUtil;
		Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
		PosDuo[] pdArr;
		Set<Position> winDuosSet = winDuos.keySet();

		for (Position p : winDuosSet) {
			firstofTrioUtil = this.gridWorth(board.getChild(p), player);

			if (firstofTrioUtil == 0.0) continue;

			pdArr = winDuos.get(p);

			//get the max util that can be caused by choosing p
			maxRestOfTrioUtil = 0.0;
			for (PosDuo pd : pdArr) {
				restOfTrioUtil = this.gridWorth(board.getChild(pd.first), player) * this.gridWorth(board.getChild(pd.second), player);
				maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
			}

			util += firstofTrioUtil * maxRestOfTrioUtil;
		}

		return util / winDuosSet.size(); //should be divide by 9.0, which is the number of positions in a grid
	}

	private double gridWorth(Grid grid, Player player) {
		Player owner = grid.getOwner();

		if (owner == null) return 0.5;
		if (owner == player) return 1.0;
		return 0.0; //opponent of player
	}
}
