package xyz.thomasrichards.superxo.test;

import xyz.thomasrichards.superxo.game.Game;

import static xyz.thomasrichards.superxo.game.Position.TL;
import static xyz.thomasrichards.superxo.game.Position.TR;

class DuplicateTest {
	public static void main(String[] args) {
		Game g = new Game();
		g.inputTurn(TL,TR);

		Game g2 = g.duplicate();

		System.out.println(g.getBoard());
		System.out.println(g2.getBoard());
		System.out.println(g.getBoard().toString().equals(g2.getBoard().toString()));
	}
}
