package xyz.thomasrichards.superxo.game;

//bloody java generics

import java.util.ArrayList;
import java.util.List;

public class Board extends GridGen<Grid> {
	Board() {
		super(null, null);
	}

	protected List<Grid> generateChildren() {
		Position[] positions = Position.class.getEnumConstants();
		List<Grid> children = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			children.add(i, new Grid(positions[i], this));
		}

		return children;
	}
}
