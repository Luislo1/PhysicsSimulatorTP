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
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import extra.dialog.ex1.Dish;
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
		
		
		
		_lawsModel = new DefaultComboBoxModel<>();
		//TODO add the description of all force laws to _lawsModel : do it with factory arraylist in main with getInfo()
		_laws = new JComboBox<>(_lawsModel);
		_laws.addActionListener((e) -> {
			_selectedLawsIndex = _laws.getSelectedIndex();
			JSONObject info = _forceLawsInfo.get(_selectedLawsIndex);
			JSONArray data = info.getJSONArray("data");
			for(int i = 0; i < data.length(); i++) {
				JSONObject jo = data.getJSONObject(i);
				//jo.;
			}
		});
		mainPanel.add(_laws);
		
		
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
			if (_groupsModel.getSelectedItem() != null) {
				_status = 1;
				ForceLawsDialog.this.setVisible(false);
			}
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
		
		_lawsModel.removeAllElements();
		for (JSONObject j : _forceLawsInfo)
			_lawsModel.addElement(j.getString("desc"));
		
		
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		pack();
		setVisible(true);
		return _status;
	}
	
	// For getting the item selected in the comboBox.
	JSONObject getForceLawDesc() { 
		return (JSONObject) _lawsModel.getSelectedItem();
	}
	
	BodiesGroup getGroup() {
		return (BodiesGroup) _groupsModel.getSelectedItem();
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
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
