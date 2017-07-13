package xyz.thomasrichards.superxo;

class Pair<L,R> {

	private final L left;
	private final R right;

	Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L left() {
		return left;
	}
	public R right() {
		return right;
	}

	public int hashCode() {
		return left.hashCode() ^ right.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Pair) {
			@SuppressWarnings("rawtypes")
			Pair pairo = (Pair) o;
			return this.left.equals(pairo.left()) && this.right.equals(pairo.right());
		}

		return false;
	}

}