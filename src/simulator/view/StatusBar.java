package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel  implements SimulatorObserver {
	private JTextField _timeField;
	private JTextField _groupField;
	private double _time = 0;
	private int _groupNumber = 0;
	//TODO everything
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	// TODO bug with 0.0 time.
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		JLabel timeLabel = new JLabel("Time: ");
		this.add(timeLabel);
		_timeField = new JTextField(Double.toString(_time));
		this.add(_timeField); 
		_timeField.setEditable(false); // TODO check.
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		JLabel groupLabel = new JLabel("Groups: ");
		this.add(groupLabel);
		_groupField = new JTextField(Integer.toString(_groupNumber));
		this.add(_groupField);
		_groupField.setEditable(false); // TODO check.
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		this.add(s2);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_time = time;
		_groupNumber = groups.size();
		_timeField.setText(Double.toString(_time));
		_groupField.setText(Integer.toString(_groupNumber));
	}
	
	private void setFieldValues() {
		_timeField.setText(Double.toString(_time));
		_groupField.setText(Integer.toString(_groupNumber));
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		
		_time = time;
		_groupNumber = groups.size();
		setFieldValues();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_time = time;
		_groupNumber = groups.size();	
		setFieldValues();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupNumber = groups.size();
		setFieldValues();
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
