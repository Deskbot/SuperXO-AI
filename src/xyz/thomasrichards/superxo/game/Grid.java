package xyz.thomasrichards.superxo.game;

//bloody java generics

import java.util.ArrayList;
import java.util.List;

public class Grid extends AbsGrid<Cell> {
	private final Board parent;

	@SuppressWarnings("unchecked")
	Grid(Position pos, Board parent) {
		super(pos, (AbsGrid<Cell>)(AbsGrid<?>) parent);
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	private Grid(Position pos, Board parent, Player owner) {
		super(pos, (AbsGrid<Cell>)(AbsGrid<?>) parent, owner);
		this.parent = parent;
	}

	protected List<Cell> generateChildren() {
		Position[] positions = Position.class.getEnumConstants();
		List<Cell> children = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			children.add(new Cell(positions[i], this));
		}

		return children;
	}

	public Grid duplicate(Board parent) {
		return new Grid(this.pos, parent, this.owner) {
			@Override
			protected List<Cell> generateChildren() {
				List<Cell> newChildren = new ArrayList<>();
				Grid.this.forEachChild(c -> newChildren.add(c.duplicate(this)));
				return newChildren;
			}
		};
	}
}
