package xyz.thomasrichards.superxo;

public class Trio<T> extends Tuple3<T,T,T> implements MatchTuple<T> {
	public Trio(T t, T u, T v) {
		super(t, u, v);
	}

	public boolean contains(T t) {
		return t == first || t == second || t == third || t.equals(first) || t.equals(second) || t.equals(third);
	}

	public int matchCount(T[] a) {
		int count = 0;

		for (T t : a) {
			if (this.contains(t)) count++;
		}

		return count;
	}
}