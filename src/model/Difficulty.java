package model;

import minesweeper.MineProperties;

/**
 * This enumerator specifies the levels of difficulty of the game.
 * 
 * @author Ludgero
 * 
 */
public enum Difficulty {
	BEGINNER("Beginner"), INTERMEDIATE("Intermediate"), EXPERT("Expert"), CUSTOM("Custom");

	private final String name;

	private static Difficulty current;

	private static int customRows;
	private static int customColumns;
	private static int customMines;

	Difficulty(String name) {
		this.name = name;
	}

	public String getDifficultyName() {
		return name;
	}

	public static void setDifficulty(Difficulty dif) {
		current = dif;
	}

	public static void setDifficulty(int rows, int columns, int mines) {
		current = Difficulty.CUSTOM;
		customRows = rows;
		customColumns = columns;
		customMines = mines;
	}

	public static Difficulty getCurrentDifficulty() {
		return current;
	}

	public int getRows() {

		switch (this) {
		case BEGINNER:
			return MineProperties.INSTANCE.ROWS_BEGINNER;
		case INTERMEDIATE:
			return MineProperties.INSTANCE.ROWS_INTERMEDIATE;
		case EXPERT:
			return MineProperties.INSTANCE.ROWS_EXPERT;
		default:
			return customRows;
		}
	}

	public int getColumns() {

		switch (this) {
		case BEGINNER:
			return MineProperties.INSTANCE.COLUMNS_BEGINNER;
		case INTERMEDIATE:
			return MineProperties.INSTANCE.COLUMNS_INTERMEDIATE;
		case EXPERT:
			return MineProperties.INSTANCE.COLUMNS_EXPERT;
		default:
			return customColumns;
		}
	}

	public int getMines() {

		switch (this) {
		case BEGINNER:
			return MineProperties.INSTANCE.NUMBER_OF_MINES_BEGINNER;
		case INTERMEDIATE:
			return MineProperties.INSTANCE.NUMBER_OF_MINES_INTERMEDIATE;
		case EXPERT:
			return MineProperties.INSTANCE.NUMBER_OF_MINES_EXPERT;
		default:
			return customMines;
		}
	}
}