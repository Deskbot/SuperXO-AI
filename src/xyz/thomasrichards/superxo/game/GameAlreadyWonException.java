package xyz.thomasrichards.superxo.game;

@SuppressWarnings("serial")
public class GameAlreadyWonException extends RuntimeException {
	public GameAlreadyWonException(String s) {
		super(s);
	}
}
