package xyz.thomasrichards.superxo.game;

//bloody java generics

import java.util.ArrayList;
import java.util.List;

public class Board extends AbsGrid<Grid> {

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

	public Board duplicate() {
		return new Board() {
			@Override
			protected List<Grid> generateChildren() {
				List<Grid> newChildren = new ArrayList<>();
				Board.this.forEachChild(c -> newChildren.add(c.duplicate(this)));
				return newChildren;
			}
		};
	}
}
