package domain.events;

import java.awt.Point;

/**
 * Represents the game over event which is fired when a mined square is clicked
 * 
 * @author Ludgero
 * 
 */
public class GameOverEvent extends SquareEvent {

	Iterable<Point> mines;

	/**
	 * Constructs and initializes a new game over event.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param iterable
	 *            The Iterable containing all mines positions
	 */
	public GameOverEvent(int x, int y, Iterable<Point> iterable) {
		super(x, y);
		this.mines = iterable;
	}

	/**
	 * @return an iterable containing all mines positions
	 */
	public Iterable<Point> getMines() {
		return mines;
	}

}
