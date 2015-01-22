package ui.controller.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import minesweeper.Difficulty;
import ui.view.swing.UiFacade;
import domain.DomainFacade;
import domain.events.ClearEvent;
import domain.events.ResizeEvent;

/**
 * The controller for the menu operations.
 * 
 * @author Ludgero
 * 
 */
public class MenuController implements Observer, ActionListener {

	private DomainFacade domainHandler;
	private UiFacade uiHandler;

	/**
	 * Constructs and initializes a new controller for the menu operations.
	 * 
	 * @param domainHandler
	 *            The model component of the mvc architecture pattern
	 * @param uiHandler
	 *            The view component of the mvc architecture pattern
	 */
	public MenuController(DomainFacade domainHandler, UiFacade uiHandler) {
		this.domainHandler = domainHandler;
		this.uiHandler = uiHandler;
	}

	public void addObservers() {
		domainHandler.addObserver(this);
		uiHandler.addListener(this);
	}

	/**
	 * Resizes the game window to fit the given number of rows and columns.
	 * 
	 * @param rows
	 * @param columns
	 */
	private void resizeGrid(int rows, int columns) {
		uiHandler.resizeGrid(rows, columns);
	}

	/**
	 * Clears the game window.
	 */
	private void clearView() {
		uiHandler.clearGrid();
	}

	/**
	 * Sets a new Difficulty
	 * 
	 * @param diffText
	 *            The difficulty as a String
	 */
	private void setDifficulty(String diffText) {

		Difficulty diff = Difficulty.valueOf(diffText);
		domainHandler.setDifficulty(diff);
	}

	@Override
	public void update(Observable obs, Object hint) {
		if (hint instanceof ResizeEvent) {

			ResizeEvent re = (ResizeEvent) hint;
			resizeGrid(re.getRows(), re.getColumns());

		} else if (hint instanceof ClearEvent) {
			clearView();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		case "new":
			domainHandler.clearGrid();
			break;

		case "difficulty":
			setDifficulty(((JMenuItem) e.getSource()).getText().toUpperCase());
			break;

		case "quit":
			System.exit(0);
			break;
		}
	}
}
