package model;

import java.util.Observer;

import minesweeper.MineProperties;
import minesweeper.Util;
import model.grid.Grid;
import model.grid.fill.FillRandom;
import model.grid.fill.IFill;
import model.grid.reveal.IReveal;
import model.grid.reveal.RevealUntilNum;

/**
 * This class implements the game operations.
 * 
 * @author Ludgero
 * 
 */
public class MineFacade implements IMineFacade {

	private Grid grid;

	/**
	 * Constructs a new minesweeper game
	 */
	public MineFacade() {
		Difficulty dif = MineProperties.INSTANCE.DEFAULT_DIFFICULTY;
		Difficulty.setDifficulty(dif);

		IFill filler = new FillRandom(dif.getRows(), dif.getColumns(), dif.getMines());

		IReveal revealer = new RevealUntilNum();

		MineTimer timer = new MineTimer();

		grid = new Grid(filler, revealer, timer);
	}

	@Override
	public void clearGrid() {
		grid.clearGrid();
	}

	@Override
	public void reveal(int x, int y) {
		if (Util.isValid(x, y)) {

			if (!grid.isFilled())
				grid.fill(x, y);

			if (!grid.gameHasEnded())
				grid.reveal(x, y);
		}
	}

	@Override
	public void toggleMark(int x, int y) {
		if (grid.isFilled() && !grid.gameHasEnded())
			grid.toggleMark(x, y);
	}

	@Override
	public void addObserver(Observer observer) {
		grid.addObserver(observer);
	}

	@Override
	public void setDifficulty(Difficulty diff) {
		if (diff != Difficulty.getCurrentDifficulty()){
			Difficulty.setDifficulty(diff);
			grid.setDifficulty(diff);
		}
	}
	
	@Override
	public void setDifficulty(int rows, int columns, int mines) {
		Difficulty.setDifficulty(rows, columns, mines);
		grid.setDifficulty(rows, columns, mines);
	}

	public int getCurrentTime() {
		return grid.getCurrentTime();
	}
}
