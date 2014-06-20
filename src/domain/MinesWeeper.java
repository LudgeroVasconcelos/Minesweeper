package domain;

import java.awt.Point;
import java.util.Observer;

import domain.grid.Grid;
import domain.random.FullyRandom;
import domain.reveal.RevealUntilNum;

public class MinesWeeper implements MineFacade {

	Grid grid;
	boolean started;

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;
	private static final int NUMBER_OF_MINES = 80;

	public MinesWeeper() {
		grid = new Grid(new FullyRandom(), ROWS, COLUMNS, NUMBER_OF_MINES);
	}

	@Override
	public void start(int x, int y) {
		started = true;
		grid.start(x, y);
	}

	@Override
	public void newGame() {

	}

	@Override
	public void endGame() {
		started = false;
		grid.endGame();
	}

	@Override
	public boolean reveal(int x, int y) {
		if (!started) {
			start(x, y);
		}

		if (grid.isMarked(x, y))
			return false;

		else if (grid.isMined(x, y)) {
			grid.reveal(x, y);
			endGame();
			return false;
		}

		else {
			RevealUntilNum run = new RevealUntilNum(grid);
			Iterable<Point> squares = run.getSquaresToReveal(x, y);
			
			for(Point p : squares){
				grid.reveal(p.x, p.y);
			}
		}
		return true;
	}

	@Override
	public boolean isRevealed(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMarked(int x, int y) {
		return grid.isMarked(x, y);
	}

	@Override
	public void toggleMark(int x, int y) {
		if(started)
			grid.toggleMark(x, y);
	}

	@Override
	public boolean isMined(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addObserver(Observer observer) {
		grid.addObserver(observer);

	}

}
