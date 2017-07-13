package xyz.thomasrichards.superxo.game;

class Cell {
    final GridGen<Cell> parent;
    Player owner;
    private final Position pos;

	public Cell(Position p, GridGen<Cell> parent) {
		this.parent = parent;
		this.pos = p;
	}

    public Player getOwner() {
        return owner;
    }

	public GridGen<Cell> getParent() {
		return parent;
	}

	public Position getPos() {
        return pos;
    }

    public void setOwner(Player o) {
        this.owner = o;
        this.parent.updateOwner(this);
    }

    public String toString() {
        return this.owner == null ? "null" : this.owner.toString();
    }
}
