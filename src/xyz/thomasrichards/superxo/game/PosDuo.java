//class exists to allow generic array creation in AbsGrid.java

package xyz.thomasrichards.superxo.game;

import xyz.thomasrichards.superxo.Duo;

public class PosDuo extends Duo<Position> {
	public PosDuo(Position p, Position q) {
		super(p,q);
	}
}