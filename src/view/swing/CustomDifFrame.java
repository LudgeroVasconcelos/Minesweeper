package view.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.events.CustomDifficultyListener;

/**
 * 
 * @author Ludgero
 *
 */
public class CustomDifFrame extends JDialog implements ChangeListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private static final int MIN_ROWS = 3;
	private static final int MIN_COLUMNS = 9;
	private static final int MIN_MINES = 1;
	private static final int[] MIN_INPUT = { MIN_ROWS, MIN_COLUMNS, MIN_MINES };

	private static final int MAX_ROWS = 30;
	private static final int MAX_COLUMNS = MAX_ROWS * 2;
	private static final int MAX_MINES = MAX_ROWS / 2 * MAX_COLUMNS / 2 - 9;
	private static final int[] MAX_INPUT = { MAX_ROWS, MAX_COLUMNS, MAX_MINES };

	private static final String ROWS_NAME = "Rows";
	private static final String COLUMNS_NAME = "Columns";
	private static final String MINES_NAME = "Mines";
	private static final String[] NAMES = { ROWS_NAME, COLUMNS_NAME, MINES_NAME };

	private static final Dimension ROWS_COLUMNS_DIMENSION = new Dimension(40, 20);
	private static final Dimension MINES_DIMENSION = new Dimension(55, 20);
	private static final Dimension[] SPINNER_DIMENSIONS = { ROWS_COLUMNS_DIMENSION, ROWS_COLUMNS_DIMENSION,
			MINES_DIMENSION };

	private static final Dimension SLIDER_DIMENSION = new Dimension(150, 26);

	private static final int[] RIGHT_BORDER = { 28, 28, 20 };

	private JSlider[] sliders;
	private JSpinner[] spinners;

	private CustomDifficultyListener listener;

	public CustomDifFrame(JFrame parent) {
		super(parent, "Custom");

		sliders = new JSlider[3];
		spinners = new JSpinner[3];

		GridLayout layout = new GridLayout(1, 2);

		JPanel total = new JPanel(new BorderLayout(0, 10));
		String alignments[] = { BorderLayout.NORTH, BorderLayout.CENTER, BorderLayout.SOUTH };

		for (int i = 0; i < 3; i++) {
			JLabel name = new JLabel(NAMES[i]);

			String id = String.valueOf(i);

			JSpinner spinner = createSpinner(id, MIN_INPUT[i], MAX_INPUT[i], SPINNER_DIMENSIONS[i]);
			spinners[i] = spinner;

			Border border = BorderFactory.createEmptyBorder(0, 0, 0, RIGHT_BORDER[i]);
			JPanel labelsPanel = new JPanel(new BorderLayout());
			labelsPanel.setBorder(border);
			labelsPanel.add(name, BorderLayout.WEST);
			labelsPanel.add(spinner, BorderLayout.EAST);

			JSlider slider = createSlider(id, MIN_INPUT[i], MAX_INPUT[i], SLIDER_DIMENSION);
			sliders[i] = slider;

			// set default value in the middle
			slider.setValue((MAX_INPUT[i] + MIN_INPUT[i]) / 2);

			JPanel singlePanel = new JPanel(layout);
			singlePanel.add(labelsPanel);
			singlePanel.add(slider);

			total.add(singlePanel, alignments[i]);
		}

		JOptionPane jop = new JOptionPane(total, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		jop.addPropertyChangeListener(this);
		setContentPane(jop);

		pack();
		setModalityType(ModalityType.APPLICATION_MODAL);
	}

	public void addPropertyListener(CustomDifficultyListener listener) {
		this.listener = listener;
	}

	private JSlider createSlider(String name, int minInput, int maxInput, Dimension dim) {
		JSlider slider = new JSlider();
		slider.setName(name);
		slider.setMinimum(minInput);
		slider.setMaximum(maxInput);
		slider.setPreferredSize(dim);
		slider.addChangeListener(this);
		return slider;
	}

	private JSpinner createSpinner(String name, int minInput, int maxInput, Dimension dim) {
		SpinnerNumberModel snm = createSpinnerModel(minInput, maxInput);

		JSpinner spinner = new JSpinner(snm);
		spinner.setName(name);
		spinner.setPreferredSize(dim);
		spinner.addChangeListener(this);

		alterEditor((DefaultEditor) spinner.getEditor());

		return spinner;
	}

	private SpinnerNumberModel createSpinnerModel(int minInput, int maxInput) {
		return new SpinnerNumberModel(10, minInput, maxInput, 1);
	}

	/**
	 * Disables keyboard editing and centers the text.
	 * 
	 * @param editor The editor to alter
	 */
	private void alterEditor(DefaultEditor editor) {
		JFormattedTextField tf = editor.getTextField();
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setEditable(false);
	}

	private int getRowsValue() {
		return (int) sliders[0].getValue();
	}

	private int getColumnsValue() {
		return (int) sliders[1].getValue();
	}

	private int getCurrentMaxMines() {
		return getRowsValue() * getColumnsValue() - 9;
	}

	@Override
	public void stateChanged(ChangeEvent ce) {

		if (ce.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) ce.getSource();

			int nameIndex = Integer.parseInt(slider.getName());

			int spinnerValue = (int) spinners[nameIndex].getValue();
			int sliderValue = slider.getValue();

			if (spinnerValue != sliderValue) {
				spinners[nameIndex].setValue(sliderValue);
			}

			if (spinners[2] != null && NAMES[nameIndex] != MINES_NAME) {

				SpinnerNumberModel snm = createSpinnerModel(MIN_INPUT[2], getCurrentMaxMines());
				spinners[2].setModel(snm);
				alterEditor((DefaultEditor) spinners[2].getEditor());

				sliders[2].setMaximum(getCurrentMaxMines());
				spinners[2].setValue(getCurrentMaxMines() / 2);
			}

		} else if (ce.getSource() instanceof JSpinner) {

			JSpinner spinner = (JSpinner) ce.getSource();

			int nameIndex = Integer.parseInt(spinner.getName());

			int spinnerValue = (int) spinner.getValue();
			int sliderValue = sliders[nameIndex].getValue();

			if (spinnerValue != sliderValue) {
				sliders[nameIndex].setValue(spinnerValue);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		JOptionPane jop = (JOptionPane) pce.getSource();
		Object value = jop.getValue();

		if (value instanceof Integer && isVisible()) {
			dispose();

			int response = (int) value;

			if (response == JOptionPane.OK_OPTION) {
				int rows = (int) spinners[0].getValue();
				int columns = (int) spinners[1].getValue();
				int mines = (int) spinners[2].getValue();

				listener.onCustomDifficultyTriggered(rows, columns, mines);
			}

			// what is this line for? So next time the user clicks the same
			// option as before, the propertyChange is fired again, otherwise
			// there would be no property change
			jop.setValue(-1);
		}
	}
}
