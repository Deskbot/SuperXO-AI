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

/*
public Iterator<Tree<Move, Game>> iterator() {
	Set<Grid> validGrids = this.value.getValidGrids();

	return new Iterator<Tree<Move, Game>>() {
		Iterator<Grid> gridChoices = validGrids.iterator();
		Iterator<Cell> cellChoices;
		Game game = GameTree.this.value.duplicate();
		Grid currentGrid;

		public boolean hasNext() {
			return false;
		}

		public GameTree next() {
			if (!cellChoices.hasNext() || currentGrid == null) {
				if (this.gridChoices.hasNext()) {
					this.currentGrid = this.gridChoices.next();
				} else {
					return null;
				}
			}
			return new GameTree(this.getGameWithMove(currentGrid.getPos(), this.cellChoices.next().getPos()));
		}

		private Game getGameWithMove(Position gridPos, Position cellPos) {
			Game newGame = game.duplicate();
			newGame.inputTurn(gridPos, cellPos);
			return newGame;
		}
	};
}
 */