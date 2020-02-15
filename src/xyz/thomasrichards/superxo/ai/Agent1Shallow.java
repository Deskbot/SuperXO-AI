/*
 * This agent uses the weighting of each trio of ways of winning as a heuristic
 * to determine how good the current game state is for a given player.
 * It only looks at the state of the large game and ignores the content of the small games.
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.*;

public class Agent1Shallow extends Agent {

	public Agent1Shallow(Player symbol, int depth) {
		super(depth, symbol);
	}

	public Move chooseMove(Game game) {
		if (game.getTurnPlayer() != this.symbol) return null;

		GameTree gt = new GameTree(game);
		double topScore = 0.0;
		double tmp;
		List<Move> topMoves = new ArrayList<>();

		for (Move move : gt.getEdges()) {
			tmp = this.minimax.getValue(gt.getChild(move), this.depth, false); //min due to this being the opponent's move
			if (tmp > topScore || topMoves.isEmpty()) {
				topScore = tmp;
				topMoves = new ArrayList<>();
				topMoves.add(move);

			} else if (tmp == topScore) {
				topMoves.add(move);
			}
		}

		return topMoves.get((int) (Math.random() * topMoves.size()));
	}

	protected Double heuristic(Game g) {
		if (g.isDraw())
			return 0.0;

		Player winner = g.getWinner();
		if (winner == null)
			return this.boardWorth(g.getBoard());
		if (winner == this.symbol)
			return POSITIVE_INFINITY;

		return NEGATIVE_INFINITY; // opponent
	}

	private double boardWorth(Board b) {
		return this.boardWorth(b, this.symbol) - this.boardWorth(b, this.symbol.opponent());
	}

	private double boardWorth(Board b, Player player) {
		double util = 0.0;

		double maxRestOfTrioUtil, firstofTrioUtil, restOfTrioUtil;
		Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
		PosDuo[] pdArr;
		Set<Position> winDuosSet = winDuos.keySet();

		for (Position p : winDuosSet) {
			firstofTrioUtil = this.smallGameWorth(b.getChild(p), player);

			if (firstofTrioUtil == 0.0) continue;

			pdArr = winDuos.get(p);

			//get the max util that can be caused by choosing p
			maxRestOfTrioUtil = 0.0;
			for (PosDuo pd : pdArr) {
				restOfTrioUtil = this.smallGameWorth(b.getChild(pd.first), player) * this.smallGameWorth(b.getChild(pd.second), player);
				maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
			}

			util += firstofTrioUtil * maxRestOfTrioUtil;
		}

		return util / winDuosSet.size(); //should be divide by 9.0, which is the number of positions in a grid
	}

	private double smallGameWorth(Grid c, Player player) {
		Player owner = c.getOwner();

		if (owner == null) return 0.5;
		if (owner == player) return 1.0;
		return 0.0; //opponent of player
	}
}
