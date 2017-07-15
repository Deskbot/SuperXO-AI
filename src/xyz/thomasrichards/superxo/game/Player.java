package xyz.thomasrichards.superxo.game;

public enum Player {
    X,
    O;

    public Player opponent() {
    	return this == X ? O : X;
	}
}