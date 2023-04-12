package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public String getColumnName(int col) {
		return _header[col];
	}
	
	@Override
	public int getRowCount() {
		return _bodies == null ? 0 : _bodies.size();
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
			s = _bodies.get(rowIndex).getId(); 
			break;
		case 1:
			s = _bodies.get(rowIndex).getgId();
			break;
		case 2:
			s = _bodies.get(rowIndex).getMass();// TODO get bodies id's.
			break;
		case 3:
			s = _bodies.get(rowIndex).getVelocity();
			break;
		case 4:
			s = _bodies.get(rowIndex).getPosition();
			break;
		case 5:
			s = _bodies.get(rowIndex).getForce();
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Body b : _bodies) {
            for (Body b2 : groups.get(b.getgId())) { // For bodies in the same group as body b.
            	if(b2.equals(b))
            		b = b2;
            }
        }
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    for(Body b : value) 
		    	_bodies.add(b);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    for(Body b : value)
		    	_bodies.add(b);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for(Body b : g)
			_bodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
	
	

}
