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

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MaxSpeedDialog extends JDialog{
	private MaxSpeedTableModel maxSpeedTableModel;
	
	MaxSpeedDialog(Frame parent, MaxSpeedTableModel speed) {
		super(parent, true);
		initGUI();
		this.maxSpeedTableModel = speed;	
	}
	
	private void initGUI() {
		setTitle("Max Speed Statistics");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);
		
		JTable _dataTable = new JTable(maxSpeedTableModel);
		eventsPanel.add(_dataTable);
		eventsPanel.add(new JScrollPane(_dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		//JPanel comboBoxPanel = new JPanel();
		//mainPanel.add(comboBoxPanel, BorderLayout.PAGE_END);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		//Spinner
		SpinnerModel model = new SpinnerNumberModel(100, 0, 100000, 1);
		JSpinner _stepSpinner = new JSpinner(model);
		_stepSpinner.setMinimumSize(new Dimension(70, 50));	
		_stepSpinner.setMaximumSize(new Dimension(70, 50));
		_stepSpinner.setToolTipText("Entries shown");
		buttonsPanel.add(_stepSpinner);
		
		//Reset buttom.
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener((e) -> {
			maxSpeedTableModel.reset((Integer)_stepSpinner.getValue());
			setVisible(false);
		});
		buttonsPanel.add(resetButton);
		//Cancel buttom.
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			setVisible(false);
		});
		buttonsPanel.add(cancelButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(true);
	}

}
