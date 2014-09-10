package domain.events;

public class ToggleMarkEvent extends SquareEvent{

	private int flaggedMines;
	
	public ToggleMarkEvent(int x, int y, int flaggedMines) {
		super(x, y);
		this.flaggedMines = flaggedMines;
	}

	public int getNumberOfFlaggedMines(){
		return flaggedMines;
	}
}
