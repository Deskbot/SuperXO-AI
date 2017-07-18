package xyz.thomasrichards.superxo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tree<E,V> implements ISmallTree<E,V> {
	protected V value;
	protected Map<E,Tree<E,V>> children;

	public Tree(V value) {
		this.children = new HashMap<>();
		this.value = value;
	}

	public V getValue() {
		return this.value;
	}

	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	public void addChild(E edge, Tree<E,V> subtree) {
		this.children.put(edge, subtree);
	}

	public Tree<E,V> getChild(E edge) {
		return this.children.get(edge);
	}

	public Set<E> getEdges() {
		return this.children.keySet();
	}

	public Map<E, Tree<E,V>> getChildren() {
		return this.children;
	}
}
