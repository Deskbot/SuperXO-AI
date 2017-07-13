package xyz.thomasrichards.superxo.game;

@SuppressWarnings("serial")
class GameAlreadyWonException extends RuntimeException {
	public GameAlreadyWonException(String s) {
		super(s);
	}
}
