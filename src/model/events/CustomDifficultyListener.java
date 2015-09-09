package model.events;

import java.util.EventListener;

public interface CustomDifficultyListener extends EventListener {

	public void onCustomDifficultyTriggered(int rows, int columns, int mines);
}
