package domain.fill;

import domain.grid.Square;


public interface IFill {

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Square[][] fillGrid(int x, int y);
}
