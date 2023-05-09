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
public class TotalForceDialog extends JDialog implements SimulatorObserver{
	String[] _headers = { "Id", "Total force" };
	List<Body> _bodies;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private int _status;
	private ForcesTableModel _forcesTableModel;
	
	TotalForceDialog(Frame parent, ForcesTableModel forces) {
		super(parent, true);
		initGUI();
		this._forcesTableModel = forces;
	}
	
	private void initGUI() {
		setTitle("Total Force per Body");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.add(labelPanel);
		
		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);
		
		_dataTableModel = new DefaultTableModel() {
			/*
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 0);// Make column 1 not editable
			}
			*/
		};
		_dataTableModel.setColumnIdentifiers(_headers);
		JTable _dataTable = new JTable(_dataTableModel);
		eventsPanel.add(_dataTable);
		eventsPanel.add(new JScrollPane(_dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		TableColumn tableColumn = _dataTable.getColumnModel().getColumn(1);
		tableColumn.setMinWidth(300);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			try {
				// TODO que pasa si pulso ok????
			} catch (Exception exc) {
				Utils.showErrorMsg("Problem encountered with new force laws, enter a valid value");

			}
			//_status = 1;
			//ForceLawsDialog.this.setVisible(false);
		});
		buttonsPanel.add(okButton);
		
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
		
		/*
		_forceLawsInfo = _ctrl.getForceLawsInfo(); // Get the list of available force laws with their info

		JLabel labelIntro = new JLabel(
				"<html>Select a force law and provide values of the parameters in the <font color = 'black'>Value column</font>(default values are used for parameters <br>with no value)</html>"); // <br>
																																																	// =
																																																	// lineBrak
		labelIntro.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelIntro.setAlignmentY(Component.TOP_ALIGNMENT);
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(labelIntro);
		mainPanel.add(labelPanel);

		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);

		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 1);// Make column 1 editable
			}
		};
		_dataTableModel.setColumnIdentifiers(_headers);
		JTable _dataTable = new JTable(_dataTableModel);
		eventsPanel.add(_dataTable);
		eventsPanel.add(new JScrollPane(_dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
				
		TableColumn tableColumn = _dataTable.getColumnModel().getColumn(2);
		tableColumn.setMinWidth(300);
		JPanel comboBoxPanel = new JPanel();
		mainPanel.add(comboBoxPanel, BorderLayout.PAGE_END);

		JLabel forceLawLabel = new JLabel("Force Law:");
		comboBoxPanel.add(forceLawLabel);

		_lawsModel = new DefaultComboBoxModel<>();
		_laws = new JComboBox<>(_lawsModel);
		_laws.addActionListener((e) -> { // Action listener to use the selected combo box index to fill the table
			_selectedLawsIndex = _laws.getSelectedIndex();
			fillInTable(_selectedLawsIndex);
		});
		comboBoxPanel.add(_laws);

		JLabel groupLabel = new JLabel("Group:");
		comboBoxPanel.add(groupLabel);

		for (JSONObject j : _forceLawsInfo) // Fill in Force Laws ComboBox
			_lawsModel.addElement(j.getString("desc"));

		_groupsModel = new DefaultComboBoxModel<>();
		_groups = new JComboBox<>(_groupsModel); // Groups ComboBox is filled in observer methods
		comboBoxPanel.add(_groups);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			try {
				setNewForceLaws();
			} catch (Exception exc) {
				Utils.showErrorMsg("Problem encountered with new force laws, enter a valid value");

			}
			_status = 1;
			ForceLawsDialog.this.setVisible(false);
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
		*/
	}
	
	public int open() {
		
		/*
		 if (_groupsModel.getSize() == 0)
		 	return _status;
		 	*/

		setLocationRelativeTo(getParent()); // Open the dialog in the middle of the parent window
		pack();
		setVisible(true);
		return _status;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

}
