package xyz.thomasrichards.superxo.game;

import java.util.HashSet;
import java.util.Set;

public class Game {
	private final Board board;
	private Cell lastChange;
	private Player turnPlayer;

	public Game() {
		this.board = new Board();
		this.turnPlayer = Player.X;
	}

	public Game(Board board, Player startPlayer) {
		this.board = board;
		this.turnPlayer = startPlayer;
	}

	public Set<Grid> getValidGrids() {
		if (this.lastChange == null) { //for the first turn
			return this.board.getChildren();

		} else {
			Position nextGridPos = this.lastChange.getPos();
			Grid targetGrid = this.board.getChild(nextGridPos);

			if (targetGrid.hasSpace()) {//targetGrid is insert-into-able
				Set<Grid> s = new HashSet<>();
				s.add(targetGrid);
				return s;

			} else {
				return this.board.getChildrenThat(Grid::hasSpace);
			}
		}
	}

	public void inputTurn(Move m) {
		inputTurn(m.getGridPos(), m.getCellPos());
	}

	public void inputTurn(Position gridPos, Position cellPos) {
		if (this.board.getOwner() != null)
			throw new GameAlreadyWonException(this.board.getOwner() + " has already won.");

		Grid g = this.board.getChild(gridPos);
		Cell c = g.getChild(cellPos);

		if (g.hasSpace() && c.getOwner() == null) { //move is valid
			c.setOwner(this.turnPlayer);
			this.lastChange = c;
			this.toggleTurnPlayer();

		} else {
			throw new InvalidMoveException("The move {grid: " + gridPos + ", cell: " + cellPos + "} is invalid in game: \n" + this.board);
		}
	}

	public boolean isWon() {
		return this.board.isWon();
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
		return new Game((Board) this.board.duplicate(), this.turnPlayer);
	}

	//private

	private void toggleTurnPlayer() {
		if (this.turnPlayer == Player.X) this.turnPlayer = Player.O;
		else this.turnPlayer = Player.X;
	}
}
