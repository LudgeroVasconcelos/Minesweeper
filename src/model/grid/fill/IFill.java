package model.grid.fill;

import model.grid.Square;

/**
 * This interface provides one method to fill the grid. The implementing classes
 * will be responsible for placing the mines and setting the corresponding
 * number of mines surrounding each square.
 * 
 * This interface defines the contracts that all implementing classes must
 * follow as there can be multiple criteria to fill the grid.
 * 
 * @author Ludgero
 * 
 */
public interface IFill {

	/**
	 * Fills the grid with normal and mined squares. All positions must be
	 * initialized.
	 * 
	 * @param x
	 *            The x coordinate of the first clicked square
	 * @param y
	 *            The y coordinate of the first clicked square
	 *            
	 * @return a two-dimensional Square array representing the grid
	 */
	public Square[][] fillGrid(int x, int y);
}
