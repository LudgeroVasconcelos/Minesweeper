package model.grid;

import java.util.Observable;

/**
 * This class provides the operations that all extending squares must have.
 * 
 * @author Ludgero
 * 
 */
public abstract class Square extends Observable {

	private boolean marked;
	private boolean revealed;

	/**
	 * Reveals a square.
	 */
	public void reveal() {
		revealed = true;
	}

	/**
	 * Checks whether this square is marked or not.
	 * 
	 * @return true if this square is marked
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * Checks whether this square is revealed.
	 * 
	 * @return true if this square is revealed
	 */
	public boolean isRevealed() {
		return revealed;
	}

	/**
	 * Marks this square or removes the mark depending on whether it is marked
	 * or not.
	 */
	public void toggleMark() {
		marked = !marked;
	}
}
