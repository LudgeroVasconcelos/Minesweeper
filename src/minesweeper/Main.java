package minesweeper;

import ui.controller.swing.IMineController;
import ui.controller.swing.MineController;
import ui.view.swing.MineFrame;
import domain.MineFacade;
import domain.Minesweeper;

public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// create model
				MineFacade mineFacade = new Minesweeper();

				// create view
				MineFrame mineFrame = new MineFrame(20, 20);

				// create controller
				// (controller -> model) (controller -> view)
				IMineController mineController = new MineController(mineFacade,
						mineFrame);

				// (model -> controller)
				mineController.addObserverToGrid();
				
				// (view -> controller)
				mineController.addListenersToSquares();
			}
		});

	}
}
