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

@SuppressWarnings("serial")
public class DistancesTableModel extends AbstractTableModel implements SimulatorObserver{
	String[] _header = { "Body", "Accumulated Distance"};
	List<Body> _bodies;
	Map<String, Double> _accDistance; // Guarda la distancia total que el cuerpo ha recorrido hasta el momento actual.
	Map<String, Vector2D> _prevDistance; // Guarda la última posición del cuerpo antes de recorrer distancia.
	
	DistancesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		_accDistance = new HashMap<>();
		_prevDistance = new HashMap<>();
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
			s = _bodies.get(rowIndex).getId();
			break;
		case 1:
			s = _accDistance.get(b.getId()+b.getgId());
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
			for(Body b: value) {
				Vector2D currDistance = b.getPosition();
				Vector2D prevDistance = new Vector2D();
				for (Map.Entry<String, Vector2D> oldEntry : _prevDistance.entrySet()) { // Coges la distancia previa del cuerpo.
					if (oldEntry.getKey().equals(b.getId()+b.getgId())) { // Si no tiene posición previa, será [0.0,0.0].
						prevDistance = oldEntry.getValue();
					}
					double newDistance = currDistance.distanceTo(prevDistance);
					double totDistance = _accDistance.get(b.getId()+b.getgId());
					_accDistance.put(b.getId()+b.getgId(), totDistance + newDistance);
					_prevDistance.put(b.getId()+b.getgId(), currDistance);
				}
			}
		}
		fireTableDataChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		_accDistance.clear();
		_prevDistance.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				_bodies.add(b);
				_accDistance.put(b.getId()+b.getgId(), 0.0);
				_prevDistance.put(b.getId()+b.getgId(), new Vector2D());
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		_accDistance.put(b.getId()+b.getgId(), 0.0);
		_prevDistance.put(b.getId()+b.getgId(), new Vector2D());
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

}
