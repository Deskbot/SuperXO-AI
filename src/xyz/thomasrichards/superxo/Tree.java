package xyz.thomasrichards.superxo;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Tree<T> {
	protected T value;
	protected Set<Tree<T>> children;

	public Tree(T value) {
		this.children = new TreeSet<>();
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	public void addChild(Tree<T> c) {
		this.children.add(c);
	}

	public Iterator<Tree<T>> getChildren() {
		return children.iterator();
	}
}
