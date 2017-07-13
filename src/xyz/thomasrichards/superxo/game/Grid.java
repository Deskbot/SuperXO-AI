package xyz.thomasrichards.superxo.game;

//bloody java generics

import java.util.ArrayList;
import java.util.List;

public class Grid extends GridGen<Cell> {
	@SuppressWarnings("unchecked")
	public Grid(Position p, GridGen<Grid> parent) {
		super(p, (GridGen<Cell>)(GridGen<?>) parent);
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
