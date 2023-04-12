package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;


import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog implements SimulatorObserver {

	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private int _status;
	private JComboBox<String> _laws;
	private JComboBox<String> _groups;
	private int _selectedLawsIndex = 0;
	
	
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		fillInTable(_selectedLawsIndex);
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		
		
		JPanel eventsPanel = new JPanel(new BorderLayout());
		mainPanel.add(eventsPanel, BorderLayout.CENTER);
		
		
		_dataTableModel = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					if(column == 1) // Make column 1 editable.
						return true;
					return false;
				}
		};
		_dataTableModel.setColumnIdentifiers(_headers);
		JTable _dataTable = new JTable(_dataTableModel); //TODO put label and correct alignment
		mainPanel.add(_dataTable);
		
		_lawsModel = new DefaultComboBoxModel<>();
		_laws = new JComboBox<>(_lawsModel);
		_laws.addActionListener((e) -> { 
			_selectedLawsIndex = _laws.getSelectedIndex();
			fillInTable(_selectedLawsIndex);
		});
		mainPanel.add(_laws);

		for (JSONObject j : _forceLawsInfo)
			_lawsModel.addElement(j.getString("desc"));
		
		_groupsModel = new DefaultComboBoxModel<>();
		_groups = new JComboBox<>(_groupsModel);
		mainPanel.add(_groups);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(BOTTOM_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			_status = 0;
			setVisible(false);
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			try {
			setNewForceLaws();
			} catch (Exception exc) {
				Utils.showErrorMsg("Problem encountered with new force laws, enter a valid value"); //TODO check message and if we have to separate catches
			}	
			_status = 1;
			ForceLawsDialog.this.setVisible(false);
		});
		buttonsPanel.add(okButton);
		
		
		
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);	
	}

	public int open() {
		if (_groupsModel.getSize() == 0)
			return _status;
		
		
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		pack();
		setVisible(true);
		return _status;
	}
	
	private void setNewForceLaws() {
		JSONObject info = new JSONObject();
		for (int i = 0; i < _dataTableModel.getRowCount(); i++) { //(a) TODO parse vectors for moving towards fixed point.
			info.put(_dataTableModel.getValueAt(i, 0).toString(),Double.parseDouble(_dataTableModel.getValueAt(i, 1).toString()));
		}
		JSONObject data = new JSONObject(); //(b)
		data.put("data", info);
		data.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));
		_ctrl.setForceLaws(_groupsModel.getSelectedItem().toString(), data); // (c)
	}
	
	private void fillInTable(int index) {
		JSONObject info = _forceLawsInfo.get(index); // (a)
		JSONObject data = info.getJSONObject("data"); // (b)
		_dataTableModel.setRowCount(0);
		// (c) iterate to take the key and value
			
			for (String key : data.keySet()) { 
				String value = data.getString(key);
				_dataTableModel.addRow(new Object[] {key, "", value});
			}
	} // TODO set default values.
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements(); // TODO check
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    _groupsModel.addElement(value.getId());
		}
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    _groupsModel.addElement(value.getId());
		}	
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.addElement(g.getId());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
	
}
