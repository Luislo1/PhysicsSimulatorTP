package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class MaxSpeedDialog extends JDialog {

	private MaxSpeedTableModel _model;

	MaxSpeedDialog(Frame parent, MaxSpeedTableModel model) {
		super(parent, true);
		_model = model;
		initGUI();
	}

	private void initGUI() {
		setTitle("Max Speed Statistics");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);

		JTable _dataTable = new JTable(_model);
		eventsPanel.add(_dataTable);
		eventsPanel.add(new JScrollPane(_dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		// JSpinner
		SpinnerModel model = new SpinnerNumberModel(100, 0, 100000, 1);
		JSpinner _stepSpinner = new JSpinner(model);
		_stepSpinner.setMinimumSize(new Dimension(70, 50));
		_stepSpinner.setPreferredSize(new Dimension(70, 50));
		_stepSpinner.setMaximumSize(new Dimension(70, 50));
		_stepSpinner.setToolTipText("Entries shown");
		buttonsPanel.add(_stepSpinner);

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener((e) -> {
			_model.reset((Integer) _stepSpinner.getValue());
			setVisible(false);
		});
		buttonsPanel.add(resetButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			MaxSpeedDialog.this.setVisible(false);
		});
		buttonsPanel.add(cancelButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(true);
	}
}
