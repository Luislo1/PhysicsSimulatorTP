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

public class MassLosingTextArea extends JTextArea {

	private MassLosingTextAreaModel _model;

	MassLosingTextArea(Controller ctrl) {
		super();
		_model = new MassLosingTextAreaModel(ctrl);
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		setVisible(true);
		setEditable(false);
	}

	public void showMassLoss() {
		this.setText("");
		for (Body b : _model.getList()) {
			Double d = _model.getMap().get(b.getId() + b.getgId());
			this.append(b.getId() + " " + String.valueOf(d) + "\n");
		}
	}

	public void resetMassLoss() {
		for (String key : _model.getMap().keySet()) {
			_model.getMap().put(key, 0.0);
		}
		showMassLoss();
	}
}
