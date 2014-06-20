package domain;

import java.util.Observer;

public interface MineFacade {

	public void start(int x, int y);
	
	public void newGame();
	
	public void endGame();
	
	public boolean reveal(int x, int y);
	
	public void addObserver(Observer observer);
	
	public boolean isRevealed(int x, int y);
	
	public boolean isMarked(int x, int y);
	
	public boolean isMined(int x, int y);

	public void toggleMark(int x, int y);
}
