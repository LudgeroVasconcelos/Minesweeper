package model.events;

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
	private int score;

	/**
	 * Constructs and initializes a new game won event.
	 * 
	 * @param unmarkedSquares
	 *            An iterable consisting of the positions of all squares that
	 *            are not marked. The purpose of this parameter is to let the
	 *            observer know what squares the user didn't flag after
	 *            finishing the game
	 * @param score
	 *            time lapsed, in seconds, since the start of the game
	 */
	public GameWonEvent(Iterable<Point> unmarkedSquares, int score) {
		this.unmarkedSquares = unmarkedSquares;
		this.score = score;
	}

	/**
	 * @return an iterable with the positions of squares not marked
	 */
	public Iterable<Point> getUnmarkedSquares() {
		return unmarkedSquares;
	}

	/**
	 * Returns the score of the player
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}

}