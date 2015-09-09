package view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.shephertz.app42.paas.sdk.java.game.Game.Score;

import minesweeper.MineProperties;
import model.Difficulty;
import model.events.CustomDifficultyListener;
import model.grid.SquareState;
import view.UiFacade;

/**
 * The main UI class.
 * 
 * @author Ludgero
 * 
 */
@SuppressWarnings("serial")
public class MainWindow extends UiFacade {

	private GridPanel grid;
	private TopPanel top;
	private Menu menu;
	private RankingsFrame rankings;
	private CustomDifFrame custom;

	@Override
	public void start() {
		createGUI();
		addComponents();
	}

	private void createGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// just do nothing
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		setTitle("Minesweeper");
		setIconImage(MineProperties.INSTANCE.MINE_IMAGE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addComponents() {
		Difficulty dif = Difficulty.getCurrentDifficulty();

		grid = new GridPanel(dif.getRows(), dif.getColumns());
		top = new TopPanel(dif.getMines());
		menu = new Menu(dif);
		rankings = new RankingsFrame(grid);
		custom = new CustomDifFrame(this);

		setJMenuBar(menu);
		add(grid, BorderLayout.CENTER);
		add(top, BorderLayout.NORTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void revealButtons(Iterable<Entry<Point, Integer>> revealedSquares) {
		grid.revealButtons(revealedSquares);
	}

	@Override
	public void setState(int x, int y, SquareState state, int flaggedMines) {
		grid.setState(x, y, state);
		top.setRemainingMines(Difficulty.getCurrentDifficulty().getMines() - flaggedMines);
	}

	@Override
	public void clearGrid() {
		grid.clear();
		top.clear();
	}

	@Override
	public void gameLost(int x, int y, Iterable<Point> mines, Iterable<Point> mistakenMarks) {
		grid.gameOver(x, y, mines, mistakenMarks);
		top.gameOver();
	}

	@Override
	public void gameWon(Iterable<Point> unmarkedSquares, int score) {
		grid.gameWon(unmarkedSquares);
		top.gameWon();
		
		if(Difficulty.getCurrentDifficulty() != Difficulty.CUSTOM)
			rankings.showSubmitScoreFrame(score);
	}

	@Override
	public void setTime(int time) {
		top.setTime(time);
	}

	@Override
	public void mousePressed() {
		top.mousePressed();
	}

	@Override
	public void mouseReleased() {
		top.mouseReleased();
	}

	@Override
	public void resizeGrid(int rows, int columns) {
		menu.changeSelection(Difficulty.getCurrentDifficulty());
		grid.resize(rows, columns);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void addGameListener(EventListener listener) {
		grid.addMouseListener((MouseListener) listener);
		menu.addMenuGameListener((ActionListener) listener);
		top.addClearListener((ActionListener) listener);
		custom.addPropertyListener((CustomDifficultyListener) listener);
	}

	@Override
	public void addLeaderboardListener(EventListener listener) {
		rankings.addSubmitScoreListener((ActionListener) listener);
		menu.addMenuRankingsListener((ActionListener) listener);
	}

	@Override
	public void showRankings(int userPos, String username, int score, String level, Iterable<Score> scoreList) {
		rankings.showRankings(userPos, username, score, level, scoreList);
	}

	@Override
	public void showErrorMessage() {
		JOptionPane.showMessageDialog(this,
				"The operation could not be completed.\nPlease check your internet connection and try again.",
				"Network error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showCustomDifficultyOptions() {
		custom.setLocationRelativeTo(this);
		custom.setVisible(true);
	}
}
