package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class MaxMinDistanceTableModel extends AbstractTableModel implements SimulatorObserver {

	String[] _header = { "#", "Min Dist.", "Max Dist." };
	List<Double> _minDist, _maxDist;
	List<String> _minDistBody, _maxDistBody;

	MaxMinDistanceTableModel(Controller ctrl) {
		_minDist = new ArrayList<>();
		_maxDist = new ArrayList<>();
		_minDistBody = new ArrayList<>();
		_maxDistBody = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public String getColumnName(int col) {
		return _header[col];
	}

	@Override
	public int getRowCount() {
		return _minDist == null ? 0 : _minDist.size();
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
			s = String.valueOf(rowIndex);
			break;
		case 1:
			s = _minDistBody.get(rowIndex) + " : " +_minDist.get(rowIndex).toString();
			break;
		case 2:
			s = _maxDistBody.get(rowIndex) + " : " +_maxDist.get(rowIndex).toString();
			break;
		default:
			break;
		}
		return s;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		Double minValue = 1e99, maxValue = 0.0;
		String minValueID = "", maxValueID = "";
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				Double distance = b.getPosition().distanceTo(new Vector2D());
				if (distance > maxValue) {
					maxValue = distance;
					maxValueID = b.getId();
				}
				if (distance < minValue) {
					minValue = distance;
					minValueID = b.getId();
				}
			}
		}
		_minDist.add(minValue);
		_maxDist.add(maxValue);
		_minDistBody.add(minValueID);
		_maxDistBody.add(maxValueID);		
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_minDist.clear();
		_maxDist.clear();
		_minDistBody.clear();
		_maxDistBody.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
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
