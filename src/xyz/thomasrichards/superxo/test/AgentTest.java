package xyz.thomasrichards.superxo.test;

import xyz.thomasrichards.superxo.ai.Agent;
import xyz.thomasrichards.superxo.ai.Agent1Shallow;
import xyz.thomasrichards.superxo.game.Game;
import xyz.thomasrichards.superxo.game.Move;
import xyz.thomasrichards.superxo.game.Player;

class AgentTest {
	public static void main(String[] args) {

		Game g = new Game();

		Agent1Shallow ai1 = new Agent1Shallow(Player.X, 3);
		Agent1Shallow ai2 = new Agent1Shallow(Player.O, 1);

		Agent turnPlayer;
		Move move;

		while (!g.isWon()) {
			turnPlayer = g.getTurnPlayer() == Player.X ? ai1 : ai2;
			move = turnPlayer.chooseMove(g.duplicate());
			g.inputTurn(move);
		}

		System.out.println(g.getWinner());
	}
}