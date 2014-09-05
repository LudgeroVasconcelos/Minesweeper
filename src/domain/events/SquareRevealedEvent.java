package domain.events;

import java.awt.Point;
import java.util.Map.Entry;

public class SquareRevealedEvent extends SquareEvent {

	private Iterable<Entry<Point, Integer>> revealedSquares;

	public SquareRevealedEvent(int x, int y,
			Iterable<Entry<Point, Integer>> revealedSquares) {
		super(x, y);
		this.revealedSquares = revealedSquares;
	}

	public Iterable<Entry<Point, Integer>> getRevealedSquares() {
		return revealedSquares;
	}

}
