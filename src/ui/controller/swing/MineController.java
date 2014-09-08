package ui.controller.swing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.Observable;

import javax.swing.SwingUtilities;

import ui.view.swing.MineButton;
import ui.view.swing.MineFrame;
import domain.MineFacade;
import domain.events.GameOverEvent;
import domain.events.SquareEvent;
import domain.events.SquareRevealedEvent;
import domain.events.ToggleMarkEvent;

public class MineController implements IMineController {

	MineFacade mineHandler; // domain
	MineFrame mineFrame; // ui

	public MineController(MineFacade mineHandler, MineFrame mineFrame) {
		this.mineHandler = mineHandler;
		this.mineFrame = mineFrame;
	}

	@Override
	public void addObserverToGrid() {
		mineHandler.addObserver(this);
	}

	@Override
	public void addListenersToButtons() {
		mineFrame.addListenersToButtons(reveal(), toggleMark());
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
	public ActionListener reveal() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MineButton button = (MineButton) e.getSource();
				mineHandler.reveal(button.getPosX(), button.getPosY());
			}
		};
	}

	@Override
	public MouseAdapter toggleMark() {
		return new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					MineButton button = (MineButton) e.getSource();
					mineHandler.toggleMark(button.getPosX(), button.getPosY());
				}

			}
		};
	}

	private void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		mineFrame.revealButtons(revealedSquares);
	}

	private void toggleFlag(int x, int y) {
		mineFrame.toggleFlag(x, y);
	}

	private void endGame(int x, int y, Iterable<Point> mines) {
		mineFrame.endGame(x, y, mines);
	}

	@Override
	public void update(Observable obj, Object hint) {

		if (hint instanceof SquareEvent) {
			if (hint instanceof SquareRevealedEvent) {
				SquareRevealedEvent nsre = (SquareRevealedEvent) hint;
				revealButtons(nsre.getRevealedSquares());

			} else if (hint instanceof ToggleMarkEvent) {
				ToggleMarkEvent tme = (ToggleMarkEvent) hint;
				toggleFlag(tme.getX(), tme.getY());

			} else if (hint instanceof GameOverEvent) {
				GameOverEvent goe = (GameOverEvent) hint;
				endGame(goe.getX(), goe.getY(), goe.getMines());
			}
		}
	}
}
