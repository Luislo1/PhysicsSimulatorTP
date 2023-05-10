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
	private MaxSpeedTableModel maxSpeedTable;
	
	MaxSpeedDialog(Frame parent, MaxSpeedTableModel speed) {
		super(parent, true);
		initGUI();
		this.maxSpeedTable = speed;	
	}
	
	private void initGUI() {
		setTitle("Total Force per Body");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);
		
		JTable _maxSpeedTable = new JTable(maxSpeedTable);
		eventsPanel.add(_maxSpeedTable);
		eventsPanel.add(new JScrollPane(_maxSpeedTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		JPanel comboBoxPanel = new JPanel();
		mainPanel.add(comboBoxPanel, BorderLayout.PAGE_END);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		SpinnerModel model = new SpinnerNumberModel(100, 0, 100000, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setMinimumSize(new Dimension(70, 50));	
		spinner.setMaximumSize(new Dimension(70, 50));
		buttonsPanel.add(spinner);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener((e) -> {
			// TODO
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
