package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Observable;

import com.shephertz.app42.paas.sdk.java.App42API;
import com.shephertz.app42.paas.sdk.java.game.Game;
import com.shephertz.app42.paas.sdk.java.game.Game.Score;
import com.shephertz.app42.paas.sdk.java.game.ScoreBoardService;

import model.events.RankingsEvent;

public class LeaderboardFacade extends Observable implements ILeaderboardFacade {

	private static final int NUM = 100;
	
	
	// keeps the last username and score for each level of difficulty
	private String savedUsernames[] = new String[Difficulty.values().length];
	private int savedScores[] = new int[Difficulty.values().length];
	
	ScoreBoardService scoreBoardService = App42API.buildScoreBoardService();
	
	

	@Override
	public void submitScore(String username, int score) throws Exception {
		if (username.equals(""))
			username = "Anonymous";
		
		int index = Difficulty.getCurrentDifficulty().ordinal();
		savedUsernames[index] = username;
		savedScores[index] = score;
		
		String level = Difficulty.getCurrentDifficulty().getDifficultyName();
		Game g = scoreBoardService.saveUserScore(level, username, new BigDecimal(-score));
		
		if(g.isResponseSuccess()){
			getTopRankings(level);
		} else {
			throw new Exception();
		}
	}
	
	@Override
	public void getTopRankings(String level) throws Exception {
		Game g = scoreBoardService.getTopNRankings(level, NUM);
		
		if(g.isResponseSuccess()){
			ArrayList<Score> rankings = g.getScoreList();
			
			int index = Difficulty.valueOf(level.toUpperCase()).ordinal();
			String username = (String) savedUsernames[index];
			int score = (int) savedScores[index];
			
			int pos = getUserPosition(username, score, rankings);
			
			RankingsEvent re = new RankingsEvent(pos, username, score, level, rankings);
			setChanged();
			notifyObservers(re);
		} else {
			throw new Exception();
		}
	}

	private int getUserPosition(String username, int score, ArrayList<Score> scoreList) {
		if(username == null) return -1;
		
		int index = 0;
		for(Score s : scoreList){
			if(s.getUserName().equals(username) && -s.getValue().intValue() == score)
				return index + 1;
			
			index++;
		}
		return -1;
	}
}
