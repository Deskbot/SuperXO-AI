package xyz.thomasrichards.superxo.ai;

import xyz.thomasrichards.superxo.Tree;

import java.util.function.Function;

import static java.lang.Double.max;
import static java.lang.Double.min;

public class Minimax<T> {
	private final Function<T, Double> heuristic;

	public Minimax(Function<T, Double> heuristic) {
		this.heuristic = heuristic;
	}

	public double getValue(Tree<T> node, int depth, boolean maximise) {
		if (depth == 0 || node.isLeaf()) return this.heuristic.apply(node.getValue());

		if (maximise) {
			double bestValue = Double.NEGATIVE_INFINITY;
			double v;

			for (Tree<T> child : node.getChildren()) {
				v = this.getValue(child, depth - 1, false);
				bestValue = max(bestValue, v);
			}
			return bestValue;

		} else {
			double bestValue = Double.POSITIVE_INFINITY;
			double v;

			for (Tree<T> child : node.getChildren()) {
				v = this.getValue(child, depth - 1, true);
				bestValue = min(bestValue, v);
			}
			return bestValue;
		}
	}
}