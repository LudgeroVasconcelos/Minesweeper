package ui.controller.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import minesweeper.Difficulty;
import ui.view.swing.MineFrame;
import domain.MineFacade;
import domain.events.ResizeEvent;

public class MenuController implements Observer {

	private MineFacade mineHandler;
	private MineFrame mineFrame;

	public MenuController(MineFacade mineHandler, MineFrame mineFrame) {
		this.mineHandler = mineHandler;
		this.mineFrame = mineFrame;
	}

	public void addObservers() {
		mineHandler.addObserver(this);
		mineFrame.addMenuListeners(quitListener(), diffListener());
	}

	private ActionListener diffListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem jmi = (JMenuItem) e.getSource();

				Difficulty difficulty;
				
				switch (jmi.getText()) {
				case "Beginner":
					difficulty = Difficulty.BEGINNER;
					break;
				case "Intermediate":
					difficulty = Difficulty.INTERMEDIATE;
					break;
				default:
					difficulty = Difficulty.EXPERT;
				}
				mineHandler.setDifficulty(difficulty);
			}
		};
	}

	private ActionListener quitListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
	}

	private void resizeGrid(int rows, int columns) {
		mineFrame.resizeGrid(rows, columns);
	}

	@Override
	public void update(Observable obs, Object hint) {
		if (hint instanceof ResizeEvent) {
			ResizeEvent re = (ResizeEvent) hint;

			resizeGrid(re.getRows(), re.getColumns());
		}
	}
}
