package controller.swing;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;

import model.ILeaderboardFacade;
import model.events.RankingsEvent;
import view.UiFacade;

public class LeaderboardController implements ActionListener, Observer {

	ILeaderboardFacade leaderHandler;
	UiFacade uiHandler;

	public LeaderboardController(ILeaderboardFacade leaderHandler, UiFacade uiHandler) {
		this.leaderHandler = leaderHandler;
		this.uiHandler = uiHandler;
	}

	public void addObservers() {
		leaderHandler.addObserver(this);
		uiHandler.addLeaderboardListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch (action) {
		case "submit score":
			submitScore(((String) e.getSource()), e.getID());
			break;
		case "rankings":
			showRankings(((JMenuItem) e.getSource()).getText());
		}
	}

	private void submitScore(String username, int score) {
		uiHandler.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			leaderHandler.submitScore(username, score);
		} catch (Exception e) {
			resolveException();
		}
	}

	private void showRankings(String level) {
		uiHandler.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			leaderHandler.getTopRankings(level);
		} catch (Exception e) {
			e.printStackTrace();
			resolveException();
		}
	}

	private void resolveException() {
		uiHandler.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		uiHandler.showErrorMessage();
	}

	@Override
	public void update(Observable obj, Object hint) {
		RankingsEvent re = (RankingsEvent) hint;

		uiHandler.showRankings(re.getUserPos(), re.getUsername(), re.getScore(), re.getLevel(), re.getRankings());
		uiHandler.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

}
