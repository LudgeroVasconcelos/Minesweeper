package minesweeper;

import ui.controller.swing.MenuController;
import ui.controller.swing.MineController;
import ui.view.swing.UiFacade;
import domain.DomainFacade;
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
				DomainFacade domainFacade = new Minesweeper();

				// create view
				UiFacade uiFacade = new UiFacade();

				// create controllers
				MineController mineController = new MineController(domainFacade,
						uiFacade);

				MenuController menuController = new MenuController(domainFacade,
						uiFacade);

				// establish connection between mvc components
				mineController.addObservers();
				menuController.addObservers();
			}
		});

	}
}
