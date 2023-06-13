package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class MaxSpeedTableModel extends AbstractTableModel implements SimulatorObserver {

	String[] _header = { "Time", "Id", "gId", "Max Speed" };
	private List<Body> _bodies;
	private List<Double> _time;
	private List<Double> _maxSpeedList;
	private int _steps;

	MaxSpeedTableModel(Controller ctrl, int steps) {
		_bodies = new ArrayList<>();
		_time = new ArrayList<>();
		_maxSpeedList = new ArrayList<>();
		_steps = steps;
		ctrl.addObserver(this);
	}

	public String getColumnName(int col) {
		return _header[col];
	}

	@Override
	public int getRowCount() {
		return _maxSpeedList == null ? 0 : _maxSpeedList.size();
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
			s = String.valueOf(_time.get(rowIndex));
			break;
		case 1:
			s = b.getId();
			break;
		case 2:
			s = b.getgId();
			break;
		case 3:
			s = _maxSpeedList.get(rowIndex);
			break;
		default:
			break;
		}
		return s;
	}

	public void reset(int n) {
		_bodies.clear();
		_time.clear();
		_maxSpeedList.clear();
		_steps = n;
	}

	private void removeFirst() {
		_bodies.remove(0);
		_time.remove(0);
		_maxSpeedList.remove(0);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		Double maxSpeed = 0.0;
		Body maxBody = null;
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				Double currVel = b.getVelocity().magnitude();
				if (currVel > maxSpeed) {
					maxSpeed = currVel;
					maxBody = b;
				}
			}
		}
		_bodies.add(maxBody);
		_maxSpeedList.add(maxSpeed);
		_time.add(time);
		if (_bodies.size() == _steps + 1) {
			removeFirst();
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		_time.clear();
		_maxSpeedList.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
