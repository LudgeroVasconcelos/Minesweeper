package ui.controller.swing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import minesweeper.MineProperties;
import ui.view.swing.MineFrame;
import domain.MineFacade;
import domain.events.ClearEvent;
import domain.events.GameOverEvent;
import domain.events.GameWonEvent;
import domain.events.SquareEvent;
import domain.events.SquareRevealedEvent;
import domain.events.StartGameEvent;
import domain.events.ToggleMarkEvent;

/**
 * The Controller for the main game operations.
 * 
 * @author Ludgero
 * 
 */
public class MineController implements Observer {

	private MineFacade mineHandler; // domain
	private MineFrame mineFrame; // ui

	/**
	 * Constructs and initializes a new controller that will resolve the
	 * interaction between the given model and view layers for the main game
	 * operations.
	 * 
	 * @param mineHandler
	 *            The model component of the mvc architecture pattern
	 * @param mineFrame
	 *            The view component of the mvc architecture pattern
	 */
	public MineController(MineFacade mineHandler, MineFrame mineFrame) {
		this.mineHandler = mineHandler;
		this.mineFrame = mineFrame;
	}

	/**
	 * Adds this controller as an observer that will listen to notifications
	 * from the model and view components.
	 */
	public void addObservers() {
		mineHandler.addObserver(this);
		mineFrame.addSquaresListener(squaresListener());
		mineFrame.addClearListener(clearGrid());
	}

	/**
	 * A listener that will be triggered when an event to clear the grid is
	 * fired.
	 * 
	 * @return The listener to clear the grid
	 */
	private ActionListener clearGrid() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mineHandler.clearGrid();
			}
		};
	}

	/**
	 * A listener that will be triggered when the user reveals a square.
	 * 
	 * @return The listener as specified
	 */
	private MouseListener squaresListener() {
		return new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					Point p = getPoint(e.getX(), e.getY());
					mineHandler.toggleMark(p.x, p.y);

				} else if (SwingUtilities.isLeftMouseButton(e)) {
					mineFrame.mousePressed();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					Point p = getPoint(e.getX(), e.getY());
					mineHandler.reveal(p.x, p.y);
				}
				mineFrame.mouseReleased();
			}

			/**
			 * Converts mouse coordinates to square coordinates.
			 * 
			 * @param coordX
			 *            The x coordinate to be converted
			 * @param coordY
			 *            The y coordinate to be converted
			 * @return a point (X, Y) with converted coordinates.
			 */
			public Point getPoint(int coordX, int coordY) {
				int y = coordX / MineProperties.INSTANCE.BUTTON_WIDTH;
				int x = coordY / MineProperties.INSTANCE.BUTTON_HEIGHT;
				return new Point(x, y);
			}
		};
	}

	/**
	 * Reveals squares.
	 * 
	 * @param revealedSquares
	 *            The squares to be revealed
	 */
	private void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		mineFrame.revealButtons(revealedSquares);
	}

	/**
	 * Paints a flag over the square at (x, y) if it is not already painted,
	 * otherwise, the flag is erased. Updates the number of mines left.
	 * 
	 * @param x
	 *            The x coordinate of the clicked square
	 * @param y
	 *            The y coordinate of the clicked square
	 * @param flaggedMines
	 *            The number of painted flags
	 */
	private void toggleFlag(int x, int y, int flaggedMines) {
		mineFrame.toggleFlag(x, y, flaggedMines);
	}

	/**
	 * Clears the game window.
	 */
	private void clearView() {
		mineFrame.clearGrid();
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
	 */
	private void gameOver(int x, int y, Iterable<Point> mines) {
		mineFrame.gameOver(x, y, mines);
	}

	/**
	 * Starts the timer.
	 */
	private void startGame() {
		mineFrame.startGame();
	}

	/**
	 * Configures the game window to a won state
	 * 
	 * @param unmarkedSquares
	 */
	private void gameWon(Iterable<Point> unmarkedSquares) {
		mineFrame.gameWon(unmarkedSquares);
	}

	@Override
	public void update(Observable obj, Object hint) {

		if (hint instanceof SquareEvent) {
			if (hint instanceof ToggleMarkEvent) {
				ToggleMarkEvent tme = (ToggleMarkEvent) hint;
				toggleFlag(tme.getX(), tme.getY(),
						tme.getNumberOfFlaggedMines());

			} else if (hint instanceof GameOverEvent) {
				GameOverEvent goe = (GameOverEvent) hint;
				gameOver(goe.getX(), goe.getY(), goe.getMines());
			}
		} else if (hint instanceof SquareRevealedEvent) {
			SquareRevealedEvent nsre = (SquareRevealedEvent) hint;
			revealButtons(nsre.getRevealedSquares());
		} else if (hint instanceof ClearEvent) {
			clearView();
		} else if (hint instanceof StartGameEvent) {
			startGame();
		} else if (hint instanceof GameWonEvent) {
			GameWonEvent gwe = (GameWonEvent) hint;
			gameWon(gwe.getUnmarkedSquares());
		}
	}
}
