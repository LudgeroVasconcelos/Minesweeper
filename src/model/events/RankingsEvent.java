package model.events;

import com.shephertz.app42.paas.sdk.java.game.Game.Score;

public class RankingsEvent {

	private int userPos;
	private String username;
	private int score;
	private String level;
	private Iterable<Score> rankings;

	public RankingsEvent(int userPos, String username, int score, String level, Iterable<Score> rankings) {
		super();
		this.userPos = userPos;
		this.username = username;
		this.score = score;
		this.level = level;
		this.rankings = rankings;
	}

	/**
	 * @return the userPos
	 */
	public int getUserPos() {
		return userPos;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the rankings
	 */
	public Iterable<Score> getRankings() {
		return rankings;
	}

	public String getLevel() {
		return level;
	}
}
