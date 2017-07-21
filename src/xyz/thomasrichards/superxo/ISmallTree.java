package xyz.thomasrichards.superxo;

import java.util.Set;

public interface ISmallTree<E,V> {

	V getValue();

	boolean isLeaf();

	ISmallTree<E,V> getChild(E edge);

	Set<E> getEdges();
}
