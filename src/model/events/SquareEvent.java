package model.events;

/**
 * Represents the type of events that must specify the square on which the event
 * was fired.
 * 
 * @author Ludgero
 * 
 */
public abstract class SquareEvent extends Event {

	private final int x, y;

	public SquareEvent(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

}
