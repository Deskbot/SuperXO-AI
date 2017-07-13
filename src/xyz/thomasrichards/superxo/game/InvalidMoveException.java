package xyz.thomasrichards.superxo.game;

@SuppressWarnings("serial")
class InvalidMoveException extends Exception {
	public InvalidMoveException(String s) {
		super(s);
	}
}
