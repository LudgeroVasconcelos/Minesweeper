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

public class MineController implements Observer{

	private MineFacade mineHandler;	// domain
	private MineFrame mineFrame; 	// ui

	public MineController(MineFacade mineHandler, MineFrame mineFrame) {
		this.mineHandler = mineHandler;
		this.mineFrame = mineFrame;
	}

	public void addObservers() {
		mineHandler.addObserver(this);
		mineFrame.addSquaresListener(squaresListener());
		mineFrame.addClearListener(clearGrid());
	}
	
	private ActionListener clearGrid() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mineHandler.clearGrid();
			}
		};
	}


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
			
			public Point getPoint(int coordX, int coordY){
				int y = coordX / MineProperties.INSTANCE.BUTTON_WIDTH;
				int x = coordY / MineProperties.INSTANCE.BUTTON_HEIGHT;
				return new Point(x, y);
			}
		};
	}

	private void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		mineFrame.revealButtons(revealedSquares);
	}

	private void toggleFlag(int x, int y, int flaggedMines) {
		mineFrame.toggleFlag(x, y, flaggedMines);
	}
	
	private void clearView(){
		mineFrame.clearGrid();
	}

	private void gameOver(int x, int y, Iterable<Point> mines) {
		mineFrame.gameOver(x, y, mines);
	}
	

	private void startGame() {
		mineFrame.startGame();
	}

	private void gameWon(Iterable<Point> unmarkedSquares) {
		mineFrame.gameWon(unmarkedSquares);
	}

	@Override
	public void update(Observable obj, Object hint) {

		if (hint instanceof SquareEvent) {
			 if (hint instanceof ToggleMarkEvent) {
				ToggleMarkEvent tme = (ToggleMarkEvent) hint;
				toggleFlag(tme.getX(), tme.getY(), tme.getNumberOfFlaggedMines());
				
			} else if (hint instanceof GameOverEvent) {
				GameOverEvent goe = (GameOverEvent) hint;
				gameOver(goe.getX(), goe.getY(), goe.getMines());
			}
		}
		else if (hint instanceof SquareRevealedEvent) {
			SquareRevealedEvent nsre = (SquareRevealedEvent) hint;
			revealButtons(nsre.getRevealedSquares());
		}
		else if (hint instanceof ClearEvent) {
			clearView();
		}
		else if (hint instanceof StartGameEvent){
			startGame();
		}
		else if (hint instanceof GameWonEvent){
			GameWonEvent gwe = (GameWonEvent) hint;
			gameWon(gwe.getUnmarkedSquares());
		}
	}
}
