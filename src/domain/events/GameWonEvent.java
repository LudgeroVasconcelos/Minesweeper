package domain.events;

import java.awt.Point;

/**
 * The event that is fired when the game is won, meaning that at this point all
 * squares without mines are revealed.
 * 
 * @author Ludgero
 * 
 */
public class GameWonEvent extends Event {

	private Iterable<Point> unmarkedSquares;

	/**
	 * Constructs and initializes a new game won event.
	 * 
	 * @param unmarkedSquares
	 *            An iterable consisting of the positions of all squares that
	 *            are not marked. The purpose of this parameter is to let the
	 *            observer know what squares the user didn't flag after
	 *            finishing the game
	 */
	public GameWonEvent(Iterable<Point> unmarkedSquares) {
		this.unmarkedSquares = unmarkedSquares;
	}

	/**
	 * @return an iterable with the positions of squares not marked
	 */
	public Iterable<Point> getUnmarkedSquares() {
		return unmarkedSquares;
	}
}