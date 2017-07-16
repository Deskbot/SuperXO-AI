package xyz.thomasrichards.superxo.game;

public class Move {
	private final Player player;
	private final Position gridPos;
	private final Position cellPos;

	public Move(Player player, Position gridPos, Position cellPos) {
		this.player = player;
		this.gridPos = gridPos;
		this.cellPos = cellPos;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Position getGridPos() {
		return this.gridPos;
	}

	public Position getCellPos() {
		return this.cellPos;
	}

	public String toString() {
		return "[" + this.player + "," + this.gridPos + "," + this.cellPos + "]";
	}
}
