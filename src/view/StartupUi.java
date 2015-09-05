package view;

import controller.swing.GameController;
import controller.swing.LeaderboardController;
import controller.swing.TimeController;
import model.ILeaderboardFacade;
import model.IMineFacade;
import view.swing.MainWindow;

public class StartupUi {

	public static void run(final IMineFacade mineFacade, final ILeaderboardFacade leaderFacade) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// create UI
				UiFacade uiFacade = new MainWindow();
				uiFacade.start();

				// create controllers
				GameController gameController = new GameController(mineFacade, uiFacade);

				TimeController timeController = new TimeController(mineFacade, uiFacade);

				LeaderboardController leaderController = new LeaderboardController(leaderFacade, uiFacade);

				// establish connection between mvc components
				gameController.addObservers();
				timeController.addObservers();
				leaderController.addObservers();
			}
		});

	}
}
