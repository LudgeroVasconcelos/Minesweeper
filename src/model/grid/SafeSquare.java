package model.grid;

/**
 * This class represents a safe square.
 * 
 * @author Ludgero
 * 
 */
public class SafeSquare extends Square {

	private final int minesAround;

	/**
	 * Constructs and initializes a new safe square.
	 * 
	 * @param minesAround
	 *            The number of mines surrounding the square to be created
	 */
	public SafeSquare(int minesAround) {
		this.minesAround = minesAround;
	}

	/**
	 * @return The number of mines around this square
	 */
	public int getNumOfMinesAround() {
		return minesAround;
	}

}