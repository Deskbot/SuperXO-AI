package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.Tree;
import xyz.thomasrichards.superxo.game.*;

import java.util.Iterator;
import java.util.Set;

class GameTree extends Tree<Game> {
	GameTree(Game g) {
		super(g);
	}

	public Iterator<Tree<Game>> getChildren() {
		Set<Grid> validGrids = this.value.getValidGrids();

		return new Iterator<Tree<Game>>() {
			Iterator<Grid> gridChoices = validGrids.iterator();
			Iterator<Cell> cellChoices;
			Game game = GameTree.this.value.duplicate();
			Grid currentGrid;

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Tree<Game> next() {
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
}
