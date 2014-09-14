package domain;

import java.util.Observer;

import minesweeper.Difficulty;

public interface MineFacade {
	
	public void clearGrid();
	
	public void reveal(int x, int y);
	
	public void toggleMark(int x, int y);

	public void addObserver(Observer observer);

	public void setDifficulty(Difficulty difficulty);
}
