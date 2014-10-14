package minesweeper;

import ui.controller.swing.MenuController;
import ui.controller.swing.MineController;
import ui.view.swing.MineFrame;
import domain.MineFacade;
import domain.Minesweeper;

/**
 * Main class. All the magic starts here.
 * 
 * @author Ludgero
 *
 */
public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// create model
				MineFacade mineFacade = new Minesweeper();

				// create view
				MineFrame mineFrame = new MineFrame();

				// create controllers
				MineController mineController = new MineController(mineFacade,
						mineFrame);

				MenuController menuController = new MenuController(mineFacade,
						mineFrame);

				// establish connection between mvc components
				mineController.addObservers();
				menuController.addObservers();
			}
		});

	}
}
