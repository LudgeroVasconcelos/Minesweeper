package domain;

import java.awt.Point;
import java.util.Observer;

import domain.grid.Grid;
import domain.grid.IGrid;
import domain.random.FullyRandom;
import domain.reveal.AbstractReveal;
import domain.reveal.RevealUntilNum;

// esta classe tem de ser publica n�o �? E a grid?
public class Minesweeper implements MineFacade {

	private IGrid grid;
	private AbstractReveal ar;

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;
	private static final int NUMBER_OF_MINES = 80;

	public Minesweeper() {
		grid = new Grid(new FullyRandom(), ROWS, COLUMNS, NUMBER_OF_MINES);
		ar = new RevealUntilNum(grid);
	}

	@Override
	public void clearGrid() {
		grid.clearGrid();
	}

	@Override
	public void reveal(int x, int y) {
		if (!grid.isFilled()) {
			grid.fill(x, y);
		}

		// ser� que devo ver primeiro se o quadrado x y � uma mina ou se est�
		// marcado ou deixo o revealUntilNum respons�vel por isso?
		Iterable<Point> squares = ar.getSquaresToReveal(x, y);

		for (Point p : squares) {
			grid.reveal(p.x, p.y);
		}
	}

//	not needed
//	
//	@Override
//	public boolean isRevealed(int x, int y) {
//		return grid.isRevealed(x, y);
//	}

//	not needed
//	
//	@Override
//	public boolean isMarked(int x, int y) {
//		return grid.isMarked(x, y);
//	}

	@Override
	public void toggleMark(int x, int y) {
		// aqui deveria lan�ar uma excep�ao quando n�o desse para fazer
		// toggle????? para o controller ficar a saber porque � que n�o deu. Ou
		// apenas deve estar escrito nos contratos?
		if (grid.isFilled())
			grid.toggleMark(x, y);
	}

	@Override
	public void addObserver(Observer observer) {
		grid.addObserver(observer);
	}

}
