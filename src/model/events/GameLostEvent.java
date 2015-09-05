package model.events;

import java.awt.Point;

/**
 * Represents the game over event which is fired when a mined square is clicked.
 * 
 * @author Ludgero
 * 
 */
public class GameLostEvent extends SquareEvent {

	private Iterable<Point> mines;
	private Iterable<Point> mistakenMarks;

	/**
	 * Constructs and initializes a new game lost event.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param mines
	 *            An Iterable containing all mines' positions that are not
	 *            marked
	 * @param mistakenMarks
	 *            An iterable containing safe squares that are marked
	 */
	public GameLostEvent(int x, int y, Iterable<Point> mines,
			Iterable<Point> mistakenMarks) {
		super(x, y);
		this.mines = mines;
		this.mistakenMarks = mistakenMarks;
	}

	/**
	 * @return an iterable containing all unmarked mines
	 */
	public Iterable<Point> getMines() {
		return mines;
	}

	/**
	 * @return An iterable containing safe squares that are marked
	 */
	public Iterable<Point> getMistakenMarks() {
		return mistakenMarks;
	}
}
