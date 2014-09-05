package domain.grid;

import java.util.Observer;

public interface IGrid {

	public void fill(int x, int y);

	public boolean isFilled();

	public void clearGrid();

	public void reveal(int x, int y);

	public boolean isRevealed(int x, int y);

	public void toggleMark(int x, int y);

	public boolean isMarked(int x, int y);

	public void addObserver(Observer observer);
}
