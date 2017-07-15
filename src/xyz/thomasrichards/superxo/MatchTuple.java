package xyz.thomasrichards.superxo;

public interface MatchTuple<T> {
	public boolean contains(T instance);

	public int matchCount(T[] array);
}
