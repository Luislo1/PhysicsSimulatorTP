package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class MassLosingTextArea extends JTextArea implements SimulatorObserver {

	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 1);
	private Map<String, Double> _massLoss;
	private List<Body> _bodies;

	MassLosingTextArea(Controller ctrl) {
		super();
		_massLoss = new HashMap<>();
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// add border
		this.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Mass Losing"));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton cancelButton = new JButton("SHOW");
		cancelButton.addActionListener((e) -> {
			showMassLoss();
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("RESET");
		okButton.addActionListener((e) -> {
			resetMassLoss();
		});
		buttonsPanel.add(okButton);
		setVisible(true);
		setEditable(false);

	}
	
	private void showMassLoss() {
		this.setText("");
		for (Body b : _bodies) {
			Double d = _massLoss.get(b.getId()+b.getgId());
			this.append(b.getId() + " " + String.valueOf(d) + "\n");
		}
	}
	
	private void resetMassLoss() {
		for (String key : _massLoss.keySet()) {
			_massLoss.put(key, 0.0);
		}
		showMassLoss();
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				Double newPos = b.getPosition().magnitude();
				Double sumPos = _massLoss.get(b.getId()+b.getgId());
				_massLoss.put(b.getId()+b.getgId(), sumPos + newPos);
			}
		}
	
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		_massLoss.clear();
		showMassLoss();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				_bodies.add(b);
				_massLoss.put(b.getId()+b.getgId(), 0.0);
			}
		}

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		_massLoss.put(b.getId()+b.getgId(), 0.0);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}//try to do the better solution: create a MassLosingTextAreaModel which implements observer and map + list
//then this TextArea which uses the model 
//then a MassLosingPanel with constructor (ctrl, model) which adds textarea and buttons to GUI
