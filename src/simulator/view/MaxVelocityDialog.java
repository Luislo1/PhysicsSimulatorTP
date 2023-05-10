package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class MaxVelocityDialog extends JDialog {

	private MaxVelTableModel _maxVelTableModel;
	private int _status;
	private int _selectedLawsIndex = 0;


	MaxVelocityDialog(Frame parent, MaxVelTableModel maxVelTableModel) {
		super(parent, true);
		_maxVelTableModel = maxVelTableModel;
		initGUI();
	}

	private void initGUI() {
		setTitle("Maximum Velocity");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);
		
		JTable _maxVelTable = new JTable(_maxVelTableModel);
		eventsPanel.add(_maxVelTable);
		
		eventsPanel.add(new JScrollPane(_maxVelTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		TableColumn tableColumn = _maxVelTable.getColumnModel().getColumn(2);
		tableColumn.setMinWidth(300);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			MaxVelocityDialog.this.setVisible(false);
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(true);
	}

	public int open() {

		setLocationRelativeTo(getParent()); // Open the dialog in the middle of the parent window
		pack();
		setVisible(true);
		return _status;
	}
}
