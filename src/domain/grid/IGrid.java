package domain.grid;

import java.util.Observer;

import javax.naming.OperationNotSupportedException;

public interface IGrid {

	public void fill(int x, int y);
	
	public boolean isFilled();
	
	public void clearGrid();
	
	/**
	 * @param x
	 * @param y
	 */
	public void reveal(int x, int y);
	
	public boolean isRevealed(int x, int y);
	
	public void toggleMark(int x, int y);
	
	public boolean isMarked(int x, int y);
	
	public int getNumOfMinesAround(int x, int y) throws OperationNotSupportedException;

	public int getColumns();
	
	public int getRows();
	
	public void addObserver(Observer observer);
}
