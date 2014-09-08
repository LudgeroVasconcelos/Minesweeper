package domain.events;

import java.awt.Point;

public class GameOverEvent extends SquareEvent {

	Iterable<Point> mines;

	public GameOverEvent(int x, int y, Iterable<Point> iterable) {
		super(x, y);
		this.mines = iterable;
	}

	/**
	 * @return the mines
	 */
	public Iterable<Point> getMines() {
		return mines;
	}

}
