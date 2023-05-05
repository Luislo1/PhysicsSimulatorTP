package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class TotalForceTableModel extends AbstractTableModel implements SimulatorObserver {

	Map<String, Vector2D> _totalForce;
	String[] _header = { "Body", "Total Forces" };
	List<Body> _bodies;

	public TotalForceTableModel(Controller ctrl) {
		_totalForce = new HashMap<>();
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
		Body b = _bodies.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = b.getId();
			break;
		case 1:
			s = _totalForce.get(b.getId()+b.getgId());
			break;
		default:
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				Vector2D currTotal = _totalForce.get(b.getId()+b.getgId());
				Vector2D currForce = b.getForce();
				_totalForce.put(b.getId()+b.getgId(), currTotal.plus(currForce));				
			}
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		_totalForce.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				_bodies.add(b);
				_totalForce.put(b.getId()+b.getgId(), new Vector2D());				
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		_totalForce.put(b.getId()+b.getgId(), new Vector2D());
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
