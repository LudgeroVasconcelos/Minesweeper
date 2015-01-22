package ui.controller.swing;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import minesweeper.Util;
import ui.view.swing.UiFacade;
import domain.DomainFacade;
import domain.events.GameOverEvent;
import domain.events.GameWonEvent;
import domain.events.RevealEvent;
import domain.events.SquareEvent;
import domain.events.StartGameEvent;
import domain.events.ToggleMarkEvent;

/**
 * The Controller for the main game operations.
 * 
 * @author Ludgero
 * 
 */
public class GridController extends MouseAdapter implements Observer {

	private DomainFacade domainHandler;
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
	public GridController(DomainFacade domainHandler, UiFacade uiHandler) {
		this.domainHandler = domainHandler;
		this.uiHandler = uiHandler;
	}

	/**
	 * Adds this controller as an observer that will listen to notifications
	 * from the model and view components.
	 */
	public void addObservers() {
		domainHandler.addObserver(this);
		uiHandler.addListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Point p = Util.getPoint(e.getX(), e.getY());
			domainHandler.toggleMark(p.x, p.y);

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
	 * Paints a flag over the square at (x, y) if the square is flagged,
	 * otherwise, the flag is removed. Updates the number of mines left.
	 * 
	 * @param x
	 *            The x coordinate of the clicked square
	 * @param y
	 *            The y coordinate of the clicked square
	 * @param flagged
	 *            Is this square flagged?
	 * @param flaggedMines
	 *            The number of flagged squares
	 */
	private void toggleFlag(int x, int y, boolean flagged, int flaggedMines) {
		uiHandler.toggleFlag(x, y, flagged, flaggedMines);
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
	private void gameOver(int x, int y, Iterable<Point> mines,
			Iterable<Point> mistakenMarks) {
		uiHandler.gameOver(x, y, mines, mistakenMarks);
	}

	/**
	 * Starts the timer.
	 */
	private void startGame() {
		uiHandler.startGame();
	}

	/**
	 * Configures the game window to a won state
	 * 
	 * @param unmarkedSquares
	 */
	private void gameWon(Iterable<Point> unmarkedSquares) {
		uiHandler.gameWon(unmarkedSquares);
	}

	@Override
	public void update(Observable obj, Object hint) {

		if (hint instanceof SquareEvent) {
			if (hint instanceof ToggleMarkEvent) {
				ToggleMarkEvent tme = (ToggleMarkEvent) hint;
				toggleFlag(tme.getX(), tme.getY(), tme.isMarked(),
						tme.getNumberOfFlaggedMines());

			} else if (hint instanceof GameOverEvent) {
				GameOverEvent goe = (GameOverEvent) hint;
				gameOver(goe.getX(), goe.getY(), goe.getMines(),
						goe.getMistakenMarks());
			}
		} else if (hint instanceof RevealEvent) {
			RevealEvent nsre = (RevealEvent) hint;
			revealButtons(nsre.getRevealedSquares());
		} else if (hint instanceof StartGameEvent) {
			startGame();
		} else if (hint instanceof GameWonEvent) {
			GameWonEvent gwe = (GameWonEvent) hint;
			gameWon(gwe.getUnmarkedSquares());
		}
	}
}
