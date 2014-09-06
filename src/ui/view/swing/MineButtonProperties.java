package ui.view.swing;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Singleton design pattern
 * 
 * @author ludgero
 */
public class MineButtonProperties {

	private static MineButtonProperties instance;

	private Image mineImage;
	private Image crossImage;
	private ImageIcon flagIcon;

	private int buttonWidth;
	private int buttonHeight;

	private Color[] colors;

	private MineButtonProperties() {
		buttonWidth = 25;
		buttonHeight = 25;

		setColors();
		loadImages();
	}

	private void setColors() {
		colors = new Color[8];
		colors[0] = new Color(1602765);
		colors[1] = new Color(2330147);
		colors[2] = new Color(16711680);
		colors[3] = new Color(0, 0, 156);
		colors[4] = new Color(139, 35, 35);
		colors[5] = new Color(66, 111, 66);
		colors[6] = new Color(79, 79, 47);
		colors[7] = Color.BLACK;
	}

	private void loadImages() {
		try {
			mineImage = ImageIO.read(getClass().getResource("images/Mine.png"));
			crossImage = ImageIO.read(getClass().getResource(
					"images/Icon_cross.png"));

			Image flagImage = ImageIO.read(getClass().getResource(
					"images/RedFlag.png"));
			flagIcon = new ImageIcon(flagImage.getScaledInstance(
					buttonWidth - 3, buttonHeight - 3, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			System.err.println("Could not load image");
			// bad luck, no images will be shown.
		}
	}

	public static MineButtonProperties getInstance() {
		if (instance == null)
			instance = new MineButtonProperties();

		return instance;
	}

	public Color getColor(int n) {
		return colors[--n];
	}

	public ImageIcon getFlagIcon() {
		return flagIcon;
	}

	public Image getMineImage() {
		return mineImage;
	}

	public Image getCrossImage() {
		return crossImage;
	}

	public int getHeight() {
		return buttonWidth;
	}

	public int getWidth() {
		return buttonHeight;
	}
}
