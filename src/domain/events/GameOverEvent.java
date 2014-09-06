package domain.events;

public class GameOverEvent extends SquareEvent {

	boolean[][] mines;

	public GameOverEvent(int x, int y, boolean[][] mines) {
		super(x, y);
		this.mines = mines;
	}

	/**
	 * @return the mines
	 */
	public boolean[][] getMines() {
		return mines;
	}

}
