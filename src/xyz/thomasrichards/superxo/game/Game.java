package xyz.thomasrichards.superxo.game;

import java.util.HashSet;
import java.util.Set;

public class Game {
	private final Board board;
	private Position lastCellPosUsed;
	private Player turnPlayer;
	private Set<Grid> validGridsThisTurn;

	public Game() {
		this.board = new Board();
		this.turnPlayer = Player.X;
	}

	private Game(Board board, Position lastCellPosUsed, Player startPlayer, Set<Grid> validGridsThisTurn) {
		this.board = board;
		this.lastCellPosUsed = lastCellPosUsed;
		this.turnPlayer = startPlayer;
		this.validGridsThisTurn = validGridsThisTurn;
	}

	public Set<Grid> getValidGrids() {
		if (this.validGridsThisTurn != null) return this.validGridsThisTurn;

		if (this.lastCellPosUsed == null) { //for the first turn
			return this.validGridsThisTurn = this.board.getChildren();

		} else {
			Position nextGridPos = this.lastCellPosUsed;
			Grid targetGrid = this.board.getChild(nextGridPos);

			if (targetGrid.hasSpace()) {//targetGrid is insert-into-able
				Set<Grid> s = new HashSet<>();
				s.add(targetGrid);
				return this.validGridsThisTurn = s;

			} else {
				return this.validGridsThisTurn = this.board.getChildrenThat(Grid::hasSpace);
			}
		}
	}

	public void inputTurn(Move m) {
		inputTurn(m.getGridPos(), m.getCellPos());
	}

	public void inputTurn(Position gridPos, Position cellPos) {
		if (this.isOver())
			throw new GameAlreadyOverException(this.board.getOwner() + " has already won.");

		Grid g = this.board.getChild(gridPos);
		Cell c = g.getChild(cellPos);

		if (g.hasSpace() && c.getOwner() == null) { //move is valid
			c.setOwner(this.turnPlayer);
			this.lastCellPosUsed = cellPos;
			this.toggleTurnPlayer();

		} else {
			throw new InvalidMoveException("Invalid move {grid: " + gridPos + ", cell: " + cellPos + "} in game: \n" + this.board);
		}

		this.validGridsThisTurn = null;
	}

	public boolean isOver() {
		return this.isWon() || this.isDraw();
	}

	public boolean isWon() {
		return this.board.isWon();
	}

	public boolean isDraw() {
		return this.getValidGrids().isEmpty();
	}

	public Player getWinner() {
		return this.board.getOwner();
	}

	public Board getBoard() {
		return this.board;
	}

	public Player getTurnPlayer() {
		return this.turnPlayer;
	}

	public Game duplicate() {
		return new Game(this.board.duplicate(), this.lastCellPosUsed, this.turnPlayer, this.validGridsThisTurn);
	}

	//private

	private void toggleTurnPlayer() {
		if (this.turnPlayer == Player.X) this.turnPlayer = Player.O;
		else this.turnPlayer = Player.X;
	}
}
