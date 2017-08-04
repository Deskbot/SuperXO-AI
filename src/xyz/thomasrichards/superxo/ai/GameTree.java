package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.ISmallTree;
import xyz.thomasrichards.superxo.game.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class GameTree implements ISmallTree<Move, Game> {
	private Set<Move> edges;
	private final Game value;
	private final Map<Move,GameTree> children;

	GameTree(Game g) {
		this.children = new HashMap<>();
		this.value = g;
	}

	public Game getValue() {
		return this.value;
	}

	public GameTree getChild(Move m) {
		if (this.children.containsKey(m)) return this.children.get(m);

		Game g = this.value.duplicate();
		g.inputTurn(m);
		GameTree newChild = new GameTree(g);
		this.children.put(m, newChild);
		return newChild;
	}

	public Set<Move> getEdges() {
		if (this.edges != null) return this.edges;

		this.edges = new HashSet<>();
		Set<Grid> gridChoices = this.value.getValidGrids();

		for (Grid g : gridChoices) {
			for (Cell c : g.getChildrenThat(Cell::isEmpty)) {
				this.edges.add(new Move(this.value.getTurnPlayer(), g.getPos(), c.getPos()));
			}
		}

		return this.edges;
	}

	public boolean isLeaf() {
		return this.value == null;
	}
}