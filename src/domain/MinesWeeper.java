package domain;

import java.awt.Point;
import java.util.Observer;

import minesweeper.MineProperties;
import domain.fill.FillRandom;
import domain.grid.Grid;
import domain.grid.IGrid;
import domain.reveal.AbstractReveal;
import domain.reveal.RevealUntilNum;

public class Minesweeper implements MineFacade {

	private IGrid grid;
	private AbstractReveal ar;

	public Minesweeper() {
		grid = new Grid(new FillRandom(MineProperties.INSTANCE.ROWS,
				MineProperties.INSTANCE.COLUMNS,
				MineProperties.INSTANCE.NUMBER_OF_MINES));
		
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
		
		Iterable<Point> squares = ar.getSquaresToReveal(x, y);

		for (Point p : squares) {
			grid.reveal(p.x, p.y);
		}
	}

	@Override
	public void toggleMark(int x, int y) {
		// aqui deveria lançar uma excepçao quando não desse para fazer
		// toggle????? para o controller ficar a saber porque é que não deu. Ou
		// apenas deve estar escrito nos contratos?
		if (grid.isFilled())
			grid.toggleMark(x, y);
	}

	@Override
	public void addObserver(Observer observer) {
		grid.addObserver(observer);
	}

}
