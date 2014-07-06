package domain.grid;

import java.util.Observable;

abstract class Square extends Observable{

	private boolean marked;
	private boolean revealed;
	
	public void reveal(){
		revealed = true;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public boolean isRevealed(){
		return revealed;
	}

	public void toggleMark() {
		marked = !marked;
	}
	 
}
