package xyz.thomasrichards.superxo;

public class Tuple3<A,B,C> {
	public A first;
	public B second;
	public C third;

	Tuple3(A first, B second, C third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public int hashCode() {
		return first.hashCode() ^ second.hashCode() ^ third.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Tuple3) {
			@SuppressWarnings("rawtypes")
			Tuple3 pairo = (Tuple3) o;
			return first.equals(pairo.first) && second.equals(pairo.second) && second.equals(pairo.third);
		}

		return false;
	}
}
