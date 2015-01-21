package ui.controller.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import minesweeper.Difficulty;
import ui.view.swing.UiFacade;
import domain.DomainFacade;
import domain.events.ResizeEvent;

/**
 * The controller for the menu operations.
 * 
 * @author Ludgero
 * 
 */
public class MenuController implements Observer {

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
		uiHandler.addMenuListeners(quitListener(), diffListener());
	}

	/**
	 * Creates and returns a listener that will be triggered when a new
	 * difficulty is chosen.
	 * 
	 * @return The specified listener
	 */
	private ActionListener diffListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem jmi = (JMenuItem) e.getSource();

				Difficulty diff;

				switch (jmi.getText()) {
				case "Beginner":
					diff = Difficulty.BEGINNER;
					break;
				case "Intermediate":
					diff = Difficulty.INTERMEDIATE;
					break;
				default:
					diff = Difficulty.EXPERT;
				}
				domainHandler.setDifficulty(diff);
			}
		};
	}

	/**
	 * Creates and returns a listener that will be triggered when the user quits
	 * the game.
	 * 
	 * @return The specified listener
	 */
	private ActionListener quitListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
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

	@Override
	public void update(Observable obs, Object hint) {
		if (hint instanceof ResizeEvent) {
			ResizeEvent re = (ResizeEvent) hint;

			resizeGrid(re.getRows(), re.getColumns());
		}
	}
}
