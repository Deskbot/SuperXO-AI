package xyz.thomasrichards.superxo;

interface MatchTuple<T> {
	boolean contains(T instance);

	int matchCount(T[] array);
}
