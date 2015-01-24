package minesweeper;

import controller.swing.GridController;
import controller.swing.MenuController;
import model.IMineFacade;
import model.MineFacade;
import view.swing.UiFacade;

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
				IMineFacade mineFacade = new MineFacade();

				// create view
				UiFacade uiFacade = new UiFacade();

				// create controllers
				GridController mineController = new GridController(mineFacade,
						uiFacade);

				MenuController menuController = new MenuController(mineFacade,
						uiFacade);

				// establish connection between mvc components
				mineController.addObservers();
				menuController.addObservers();
			}
		});

	}
}
