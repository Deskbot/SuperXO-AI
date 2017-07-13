package xyz.thomasrichards.superxo.game;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class GridGen<C extends Cell> extends Cell {

	private boolean full = false;
	private List<C> matrix;

	//static
	private static final Map<Position, PosDuo[]> winCombos = new HashMap<>();
	static {
		winCombos.put(Position.TL,
				new PosDuo[]{new PosDuo(Position.TM, Position.TR), new PosDuo(Position.ML, Position.BL), new PosDuo(Position.MM, Position.BR)}
		);
		winCombos.put(Position.TM,
				new PosDuo[]{new PosDuo(Position.TL, Position.TR), new PosDuo(Position.MM, Position.BM)}
		);
		winCombos.put(Position.TR,
				new PosDuo[]{new PosDuo(Position.TL, Position.TM), new PosDuo(Position.MR, Position.BR), new PosDuo(Position.BL, Position.MM)}
		);
		winCombos.put(Position.ML,
				new PosDuo[]{new PosDuo(Position.MM, Position.MR), new PosDuo(Position.TL, Position.BL)}
		);
		winCombos.put(Position.MM,
				new PosDuo[]{new PosDuo(Position.ML, Position.MR), new PosDuo(Position.TM, Position.BM), new PosDuo(Position.TL, Position.BR), new PosDuo(Position.BL, Position.TR)}
		);
		winCombos.put(Position.MR,
				new PosDuo[]{new PosDuo(Position.ML, Position.MM), new PosDuo(Position.TR, Position.BR)}
		);
		winCombos.put(Position.BL,
				new PosDuo[]{new PosDuo(Position.BM, Position.BR), new PosDuo(Position.TL, Position.ML), new PosDuo(Position.MM, Position.TR)}
		);
		winCombos.put(Position.BM,
				new PosDuo[]{new PosDuo(Position.BL, Position.BR), new PosDuo(Position.TM, Position.MM)}
		);
		winCombos.put(Position.BR,
				new PosDuo[]{new PosDuo(Position.BL, Position.BM), new PosDuo(Position.TR, Position.MR), new PosDuo(Position.TL, Position.MM)}
		);
	}

	//constructor

	public GridGen(Position p, GridGen<Cell> parent) {
		super(p, parent);
		this.matrix = this.generateChildren();
	}

	//public

	public C getChild(Position p) {
		return this.matrix.get(p.getIndex());
	}

	public Set<C> getChildren() {
		final Set<C> s = new HashSet<>();

		this.forEachChild(s::add);

		return s;
	}

	public Set<C> getChildrenThat(Predicate<C> p) {
		final Set<C> s = new HashSet<>();

		this.forEachChild(c -> {
			if (p.test(c)) s.add(c);
		});

		return s;
	}

	public boolean hasSpace() { //if the cell is owned, nothing can be put in, so there is no space
		return this.owner != null || this.isFull();
	}

	boolean isFull() {
		if (this.full) return true;

		for (C c : this.matrix) {
			if (c.getOwner() != null) return false;
		}

		this.full = true;
		return true;
	}

	public boolean isWon() {
		return this.owner != null;
	}

	public void updateOwner(C change) {
		PosDuo[] testCases = GridGen.winCombos.get(change.getPos());
		Player owner1 = change.getOwner();

		for (PosDuo pd : testCases) {
			Player owner2 = this.getChild(pd.left()).getOwner();
			Player owner3 = this.getChild(pd.right()).getOwner();

			if (owner1 == owner2 && owner2 == owner3) { //see if controllers of all cells match
				this.owner = owner1;
				if (this.parent != null) this.parent.updateOwner(this);
			}
		}
	}

	public void forEachChild(Consumer<C> f) {
		for (C c : this.matrix) {
			f.accept(c);
		}
	}

	public String toString() {
		return this.matrix.toString();
	}

	//private

	protected abstract List<C> generateChildren();
}
