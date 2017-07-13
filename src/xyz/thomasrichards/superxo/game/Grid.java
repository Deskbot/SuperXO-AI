package xyz.thomasrichards.superxo.game;

//bloody java generics

import java.util.ArrayList;
import java.util.List;

public class Grid extends AbsGrid<Cell> {
	@SuppressWarnings("unchecked")
	public Grid(Position p, AbsGrid<Grid> parent) {
		super(p, (AbsGrid<Cell>)(AbsGrid<?>) parent);
	}

	protected List<Cell> generateChildren() {
		Position[] positions = Position.class.getEnumConstants();
		List<Cell> children = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			children.add(new Cell(positions[i], this));
		}

		return children;
	}
}
