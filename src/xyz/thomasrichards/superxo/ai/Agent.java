package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.Game;
import xyz.thomasrichards.superxo.game.Move;
import xyz.thomasrichards.superxo.game.Player;

public abstract class Agent {
	final int depth;
	final Minimax<Move, Game> minimax;
	final Player symbol;

	public Agent(int depth, Player symbol) {
		this.depth = depth;
		this.symbol = symbol;
		this.minimax = new Minimax<>(this::heuristic);
	}

	public abstract Move chooseMove(Game game);

	protected abstract double heuristic(Game game);
}
