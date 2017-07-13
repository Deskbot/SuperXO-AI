package xyz.thomasrichards.superxo.game;

class Move {
	private final Player player;
	private final Position gridPos;
	private final Position cellPos;

	public Move(Player player, Position gridPos, Position cellPos) {
		this.player = player;
		this.gridPos = gridPos;
		this.cellPos = cellPos;
	}

	public String toString() {
		return "[" + this.player + "," + this.gridPos + "," + this.cellPos + "]";
	}
}
