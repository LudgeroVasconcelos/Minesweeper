package domain.events;

import java.awt.Point;

public class GameWonEvent extends Event {

	private Iterable<Point> unmarkedSquares;

	public GameWonEvent(Iterable<Point> unmarkedSquares) {
		this.unmarkedSquares = unmarkedSquares;
	}

	/**
	 * @return the unmarkedSquares
	 */
	public Iterable<Point> getUnmarkedSquares() {
		return unmarkedSquares;
	}
}