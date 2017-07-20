package xyz.thomasrichards.superxo.game;

//bloody java generics

import java.util.ArrayList;
import java.util.List;

public class Grid extends AbsGrid<Cell> {
	private Board parent;

	@SuppressWarnings("unchecked")
	public Grid(Position p, Board parent) {
		super(p, (AbsGrid<Cell>)(AbsGrid<?>) parent);
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

	public Grid duplicate() {
		List<Cell> newChildren = new ArrayList<>();

		this.forEachChild(c -> newChildren.add(c.duplicate()));

		return new Grid(this.pos, this.parent) {
			@Override
			protected List<Cell> generateChildren() {
				return newChildren;
			}
		};
	}
}
