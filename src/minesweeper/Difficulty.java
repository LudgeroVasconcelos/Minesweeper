package minesweeper;

public enum Difficulty {
	BEGINNER ("Beginner", 9, 9, 10),
	INTERMEDIATE ("Intermediate", 16, 16, 40),
	EXPERT ("Expert", 16, 30, 99);
	
	private final String difficulty;
	private final int rows;
	private final int columns;
	private final int mines;
	
	private Difficulty(String difficulty, int rows, int columns, int mines){
		this.difficulty = difficulty;
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
	}
	
	public String getDifficulty(){
		return difficulty;
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getColumns(){
		return columns;
	}
	
	public int getMines(){
		return mines;
	}
}
