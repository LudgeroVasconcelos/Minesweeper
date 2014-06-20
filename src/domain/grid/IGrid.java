package domain.grid;

import javax.naming.OperationNotSupportedException;

public interface IGrid {

	public void start(int x, int y);
	
	public void newGame();
	
	public boolean endGame();
	
	public void reveal(int x, int y);
	
	public boolean isRevealed(int x, int y);
	
	public void toggleMark(int x, int y);
	
	public boolean isMarked(int x, int y);
	
	public boolean isMined(int x, int y);
	
	public int getNumOfMinesAround(int x, int y) throws OperationNotSupportedException;

	public int getColumns();
	
	public int getRows();
}
