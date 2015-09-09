package view.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
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
public class Menu extends JMenuBar implements ActionListener {

	private static final String GAME_MENU = "Game";
	private static final String RANKINGS_MENU = "Rankings";

	private static final String COMMAND_NEW = "new";
	private static final String COMMAND_DIFFICULTY = "difficulty";
	private static final String COMMAND_QUESTION_MARK = "question";
	private static final String COMMAND_QUIT = "quit";
	private static final String COMMAND_RANKING = "rankings";

	private static final String NEW_NAME = "New";
	private static final String QUESTION_MARK_NAME = "Marks (?)";
	private static final String EXIT_NAME = "Exit";

	JMenuItem gameItems[];
	JMenuItem rankingsItems[];

	/**
	 * Constructs the menu and adds some menu items to it.
	 */
	public Menu(Difficulty startingDif) {
		gameItems = new JMenuItem[Difficulty.values().length + 3];
		rankingsItems = new JMenuItem[Difficulty.values().length - 1];

		JMenu game = new JMenu(GAME_MENU);
		JMenu rankings = new JMenu(RANKINGS_MENU);

		game.setMnemonic(KeyEvent.VK_G);
		rankings.setMnemonic(KeyEvent.VK_R);

		// add the 'new' jmenu item to the Game menu
		gameItems[0] = new JMenuItem(NEW_NAME);
		gameItems[0].setActionCommand(COMMAND_NEW);
		game.add(gameItems[0]);
		game.addSeparator();

		// add the difficulties jmenu items to the Game and Rankings menus
		int index = 0;
		for (Difficulty dif : Difficulty.values()) {
			String difString = dif.getDifficultyName();
			index = dif.ordinal();

			gameItems[index + 1] = new JCheckBoxMenuItem(difString);
			gameItems[index + 1].setActionCommand(COMMAND_DIFFICULTY);
			gameItems[index + 1].addActionListener(this);
			game.add(gameItems[index + 1]);

			// there are no rankings to the custom difficulty
			if (dif != Difficulty.CUSTOM) {
				rankingsItems[index] = new JMenuItem(difString);
				rankingsItems[index].setActionCommand(COMMAND_RANKING);
				rankings.add(rankingsItems[index]);
			}
		}

		gameItems[startingDif.ordinal() + 1].setSelected(true);

		// add the question mark option jmenu item to the Game menu
		index += 2;
		game.addSeparator();
		gameItems[index] = new JCheckBoxMenuItem(QUESTION_MARK_NAME);
		gameItems[index].setActionCommand(COMMAND_QUESTION_MARK);
		game.add(gameItems[index]);

		// add the quit jmenu item to the Game menu
		index++;
		game.addSeparator();
		gameItems[index] = new JMenuItem(EXIT_NAME);
		gameItems[index].setActionCommand(COMMAND_QUIT);
		game.add(gameItems[index]);

		// and finally add the top level menus
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

	/**
	 * Deselects the current selected item and selects the given one.
	 * 
	 * @param dif
	 */
	public void changeSelection(Difficulty dif) {

		for (Difficulty d : Difficulty.values()) {
			int index = d.ordinal() + 1;
			gameItems[index].setSelected(false);
		}

		gameItems[dif.ordinal() + 1].setSelected(true);
	}

	/**
	 * Prevents the selection of a check box menu item.
	 * 
	 * When a check box menu item is clicked, it is automatically selected. The
	 * problem arises when the user clicks on the custom difficulty menu item.
	 * That item is then selected but the user may cancel and continue playing
	 * with the same difficulty. <br>
	 * </br>
	 * See {@link #changeSelection()}
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		changeSelection(Difficulty.getCurrentDifficulty());
	}
}
