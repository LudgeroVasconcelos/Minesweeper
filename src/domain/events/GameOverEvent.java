package domain.events;

import java.awt.Point;

public class GameOverEvent extends SquareEvent{

	Iterable<Point> listOfMines;
	
	public GameOverEvent(int x, int y, Iterable<Point> list) {
		super(x, y);
		this.listOfMines = list;
	}
	
	public Iterable<Point> getListOfMines(){
		return listOfMines;
	}
}
