package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.Game;
import xyz.thomasrichards.superxo.game.Move;
import xyz.thomasrichards.superxo.game.Player;

import java.util.function.Function;

public abstract class Agent {
	protected int depth;
	protected final Function<Game, Double> heuristic;
	protected final Minimax<Game> minimax;
	protected Player symbol;

	public Agent(int depth, Player symbol) {
		this.depth = depth;
		this.symbol = symbol;
		this.heuristic = this.defineHeuristic();
		this.minimax = new Minimax<>(this.heuristic);
	}

	public abstract Move chooseMove(Game game);

	protected abstract Function<Game, Double> defineHeuristic();
}
