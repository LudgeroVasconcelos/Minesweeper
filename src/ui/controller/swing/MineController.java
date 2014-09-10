package ui.controller.swing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.Observable;

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

public class MineController implements IMineController {

	MineFacade mineHandler;	// domain
	MineFrame mineFrame; 	// ui

	public MineController(MineFacade mineHandler, MineFrame mineFrame) {
		this.mineHandler = mineHandler;
		this.mineFrame = mineFrame;
	}

	@Override
	public void addObserverToGrid() {
		mineHandler.addObserver(this);
	}

	@Override
	public void addListeners() {
		mineFrame.addSquaresListener(squaresListener());
		mineFrame.addSmileListener(clearGrid());
	}

	@Override
	public ActionListener clearGrid() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mineHandler.clearGrid();
			}
		};
	}

	@Override
	public MouseAdapter squaresListener() {
		return new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				if (SwingUtilities.isRightMouseButton(e)) {
					int y = e.getX() / MineProperties.INSTANCE.BUTTON_WIDTH;
					int x = e.getY() / MineProperties.INSTANCE.BUTTON_HEIGHT;

					mineHandler.toggleMark(x, y);
					
				} else if (SwingUtilities.isLeftMouseButton(e)) {
					int y = e.getX() / MineProperties.INSTANCE.BUTTON_WIDTH;
					int x = e.getY() / MineProperties.INSTANCE.BUTTON_HEIGHT;

					mineHandler.reveal(x, y);
				}
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
			if (hint instanceof SquareRevealedEvent) {
				SquareRevealedEvent nsre = (SquareRevealedEvent) hint;
				revealButtons(nsre.getRevealedSquares());

			} else if (hint instanceof ToggleMarkEvent) {
				ToggleMarkEvent tme = (ToggleMarkEvent) hint;
				toggleFlag(tme.getX(), tme.getY(), tme.getNumberOfFlaggedMines());
				
			} else if (hint instanceof GameOverEvent) {
				GameOverEvent goe = (GameOverEvent) hint;
				gameOver(goe.getX(), goe.getY(), goe.getMines());
			}
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
