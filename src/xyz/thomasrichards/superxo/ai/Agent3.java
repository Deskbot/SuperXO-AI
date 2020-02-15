/*
 * Identical to Agent1 but diffs the trio worth for each player instead of
 * diffing the board worth for each player
 */

package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Double.*;

public class Agent3 extends Agent {

    public Agent3(Player symbol, int depth) {
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
        return this.cellWorth(b, this.symbol) - this.cellWorth(b, this.symbol.opponent());
    }

    private <C extends Cell> double cellWorth(C c, Player player) {
        if (!(c instanceof AbsGrid)) {
            Player owner = c.getOwner();

            if (owner == null) return 0.5;
            if (owner == player) return 1.0;
            return 0.0; //opponent of player
        }

        AbsGrid<?> g = (AbsGrid<?>) c;

        double util = 0.0;

        double maxRestOfTrioUtil, firstofTrioUtil, restOfTrioUtil;
        Map<Position, PosDuo[]> winDuos = AbsGrid.winDuos;
        PosDuo[] pdArr;
        Set<Position> winDuosSet = winDuos.keySet();

        for (Position p : winDuosSet) {
            firstofTrioUtil = this.cellWorth(g.getChild(p), player) - this.cellWorth(g.getChild(p), player.opponent());

            if (firstofTrioUtil == 0.0) continue;

            pdArr = winDuos.get(p);

            //get the max util that can be caused by choosing p
            maxRestOfTrioUtil = 0.0;
            for (PosDuo pd : pdArr) {
                restOfTrioUtil = this.cellWorth(g.getChild(pd.first), player) * this.cellWorth(g.getChild(pd.second), player)
                        - this.cellWorth(g.getChild(pd.first), player.opponent()) * this.cellWorth(g.getChild(pd.second), player.opponent());
                maxRestOfTrioUtil = max(maxRestOfTrioUtil, restOfTrioUtil);
            }

            util += firstofTrioUtil * maxRestOfTrioUtil;
        }

        return util / winDuosSet.size(); //should be divide by 9.0, which is the number of positions in a grid
    }
}
