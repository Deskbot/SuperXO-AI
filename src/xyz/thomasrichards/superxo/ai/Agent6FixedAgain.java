/*
 * This agent uses the weighting of each trio of ways of winning as a heuristic
 * to determine how good the current game state is for a given player
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.*;

public class Agent6FixedAgain extends Agent {

	public Agent6FixedAgain(Player symbol, int depth) {
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

	protected Double heuristic(Game g) {
		if (g.isDraw())
			return 0.0;

		Player winner = g.getWinner();
		if (winner == null)
			return this.cellWorth(g.getBoard());
		if (winner == this.symbol)
			return POSITIVE_INFINITY;

		return NEGATIVE_INFINITY; // opponent
	}

	private double cellWorth(Board b) {
		return this.cellWorth(b, this.symbol);
	}

	private <C extends Cell> double cellWorth(C c, Player player) {
		if (!(c instanceof AbsGrid)) {
			Player owner = c.getOwner();

			if (owner == null) return 0.5;
			if (owner == player) return 1.0;
			return 0.0; //opponent of player
		}

		AbsGrid<?> grid = (AbsGrid<?>) c;

		Player owner = grid.getOwner();

		if (owner == player) return 1.0;
		if (owner == player.opponent()) return 0;

		double util = 0.0;

		double maxRestOfTrioUtil, firstofTrioUtil, restOfTrioUtil;
		Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
		Set<Position> winDuosSet = winDuos.keySet();
		int cellsLookedAt = 0;

		for (Position p : winDuosSet) {
			if (grid.getChild(p).getOwner() != null) continue;

			cellsLookedAt++;

			firstofTrioUtil = this.cellWorth(grid.getChild(p), player);

			if (firstofTrioUtil == 0.0) continue;

			//get the max util that can be caused by choosing p
			maxRestOfTrioUtil = 0.0;
			for (PosDuo pd : winDuos.get(p)) {
				restOfTrioUtil = this.cellWorth(grid.getChild(pd.first), player) * this.cellWorth(grid.getChild(pd.second), player);
				maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
			}

			util += firstofTrioUtil * maxRestOfTrioUtil;
		}

		return cellsLookedAt == 0
				? 0.5
				: util / cellsLookedAt;
	}
}
