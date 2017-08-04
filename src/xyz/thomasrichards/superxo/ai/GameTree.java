package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.ISmallTree;
import xyz.thomasrichards.superxo.game.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class GameTree implements ISmallTree<Move, Game> {
	private final Game value;

	GameTree(Game g) {
		this.value = g;
	}

	public Game getValue() {
		return this.value;
	}

	public GameTree getChild(Move m) {
		Game g = this.value.duplicate();
		g.inputTurn(m);
		return new GameTree(g);
	}

	public Set<Move> getEdges() {
		Set<Move> edges = new HashSet<>();
		Set<Grid> gridChoices = this.value.getValidGrids();

		for (Grid g : gridChoices) {
			for (Cell c : g.getChildrenThat(Cell::isEmpty)) {
				edges.add(new Move(this.value.getTurnPlayer(), g.getPos(), c.getPos()));
			}
		}

		return edges;
	}

	public boolean isLeaf() {
		return this.value.isOver();
	}
}