/*
 * That uses the liklihood of winning each empty cell in a grid
 * and multiplying it by the max utility of each trio it is a part of.
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.*;

public class Agent6Shallow extends Agent {

	public Agent6Shallow(Player symbol, int depth) {
		super(depth, symbol);
	}

	public Move chooseMove(Game game) {
		if (game.getTurnPlayer() != this.symbol) return null;

		GameTree gt = new GameTree(game);
		double topScore = 0.0;
		double tmp;
		List<Move> topMoves = new ArrayList<>();

		for (Move move : gt.getEdges()) {
			tmp = this.minimax.getValue(gt.getChild(move), this.depth, false); //min due to this being the opponent's move
			if (tmp > topScore || topMoves.isEmpty()) {
				topScore = tmp;
				topMoves = new ArrayList<>();
				topMoves.add(move);

			} else if (tmp == topScore) {
				topMoves.add(move);
			}
		}

		return topMoves.get((int) (Math.random() * topMoves.size()));
	}

	protected double heuristic(Game g) {
		if (g.isDraw())
			return 0.0;

		Player winner = g.getWinner();
		if (winner == null)
			return this.boardWorth(g.getBoard());
		if (winner == this.symbol)
			return POSITIVE_INFINITY;

		return NEGATIVE_INFINITY; // opponent
	}

	private double boardWorth(Board b) {
		return this.boardWorth(b, this.symbol) - this.boardWorth(b, this.symbol.opponent());
	}

	private double boardWorth(Board board, Player player) {
		Player owner = board.getOwner();

		if (owner == player) return 1.0;
		if (owner == player.opponent()) return 0;

		double util = 0.0;

		double maxRestOfTrioUtil, firstofTrioUtil, restOfTrioUtil;
		Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
		Set<Position> winDuosSet = winDuos.keySet();
		int cellsLookedAt = 0;

		for (Position p : winDuosSet) {
			if (board.getChild(p).getOwner() != null) continue;

			cellsLookedAt++;

			firstofTrioUtil = this.gridWorth(board.getChild(p), player);

			if (firstofTrioUtil == 0.0) continue;

			//get the max util that can be caused by choosing p
			maxRestOfTrioUtil = 0.0;
			for (PosDuo pd : winDuos.get(p)) {
				restOfTrioUtil = this.gridWorth(board.getChild(pd.first), player) * this.gridWorth(board.getChild(pd.second), player);
				maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
			}

			util += firstofTrioUtil * maxRestOfTrioUtil;
		}

		return cellsLookedAt == 0
				? 0.5
				: util / cellsLookedAt;
	}

	private double gridWorth(Grid grid, Player player) {
		Player owner = grid.getOwner();

		if (owner == null) return 0.5;
		if (owner == player) return 1.0;
		return 0.0; //opponent of player
	}
}
