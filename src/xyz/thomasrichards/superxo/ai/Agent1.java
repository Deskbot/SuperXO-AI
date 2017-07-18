package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.max;

public class Agent1 extends Agent {

	public Agent1(Player symbol, int depth) {
		super(depth, symbol);
	}

	public Move chooseMove(Game game) {
		GameTree gt = new GameTree(game);
		double topScore = 0.0;
		double tmp;
		Move topMove = null;
		
		for (Move move : gt.getEdges()) {
			tmp = this.minimax.getValue(gt.getChild(move), this.depth, true);
			if (tmp > topScore) {
				topScore = tmp;
				topMove = move;
			}
		}

		return topMove;
	}

	protected Function<Game, Double> defineHeuristic() {
		return g -> {
			Player winner = g.getWinner();

			if (winner == null) return this.cellWorth(g.getBoard());
			else if (winner == this.symbol) return POSITIVE_INFINITY;
			else return NEGATIVE_INFINITY; //opponent
		};
	}

	//Not sure how to do this better as (AbsGrid.getChild) returns (C extends Cell), so even AbsGrid children will always be 1.0 or 0.0
	private Double cellWorth(Cell c) {
		if (c instanceof AbsGrid<?>) {
			AbsGrid<?> ag = (AbsGrid<?>) c;
			return this.cellWorth(ag, this.symbol) - this.cellWorth(ag, this.symbol.opponent());
		}

		return this.cellWorth(c, this.symbol) - this.cellWorth(c, this.symbol.opponent());
	}

	private Double cellWorth(Cell c, Player player) {
		return c.getOwner() == player ? 1.0 : 0.0;
	}

	/*
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
	 * for each cell find the highest chance of winning assuming you have that cell, then multiply it by that cell's own liklihood
	 * average those chances as all cells are assumed to be equally likley to be chosen
	 */
	private double cellWorth(AbsGrid<?> g, Player player) {
		double util = 0.0;

		Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
		PosDuo[] pda;
		double maxRestOfTrioUtil;
		double firstofTrioUtil;
		double restOfTrioUtil;
		Set<Position> winDuosSet = winDuos.keySet();

		for (Position p : winDuosSet) {
			firstofTrioUtil = this.cellWorth(g.getChild(p), player);

			if (firstofTrioUtil == 0.0) continue;

			pda = winDuos.get(p);

			//get the max util that can be caused by choosing p
			maxRestOfTrioUtil = 0.0;
			for (PosDuo pd : pda) {
				restOfTrioUtil = this.cellWorth(g.getChild(pd.first), player) * this.cellWorth(g.getChild(pd.second), player);
				maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
			}

			util += firstofTrioUtil * maxRestOfTrioUtil;
		}

		return util / winDuosSet.size(); //should be divide by 9.0, which is the number of positions in a grid
	}
}
