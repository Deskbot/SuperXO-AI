package xyz.thomasrichards.superxo.game;

public class Test {
	public static void main(String[] args) {
		Game g = new Game();
	try {
		g.inputTurn(Position.TM, Position.BL);
		g.inputTurn(Position.BL, Position.BR);
		g.inputTurn(Position.BR, Position.BR);
		g.inputTurn(Position.BR, Position.TL);
		g.inputTurn(Position.TL, Position.TM);
		g.inputTurn(Position.TM, Position.MM);
		g.inputTurn(Position.MM, Position.BR);
		g.inputTurn(Position.BR, Position.TM);
		g.inputTurn(Position.TM, Position.BR);
		g.inputTurn(Position.BR, Position.MR);
		g.inputTurn(Position.MR, Position.TM);
		g.inputTurn(Position.TM, Position.MR);
		g.inputTurn(Position.MR, Position.TL);
		g.inputTurn(Position.TL, Position.BL);
		g.inputTurn(Position.BL, Position.TM);
		g.inputTurn(Position.TM, Position.TL);
		g.inputTurn(Position.TL, Position.MR);
		g.inputTurn(Position.MR, Position.MR);
		g.inputTurn(Position.MR, Position.BM);
		g.inputTurn(Position.BM, Position.TL);
		g.inputTurn(Position.TL, Position.MM);
		g.inputTurn(Position.MM, Position.MR);
		g.inputTurn(Position.MR, Position.BL);
		g.inputTurn(Position.BL, Position.MM);
		g.inputTurn(Position.MM, Position.MM);
		g.inputTurn(Position.MM, Position.BL);
		g.inputTurn(Position.BL, Position.BL);
		g.inputTurn(Position.BL, Position.BM);
		g.inputTurn(Position.BM, Position.TR);
		g.inputTurn(Position.TR, Position.MR);
		g.inputTurn(Position.MR, Position.ML);
		g.inputTurn(Position.ML, Position.BR);
		g.inputTurn(Position.BR, Position.BL);
		g.inputTurn(Position.BL, Position.TR);
		g.inputTurn(Position.TR, Position.BR);
		g.inputTurn(Position.BR, Position.ML);
		g.inputTurn(Position.ML, Position.MR);
		g.inputTurn(Position.TM, Position.TM);
		g.inputTurn(Position.TM, Position.BM);
		g.inputTurn(Position.BM, Position.MR);
		g.inputTurn(Position.TR, Position.TR);
		g.inputTurn(Position.TR, Position.TM);
		g.inputTurn(Position.BL, Position.MR);
		g.inputTurn(Position.ML, Position.MM);
		g.inputTurn(Position.MM, Position.BM);
		g.inputTurn(Position.BM, Position.BR);
		g.inputTurn(Position.BR, Position.TR);
		g.inputTurn(Position.TR, Position.TL);
		g.inputTurn(Position.TL, Position.TR);
		g.inputTurn(Position.TR, Position.BL);
		g.inputTurn(Position.BL, Position.ML);
		g.inputTurn(Position.ML, Position.TR);
		g.inputTurn(Position.TR, Position.MM);
		g.inputTurn(Position.MM, Position.TL);
		g.inputTurn(Position.TL, Position.ML);
		g.inputTurn(Position.ML, Position.BM);
		g.inputTurn(Position.BM, Position.MM);
		g.inputTurn(Position.MM, Position.TR);
		g.inputTurn(Position.TR, Position.ML);
		g.inputTurn(Position.ML, Position.BL);
		g.inputTurn(Position.BL, Position.TL);
		g.inputTurn(Position.BR, Position.BM);
		g.inputTurn(Position.BM, Position.BL);
		g.inputTurn(Position.MM, Position.ML);
		g.inputTurn(Position.BR, Position.MM);
		System.out.println(g.getWinner());
	} catch (InvalidMoveException e) {
		System.err.println(e.getMessage());
	} catch (GameAlreadyWonException e) {
		System.err.println(e.getMessage());
	}

	}
}
