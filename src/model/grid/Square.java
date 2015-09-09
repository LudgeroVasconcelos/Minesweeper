package model.grid;

import java.util.Observable;

/**
 * This class provides the operations that all extending squares must have.
 * 
 * @author Ludgero
 * 
 */
public abstract class Square extends Observable {

	private boolean revealed;
	private SquareState currentState;
	
	public Square(){
		currentState = SquareState.EMPTY;
	}

	/**
	 * Reveals a square.
	 */
	public void reveal() {
		revealed = true;
	}

	/**
	 * Checks whether this square is marked or not.
	 * A square is said to be marked if it is either flagged or question-marked.
	 * 
	 * @return true if this square is marked
	 */
	public boolean isMarked() {
		return currentState != SquareState.EMPTY;
	}
	
	/**
	 * Checks whether this square is flagged.
	 * 
	 * @return true if this square is flagged
	 */
	public boolean isFlagged() {
		return currentState == SquareState.FLAGGED;
	}

	/**
	 * Checks whether this square is revealed.
	 * 
	 * @return true if this square is revealed
	 */
	public boolean isRevealed() {
		return revealed;
	}
	
	public void toggle(boolean questionMarkActive){
		SquareState[] states = SquareState.values();
		int next = (currentState.ordinal() + 1) % (questionMarkActive ? states.length : states.length - 1);

		currentState = states[next];
	}

	public SquareState getState() {
		return currentState;
	}
}
