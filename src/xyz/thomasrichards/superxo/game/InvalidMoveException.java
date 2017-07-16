package xyz.thomasrichards.superxo.game;

@SuppressWarnings("serial")
public class InvalidMoveException extends RuntimeException {
	public InvalidMoveException(String s) {
		super(s);
	}
}
