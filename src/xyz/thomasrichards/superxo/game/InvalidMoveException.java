package xyz.thomasrichards.superxo.game;

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception {
	public InvalidMoveException(String s) {
		super(s);
	}
}
