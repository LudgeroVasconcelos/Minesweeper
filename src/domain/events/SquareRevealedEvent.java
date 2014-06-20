package domain.events;

public class SquareRevealedEvent extends SquareEvent{

	private int numMinesAround;
	
	public SquareRevealedEvent(int x,int y, int numMinesAround){
		super(x, y);
		this.numMinesAround = numMinesAround;
	}
	
	public int getNumMinesAround(){
		return numMinesAround;
	}
}
