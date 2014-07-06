package domain.events;

public abstract class SquareEvent extends Event{

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
