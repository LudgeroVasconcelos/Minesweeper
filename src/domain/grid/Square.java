package domain.grid;

import java.util.Observable;

public abstract class Square extends Observable{

	private boolean marked;
	private boolean revealed;
	
	private Iterable<Square> neighbors;
	
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
	
	public void setNeighbors(Iterable<Square> squares){
		this.neighbors = squares;
	}
	
	public Iterable<Square> getNeighbors(){
		return neighbors;
	}
	 
}
