package domain.reveal;

import java.awt.Point;

import domain.grid.IGrid;

public abstract class AbstractReveal {

	IGrid grid;

	public AbstractReveal(IGrid grid) {
		super();
		this.grid = grid;
	}
	
	/**
	 * 
	 * @param x The x position of the square clicked by the user
	 * @param y the y position of the square clicked by the user
	 * @requires x and y to be a no mined square
	 * @return an iterable of squares that should be revealed 
	 */
	public abstract Iterable<Point> getSquaresToReveal(int x, int y);
}
