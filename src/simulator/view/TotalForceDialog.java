package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class TotalForceDialog extends JDialog {

	private TotalForceTableModel _totalForceTableModel;

	TotalForceDialog(Frame parent, TotalForceTableModel totalForceTableModel) {
		super(parent, true);
		 _totalForceTableModel =  totalForceTableModel;
		initGUI();
	}

	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);
		
		
		JTable _totalForceTable = new JTable(_totalForceTableModel);
		eventsPanel.add(_totalForceTable);
		eventsPanel.add(new JScrollPane(_totalForceTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		//TableColumn tableColumn = _totalForceTable.getColumnModel().getColumn(2);
		//tableColumn.setMinWidth(300);
		JPanel comboBoxPanel = new JPanel();
		mainPanel.add(comboBoxPanel, BorderLayout.PAGE_END);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			TotalForceDialog.this.setVisible(false);
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(true);
	}

}
