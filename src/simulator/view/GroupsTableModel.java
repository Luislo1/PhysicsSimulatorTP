package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	
	GroupsTableModel(Controller ctrl) {
		_groups = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	public String getColumnName(int col) {
		return _header[col];
	}
	
	@Override
	public int getRowCount() {
		return _groups == null ? 0 : _groups.size();
	}
	@Override
	public int getColumnCount() {
		return _header.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _groups.get(rowIndex).getId(); 
			break;
		case 1:
			s = _groups.get(rowIndex).getForceLawsInfo();
			break;
		case 2:
			s = getBodiesIds(rowIndex);// TODO get bodies id's.
			break;
		}
		return s;
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) { // TODO time?
		for (BodiesGroup g : _groups) {
            g = groups.get(g.getId());
        }	
		fireTableDataChanged();
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groups.clear();
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    _groups.add(value);
		}
		fireTableStructureChanged();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    _groups.add(value);
		}
		fireTableStructureChanged();
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groups.add(g);
		fireTableStructureChanged();
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		fireTableStructureChanged();
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
	}
	
	public String getBodiesIds(int rowIndex) {
		StringBuilder str = new StringBuilder();
		for(Body b: _groups.get(rowIndex)) {
			str.append(b.getId()).append(" ");
		}
		
		return str.toString();
	}

}
