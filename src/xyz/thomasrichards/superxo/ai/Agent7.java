/*
 * This agent counts the number of lines that are 1 away, 2 away, 3 away from winning.
 * And considers more 1s to outweigh any number of 2s and 3s
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.Trio;
import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.util.stream.Collectors.toList;

public class Agent7 extends Agent {

	public Agent7(Player symbol, int depth) {
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
		return this.cellWorth(b, this.symbol) - this.cellWorth(b, this.symbol.opponent());
	}

	private double cellWorth(Board board, Player player) {
		Optional<Double> max = AbsGrid.winTrios.stream()
				.map(line -> new Trio<>(
						cellWorth(board.getChild(line.first), player),
						cellWorth(board.getChild(line.second), player),
						cellWorth(board.getChild(line.third), player)))
				.map(line -> line.first * line.second * line.third)
				.max(Double::compare);

		return max.orElse(0d);
	}

	private <C extends Cell> double cellWorth(Grid grid, Player player) {
		List<Long> collect = AbsGrid.winTrios.stream()
				.map(line -> new Trio<>(
						grid.getChild(line.first).getOwner(),
						grid.getChild(line.second).getOwner(),
						grid.getChild(line.third).getOwner()
				))
				.map(line -> {
					ArrayList<Player> lineByPlayers = new ArrayList<>();
					lineByPlayers.add(line.first);
					lineByPlayers.add(line.second);
					lineByPlayers.add(line.third);
					return lineByPlayers;
				})
				.filter(line -> !line.contains(player.opponent()))
				.map(line -> line.stream()
						.filter(player::equals)
						.count())
				.collect(toList());

		int oneAways = 0;
		int twoAways = 0;
		int threeAways = 0;

		for (Long isMine : collect) {
			if (isMine == 2) {
				oneAways++;
			} else if (isMine == 1) {
				twoAways++;
			} else if (isMine == 0) {
				threeAways++;
			}
		}

		return oneAways * 4 + twoAways * 2 + threeAways;
	}
}
