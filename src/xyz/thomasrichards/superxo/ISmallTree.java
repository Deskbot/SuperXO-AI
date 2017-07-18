package xyz.thomasrichards.superxo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ISmallTree<E,V> {

	public V getValue();

	public abstract boolean isLeaf();

	public abstract ISmallTree<E,V> getChild(E edge);

	public Set<E> getEdges();
}
