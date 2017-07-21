package xyz.thomasrichards.superxo;

public class Tuple2<L,R> {

	public final L first;
	public final R second;

	Tuple2(L first, R second) {
		this.first = first;
		this.second = second;
	}

	public int hashCode() {
		return first.hashCode() ^ second.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Tuple2) {
			@SuppressWarnings("rawtypes")
			Tuple2 pairo = (Tuple2) o;
			return this.first.equals(pairo.first) && this.second.equals(pairo.second);
		}

		return false;
	}

}