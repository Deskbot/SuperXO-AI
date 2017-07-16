package xyz.thomasrichards.superxo.game;

public class Cell {
    final AbsGrid<Cell> parent;
    Player owner;
    protected final Position pos;

	public Cell(Position p, AbsGrid<Cell> parent) {
		this.parent = parent;
		this.pos = p;
	}

    public Player getOwner() {
        return owner;
    }

	public AbsGrid<Cell> getParent() {
		return parent;
	}

	public Position getPos() {
        return pos;
    }

    public void setOwner(Player o) {
        this.owner = o;
        this.parent.updateOwner(this);
    }

    public boolean isEmpty() {
		return this.owner == null;
	}

    public String toString() {
        return this.owner == null ? "null" : this.owner.toString();
    }

    public Cell duplicate() {
		return new Cell(this.pos, this.parent);
	}
}
