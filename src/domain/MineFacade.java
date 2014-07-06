package domain;

import java.util.Observer;

public interface MineFacade {
	
	public void clearGrid();
	
	public void reveal(int x, int y);
	
	public boolean isRevealed(int x, int y);
	
	public void toggleMark(int x, int y);
	
	public boolean isMarked(int x, int y);

	public void addObserver(Observer observer);
}
