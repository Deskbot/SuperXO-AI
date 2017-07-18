package xyz.thomasrichards.superxo.game;

import xyz.thomasrichards.superxo.Trio;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static xyz.thomasrichards.superxo.game.Position.*;

public abstract class AbsGrid<C extends Cell> extends Cell {
	private boolean full = false;
	private List<C> matrix;

	//static

	public static final Set<Trio<Position>> winTrios = new TreeSet<>();

	static {
		winTrios.add(new Trio<>(TL, TM, TR));
		winTrios.add(new Trio<>(ML, MM, MR));
		winTrios.add(new Trio<>(BL, BM, BR));
		winTrios.add(new Trio<>(TL, ML, BL));
		winTrios.add(new Trio<>(TM, MM, BM));
		winTrios.add(new Trio<>(TR, MR, BR));
		winTrios.add(new Trio<>(TL, MM, BR));
		winTrios.add(new Trio<>(BL, MM, TR));
	}
	
	public static final Map<Position, PosDuo[]> winDuos = new HashMap<>();

	static {
		winDuos.put(TL,
				new PosDuo[]{new PosDuo(TM, TR), new PosDuo(ML, BL), new PosDuo(MM, BR)}
		);
		winDuos.put(TM,
				new PosDuo[]{new PosDuo(TL, TR), new PosDuo(MM, BM)}
		);
		winDuos.put(TR,
				new PosDuo[]{new PosDuo(TL, TM), new PosDuo(MR, BR), new PosDuo(BL, MM)}
		);
		winDuos.put(ML,
				new PosDuo[]{new PosDuo(MM, MR), new PosDuo(TL, BL)}
		);
		winDuos.put(MM,
				new PosDuo[]{new PosDuo(ML, MR), new PosDuo(TM, BM), new PosDuo(TL, BR), new PosDuo(BL, TR)}
		);
		winDuos.put(MR,
				new PosDuo[]{new PosDuo(ML, MM), new PosDuo(TR, BR)}
		);
		winDuos.put(BL,
				new PosDuo[]{new PosDuo(BM, BR), new PosDuo(TL, ML), new PosDuo(MM, TR)}
		);
		winDuos.put(BM,
				new PosDuo[]{new PosDuo(BL, BR), new PosDuo(TM, MM)}
		);
		winDuos.put(BR,
				new PosDuo[]{new PosDuo(BL, BM), new PosDuo(TR, MR), new PosDuo(TL, MM)}
		);
	}

	//constructor

	public AbsGrid(Position p, AbsGrid<Cell> parent) {
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
		PosDuo[] testCases = AbsGrid.winDuos.get(change.getPos());
		Player owner1 = change.getOwner();

		for (PosDuo pd : testCases) {
			Player owner2 = this.getChild(pd.first).getOwner();
			Player owner3 = this.getChild(pd.second).getOwner();

			if (owner1 == owner2 && owner2 == owner3) { //see if controllers of all cells match
				this.owner = owner1;
				if (this.parent != null) this.parent.updateOwner(this);
			}
		}
	}

	public void forEachChild(Consumer<C> f) {
		this.matrix.forEach(f);
	}

	public Trio<Double> waysToWinCount(Player p) {
		if (this.owner != null) return new Trio<>(1.0,0.0,0.0);

		Trio<Double> res = new Trio<>(0.0,0.0,0.0);

		Set<C> plKids = this.getChildrenThat(c -> c.getOwner() == p);
		Set<C> opKids = this.getChildrenThat(c -> c.getOwner() == p.opponent());

		int qtyOwned;

		//add all possible combinations to win
		fortrio:
		for (Trio<Position> trio : winTrios) {
			qtyOwned = 0;

			for (C oppOwned : opKids) {
				if (trio.contains(oppOwned.getPos())) break fortrio;
			}

			for (C owned : plKids) {
				if (trio.contains(owned.getPos())) qtyOwned++;
			}

			if (qtyOwned == 0);
			else if (qtyOwned == 1) res.first++;
			else if (qtyOwned == 2) res.second++;
			else if (qtyOwned == 3) res.third++;
		}

		return res;
	}

	public String toString() {
		return this.matrix.toString();
	}

	@SuppressWarnings("unchecked")
	public AbsGrid<C> duplicate() {
		List<C> newChildren = new ArrayList<>();

		this.forEachChild(c -> {
			newChildren.add((C) c.duplicate());
		});

		return new AbsGrid<C>(this.pos, this.parent) {
			@Override
			protected List<C> generateChildren() {
				return newChildren;
			}
		};
	}

	//protected

	//this requires the instantiation of a generic object which isn't doable, so a subclass must extend this class with type parameters specified and generate the children in this method in the subclass
	protected abstract List<C> generateChildren();
}
