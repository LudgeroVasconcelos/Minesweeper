package controller.swing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import minesweeper.Util;
import model.Difficulty;
import model.IMineFacade;
import model.events.ClearEvent;
import model.events.CustomDifficultyListener;
import model.events.GameLostEvent;
import model.events.GameWonEvent;
import model.events.ResizeEvent;
import model.events.RevealEvent;
import model.events.SquareEvent;
import model.events.ToggleMarkEvent;
import model.grid.SquareState;
import view.UiFacade;

/**
 * The Controller for the main game operations.
 * 
 * @author Ludgero
 * 
 */
public class GameController extends MouseAdapter implements Observer, ActionListener, CustomDifficultyListener {

	private IMineFacade domainHandler;
	private UiFacade uiHandler;

	/**
	 * Constructs and initializes a new controller that will resolve the
	 * interaction between the given model and view layers for the main game
	 * operations.
	 * 
	 * @param domainHandler
	 *            The model component of the mvc architecture pattern
	 * @param uiHandler
	 *            The view component of the mvc architecture pattern
	 */
	public GameController(IMineFacade domainHandler, UiFacade uiHandler) {
		this.domainHandler = domainHandler;
		this.uiHandler = uiHandler;
	}

	/**
	 * Adds this controller as an observer that will listen to notifications
	 * from the model and view components.
	 */
	public void addObservers() {
		domainHandler.addObserver(this);
		uiHandler.addGameListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Point p = Util.getPoint(e.getX(), e.getY());
			domainHandler.toggle(p.x, p.y);

		} else if (SwingUtilities.isLeftMouseButton(e)) {
			uiHandler.mousePressed();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			Point p = Util.getPoint(e.getX(), e.getY());
			domainHandler.reveal(p.x, p.y);
		}
		uiHandler.mouseReleased();
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

		case "question":
			domainHandler.setToggleMode(((JMenuItem) e.getSource()).isSelected());
			break;

		case "quit":
			System.exit(0);
			break;
		}
	}

	/**
	 * Sets a new difficulty level.
	 * 
	 * @param diffText
	 *            The difficulty as a String
	 */
	private void setDifficulty(String diffText) {
		Difficulty diff = Difficulty.valueOf(diffText);
		
		if(diff == Difficulty.CUSTOM){
			uiHandler.showCustomDifficultyOptions();
		} else {
			domainHandler.setDifficulty(diff);
		}
	}
	
	@Override
	public void onCustomDifficultyTriggered(int rows, int columns, int mines) {
		domainHandler.setDifficulty(rows, columns, mines);
	}

	/**
	 * Reveals squares.
	 * 
	 * @param revealedSquares
	 *            The squares to be revealed
	 */
	private void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		uiHandler.revealButtons(revealedSquares);
	}

	/**
	 * Sets the state of the square at (x, y).
	 * 
	 * @param x
	 *            The x coordinate of the clicked square
	 * @param y
	 *            The y coordinate of the clicked square
	 * @param state
	 *            The state of the square to change
	 * @param flaggedMines
	 *            The number of flagged squares
	 */
	private void setState(int x, int y, SquareState state, int flaggedMines) {
		uiHandler.setState(x, y, state, flaggedMines);
	}

	/**
	 * Configures the game window to a lost state.
	 * 
	 * @param x
	 *            The x coordinate of the exploded mine
	 * @param y
	 *            The y coordinate of the exploded mine
	 * @param mines
	 *            All mines' positions
	 * @param mistakenMarks
	 *            All marked squares that are not mined
	 */
	private void gameLost(int x, int y, Iterable<Point> mines, Iterable<Point> mistakenMarks) {
		uiHandler.gameLost(x, y, mines, mistakenMarks);
	}

	/**
	 * Configures the game window to a won state
	 * 
	 * @param unmarkedSquares
	 *            A container with all squares unmarked
	 * @param score
	 *            The time spent playing
	 */
	private void gameWon(Iterable<Point> unmarkedSquares, int score) {
		uiHandler.gameWon(unmarkedSquares, score);
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

	@Override
	public void update(Observable obj, Object hint) {

		if (hint instanceof SquareEvent) {
			if (hint instanceof ToggleMarkEvent) {
				ToggleMarkEvent tme = (ToggleMarkEvent) hint;
				setState(tme.getX(), tme.getY(), tme.getState(), tme.getNumberOfFlaggedMines());

			} else if (hint instanceof GameLostEvent) {
				GameLostEvent goe = (GameLostEvent) hint;
				gameLost(goe.getX(), goe.getY(), goe.getMines(), goe.getMistakenMarks());
			}
		} else if (hint instanceof RevealEvent) {
			RevealEvent nsre = (RevealEvent) hint;
			revealButtons(nsre.getRevealedSquares());
		} else if (hint instanceof GameWonEvent) {
			GameWonEvent gwe = (GameWonEvent) hint;
			gameWon(gwe.getUnmarkedSquares(), gwe.getScore());
		} else if (hint instanceof ResizeEvent) {
			ResizeEvent re = (ResizeEvent) hint;
			resizeGrid(re.getRows(), re.getColumns());
		} else if (hint instanceof ClearEvent) {
			clearView();
		}
	}
}
