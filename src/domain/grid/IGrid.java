package domain.grid;

import java.util.Observer;

import minesweeper.Difficulty;

public interface IGrid {

	public void fill(int x, int y);

	public boolean isFilled();

	public void clearGrid();

	public void reveal(int x, int y);

	public void toggleMark(int x, int y);

	public void addObserver(Observer observer);

	public boolean hasEnded();

	public void setDifficulty(Difficulty diff);
}
