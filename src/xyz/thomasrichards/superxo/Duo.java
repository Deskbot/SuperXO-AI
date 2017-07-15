package xyz.thomasrichards.superxo;

public class Duo<T> extends Tuple2<T, T> implements MatchTuple<T> {
	public Duo(T t, T u) {
		super(t, u);
	}

	public boolean contains(T t) {
		return t == first || t == second || t.equals(first) || t.equals(second);
	}

	public int matchCount(T[] a) {
		int count = 0;

		for (T t : a) {
			if (this.contains(t)) count++;
		}

		return count;
	}
}