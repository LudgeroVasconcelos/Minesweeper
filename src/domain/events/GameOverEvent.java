package domain.events;

import java.awt.Point;

/**
 * Represents the game over event which is fired when a mined square is clicked.
 * 
 * @author Ludgero
 * 
 */
public class GameOverEvent extends SquareEvent {

	private Iterable<Point> mines;
	private Iterable<Point> mistakenMarks;

	/**
	 * Constructs and initializes a new game over event.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param iterable
	 *            An Iterable containing all mines' positions that are not
	 *            marked
	 * @param mistakenMarks
	 *            An iterable containing the squares that are marked but are not
	 *            mined
	 */
	public GameOverEvent(int x, int y, Iterable<Point> iterable,
			Iterable<Point> mistakenMarks) {
		super(x, y);
		this.mines = iterable;
		this.mistakenMarks = mistakenMarks;
	}

	/**
	 * @return an iterable containing all mines positions
	 */
	public Iterable<Point> getMines() {
		return mines;
	}

	public Iterable<Point> getMistakenMarks() {
		return mistakenMarks;
	}
}
