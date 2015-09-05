package minesweeper;

import com.shephertz.app42.paas.sdk.java.App42API;

import model.ILeaderboardFacade;
import model.IMineFacade;
import model.LeaderboardFacade;
import model.MineFacade;
import view.StartupUi;

/**
 * Main class. All the magic starts here.
 * 
 * @author Ludgero
 * 
 */
public class Main {

	public static void main(String[] args) {
		App42API.apiKey = "819e101b1fab77e1be93d60cfeb8a4a6a7d7529ad5f5c0a8124973a24702cf1c";
		App42API.secretKey = "9e50196bc6aef28c14d0ee085acdbb20268277fc0114b6122c225651db36505e";

		App42API.initialize(App42API.apiKey, App42API.secretKey);

		// create model
		IMineFacade mineFacade = new MineFacade();
		ILeaderboardFacade leaderFacade = new LeaderboardFacade();

		// create UI
		StartupUi.run(mineFacade, leaderFacade);
	}
}
