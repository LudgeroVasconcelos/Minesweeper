package minesweeper;

import java.io.FileInputStream;
import java.util.Properties;

public enum MineProperties {
	INSTANCE;
	
	public final int ROWS;
	public final int COLUMNS;
	public final int NUMBER_OF_MINES;
	
	private Properties mineProperties;
	
	MineProperties(){
		String propertiesFileName = "minesweeper.properties";
		mineProperties = new Properties();
		try {
			mineProperties.load(new FileInputStream(propertiesFileName));
		} catch (Exception e) {
			// It was not able to load properties file.
			// Bad luck, proceed with the default values
		}
		
		ROWS = parseInt("rows", 20);
		COLUMNS = parseInt("columns", 20);
		NUMBER_OF_MINES = parseInt("number_of_mines", 20);
	}

	private int parseInt(String property, int defaultValue) {
		try {
			return Integer.parseInt(mineProperties.getProperty(property));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
