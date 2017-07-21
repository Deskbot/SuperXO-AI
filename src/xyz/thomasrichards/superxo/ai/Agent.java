package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.Game;
import xyz.thomasrichards.superxo.game.Move;
import xyz.thomasrichards.superxo.game.Player;

import java.util.function.Function;

public abstract class Agent {
	final int depth;
	private final Function<Game, Double> heuristic;
	final Minimax<Move, Game> minimax;
	final Player symbol;

	Agent(int depth, Player symbol) {
		this.depth = depth;
		this.symbol = symbol;
		this.heuristic = this.defineHeuristic();
		this.minimax = new Minimax<>(this.heuristic);
	}

	public abstract Move chooseMove(Game game);

	protected abstract Function<Game, Double> defineHeuristic();
}
