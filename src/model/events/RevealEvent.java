package model.events;

import java.awt.Point;
import java.util.Map.Entry;

/**
 * This class represents the event that is fired when squares are revealed.
 * 
 * @author Ludgero
 * 
 */
public class RevealEvent extends Event {

	private Iterable<Entry<Point, Integer>> revealedSquares;

	/**
	 * Constructs a new square revealed event and initializes it with the
	 * specified iterable consisting of the revealed squares.
	 * 
	 * @param revealedSquares
	 *            The iterable containing the positions of the revealed squares
	 *            and the corresponding number of neighboring mines.
	 */
	public RevealEvent(Iterable<Entry<Point, Integer>> revealedSquares) {
		this.revealedSquares = revealedSquares;
	}

	/**
	 * @return The iterable of revealed squares.
	 */
	public Iterable<Entry<Point, Integer>> getRevealedSquares() {
		return revealedSquares;
	}

}
