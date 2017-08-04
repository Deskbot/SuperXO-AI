package xyz.thomasrichards.superxo.game;

@SuppressWarnings("serial")
public class GameAlreadyOverException extends RuntimeException {
	public GameAlreadyOverException(String s) {
		super(s);
	}
}
