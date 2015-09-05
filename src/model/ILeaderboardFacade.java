package model;

import java.util.Observer;

public interface ILeaderboardFacade {

	public void submitScore(String username, int score) throws Exception;
	
	public void getTopRankings(String level) throws Exception;

	public void addObserver(Observer observer);
}
