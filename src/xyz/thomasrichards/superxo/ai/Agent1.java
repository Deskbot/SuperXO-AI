/*
 * This agent uses the weighting of each trio of ways of winning as a heuristic
 * to determine how good the current game state is for a given player
 *
 * tactic:
 * we have a fraction (between 0 and 1 inclusive) for each cell in a grid
 * the number represents its worth i.e. how close to owning the player is.
 * The usefulness of each cell depends on how easily filling it in will cause the grid to be won
 * multiplying every cell worth in a winning trio shows the liklihood of it happening
 * we can't just take an average of all products because:
 * XX_
 * __X
 * __X
 * counts 4 that are 1 away, even though only 2 cells cause the grid to be 1 away
 * we need to assume because it's a cutoff heuristic that we are choosing a random cell
 * so we need to know the liklihood that choosing a cell will cause a win, going in TR above is only worth 1 win
 * solution:
 * for each cell find the highest chance of winning involving that that cell, then multiply it by that cell's own liklihood
 * average those chances as all cells are assumed to be equally likley to be chosen
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.*;

public class Agent1 extends Agent {

	public Agent1(Player symbol, int depth) {
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
		return this.cellWorth(b, this.symbol) - this.cellWorth(b, this.symbol.opponent());
	}

	private <C extends Cell> double cellWorth(C c, Player player) {
		if (!(c instanceof AbsGrid)) {
			Player owner = c.getOwner();

			if (owner == null) return 0.5;
			if (owner == player) return 1.0;
			return 0.0; //opponent of player
		}

		AbsGrid<?> g = (AbsGrid<?>) c;

		double util = 0.0;

		double maxRestOfTrioUtil, firstofTrioUtil, restOfTrioUtil;
		Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
		PosDuo[] pdArr;
		Set<Position> winDuosSet = winDuos.keySet();

		for (Position p : winDuosSet) {
			firstofTrioUtil = this.cellWorth(g.getChild(p), player);

			if (firstofTrioUtil == 0.0) continue;

			pdArr = winDuos.get(p);

			//get the max util that can be caused by choosing p
			maxRestOfTrioUtil = 0.0;
			for (PosDuo pd : pdArr) {
				restOfTrioUtil = this.cellWorth(g.getChild(pd.first), player) * this.cellWorth(g.getChild(pd.second), player);
				maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
			}

			util += firstofTrioUtil * maxRestOfTrioUtil;
		}

		return util / winDuosSet.size(); //should be divide by 9.0, which is the number of positions in a grid
	}
}
