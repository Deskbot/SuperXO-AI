package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.ISmallTree;

import java.util.function.Function;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.max;
import static java.lang.Double.min;

public class Minimax<M,V> {
	private final Function<V, Double> heuristic;

	Minimax(Function<V, Double> heuristic) {
		this.heuristic = heuristic;
	}

	public double getValue(ISmallTree<M,V> node, int depth, boolean maximise) {
		return getValue(node, depth, NEGATIVE_INFINITY, POSITIVE_INFINITY, maximise);
	}

	//minimax with Alpha-Beta Pruning
	private double getValue(ISmallTree<M,V> node, int depth, double alpha, double beta, boolean maximise) {
		if (depth == 0 || node.isLeaf()) return this.heuristic.apply(node.getValue());

		double v, bestValue;
		ISmallTree<M,V> child;

		if (maximise) {
			bestValue = NEGATIVE_INFINITY;

			for (M move : node.getEdges()) {
				child = node.getChild(move);
				v = this.getValue(child, depth - 1, alpha, beta, false);

				if (v == POSITIVE_INFINITY) return v; //v can't be maximised further

				bestValue = max(bestValue, v);
				alpha = max(alpha, bestValue);

				if (beta <= alpha) break;

			}
			return bestValue;

		} else {
			bestValue = POSITIVE_INFINITY;

			for (M move : node.getEdges()) {
				child = node.getChild(move);
				v = this.getValue(child, depth - 1, alpha, beta, true);

				if (v == NEGATIVE_INFINITY) return v; //v can't be minimised further

				bestValue = min(bestValue, v);
				beta = min(beta, bestValue);

				if (beta <= alpha) break;
			}
			return bestValue;
		}
	}
}