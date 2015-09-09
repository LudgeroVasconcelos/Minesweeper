package view.swing;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Difficulty;

/**
 * This class represents the game menu.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class Menu extends JMenuBar {

	JMenuItem gameItems[];
	JMenuItem rankingsItems[];

	/**
	 * Constructs the menu and adds some menu items to it.
	 */
	public Menu() {
		gameItems = new JMenuItem[Difficulty.values().length + 2];
		rankingsItems = new JMenuItem[Difficulty.values().length - 1];

		JMenu game = new JMenu("Game");
		JMenu rankings = new JMenu("Rankings");

		game.setMnemonic(KeyEvent.VK_G);
		rankings.setMnemonic(KeyEvent.VK_R);

		// add the 'new' jmenu item to the Game menu
		gameItems[0] = new JMenuItem("New");
		gameItems[0].setActionCommand("new");
		game.add(gameItems[0]);
		game.addSeparator();

		// add the difficulties jmenu items to the Game and Rankings menus
		int index = 0;
		for (Difficulty dif : Difficulty.values()) {
			String difString = dif.getDifficultyName();
			index = dif.ordinal();

			gameItems[index + 1] = new JMenuItem(difString);
			gameItems[index + 1].setActionCommand("difficulty");
			game.add(gameItems[index + 1]);

			// there are no rankings to the custom difficulty
			if (dif != Difficulty.CUSTOM) {
				rankingsItems[index] = new JMenuItem(difString);
				rankingsItems[index].setActionCommand("rankings");
				rankings.add(rankingsItems[index]);
			}
		}

		// add the quit jmenu item to the Game menu
		index += 2;
		game.addSeparator();
		gameItems[index] = new JMenuItem("Quit");
		gameItems[index].setActionCommand("quit");
		game.add(gameItems[index]);

		// finally add the top level menus
		add(game);
		add(rankings);
	}

	/**
	 * Adds a listener for all menu items.
	 * 
	 * @param listener
	 */
	public void addMenuGameListener(ActionListener listener) {
		for (int i = 0; i < gameItems.length; i++) {
			gameItems[i].addActionListener(listener);
		}
	}

	public void addMenuRankingsListener(ActionListener listener) {
		for (int i = 0; i < rankingsItems.length; i++) {
			rankingsItems[i].addActionListener(listener);
		}
	}
}
