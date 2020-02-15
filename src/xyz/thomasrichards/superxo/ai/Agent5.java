/*
 * This agent uses the weighting of each trio of ways of winning as a heuristic
 * to determine how good the current game state is for a given player
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.Trio;
import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public class Agent5 extends Agent {

	public Agent5(Player symbol, int depth) {
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
			return this.cellWorth(g.getBoard());
		if (winner == this.symbol)
			return POSITIVE_INFINITY;

		return NEGATIVE_INFINITY; // opponent
	}

	private double cellWorth(Board b) {
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

		Set<Trio<Position>> winTrios = AbsGrid.winTrios;

		for (Trio<Position> trio: winTrios) {
			double trioWorth = this.cellWorth(g.getChild(trio.first), player)
					- this.cellWorth(g.getChild(trio.second), player)
					- this.cellWorth(g.getChild(trio.third), player);

			util += trioWorth;
		}

		return util / winTrios.size();
	}
}
