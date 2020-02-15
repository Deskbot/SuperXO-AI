package xyz.thomasrichards.superxo.test;

import xyz.thomasrichards.superxo.ai.Agent;
import xyz.thomasrichards.superxo.ai.Agent2;
import xyz.thomasrichards.superxo.game.Game;
import xyz.thomasrichards.superxo.game.Move;
import xyz.thomasrichards.superxo.game.Player;

import static xyz.thomasrichards.superxo.game.Position.*;

class BadGame {
	public static void main(String[] args) {
		Game g = new Game();

		g.inputTurn(MM,MM);
		g.inputTurn(MM,BM);
		g.inputTurn(BM,TR);
		g.inputTurn(TR,TR);
		g.inputTurn(TR,BL);
		g.inputTurn(BL,TR);
		g.inputTurn(TR,ML);
		g.inputTurn(ML,ML);
		g.inputTurn(ML,BM);
		g.inputTurn(BM,MM);
		g.inputTurn(MM,TL);
		g.inputTurn(TL,BM);
		g.inputTurn(BM,MR);
		g.inputTurn(MR,TR);
		g.inputTurn(TR,TL);
		g.inputTurn(TL,TR);
		g.inputTurn(MM,BR);
		g.inputTurn(BR,MM);
		g.inputTurn(BM,BR);
		g.inputTurn(BR,TL);
		g.inputTurn(TL,BL);
		g.inputTurn(BL,MM);
		g.inputTurn(TM,TL);
		g.inputTurn(TL,TL);
		g.inputTurn(TL,MR);
		g.inputTurn(MR,MM);
		g.inputTurn(TM,BR);
		//g.inputTurn(BR,BR);

		Agent a = new Agent2(Player.O,2);
		Move m = a.chooseMove(g);

		System.out.println(m);
	}
}
