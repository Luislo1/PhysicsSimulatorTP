package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;


import simulator.control.Controller;
import simulator.misc.Vector2D;
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
		
		JLabel labelIntro = new JLabel("<html>Select a force law and provide values of the parameters in the <font color = 'black'>Value column</font>(default values are used for parameters <br>with no value)</html>"); // <br> = lineBrak
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
					if(column == 1) // Make column 1 editable
						return true;
					return false;
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
		_laws.addActionListener((e) -> { 
			_selectedLawsIndex = _laws.getSelectedIndex();
			fillInTable(_selectedLawsIndex);
		});
		comboBoxPanel.add(_laws);
		
		JLabel groupLabel = new JLabel("Group:");
		comboBoxPanel.add(groupLabel);
		
		for (JSONObject j : _forceLawsInfo)
			_lawsModel.addElement(j.getString("desc"));
		
		_groupsModel = new DefaultComboBoxModel<>();
		_groups = new JComboBox<>(_groupsModel);
		comboBoxPanel.add(_groups);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
	
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
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
		JSONObject info = new JSONObject(getJSON());// (a)
		JSONObject data = new JSONObject(); //(b)
		data.put("data", info);
		data.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));
		_ctrl.setForceLaws(_groupsModel.getSelectedItem().toString(), data); // (c)
	}
	
	private String getJSON() { //Builds a string e.g: "c" : "[1,1]" that corresponds to a JSON string to be parsed in the builders
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
			String k = _dataTableModel.getValueAt(i, 0).toString();
			String v = _dataTableModel.getValueAt(i, 1).toString();
			if (!v.isEmpty()) {
				s.append('"');
				s.append(k);
				s.append('"');
				s.append(':');
				s.append(v);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');

		return s.toString();
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
	} 
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
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
