package simulator.view;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private Controller _ctrl;
	private JToolBar _toolBar;
	private JFileChooser _fc;
	private boolean _stopped = true;
	private JButton _quitButton;
	private JButton _openButton;
	private JButton _selectButton;
	private JButton _viewerButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JSpinner _stepSpinner;
	private JTextField _timeField;
	private ForceLawsDialog _fLDialog;
	//TODO
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_START);
		
		//TODO make sure correct layout; use glue etc
		_openButton = new JButton();
		_openButton.setToolTipText("Load an input file into the simulator");
		_openButton.setIcon(new ImageIcon("Resources/icons/open.png"));
		_openButton.addActionListener((e) -> _fc.showOpenDialog(Utils.getWindow(this))); //TODO do the two steps p7/14
		_toolBar.add(_openButton);
		_toolBar.addSeparator();
		
		_selectButton = new JButton();
		_selectButton.setToolTipText("Select force laws for groups");
		_selectButton.setIcon(new ImageIcon("Resources/icons/physics.png"));
		_selectButton.addActionListener((e) -> {
			_fLDialog = new ForceLawsDialog();
		});
		_toolBar.add(_selectButton);
		
		
		_viewerButton = new JButton();
		_viewerButton.setToolTipText("Open viewer window");
		_viewerButton.setIcon(new ImageIcon("Resources/icons/viewer.png"));
		_viewerButton.addActionListener((e) -> {});
		_toolBar.add(_viewerButton);
		_toolBar.addSeparator();
		
		
		_runButton = new JButton();
		_runButton.setToolTipText("Run the simulator");
		_runButton.setIcon(new ImageIcon("Resources/icons/run.png"));
		_runButton.addActionListener((e) -> Utils.quit(this));
		_toolBar.add(_runButton);
		
		//TODO steps and delta time boxes
		_stepSpinner = new JSpinner(); //TODO probably add Steps label
		_stepSpinner.setToolTipText("Simulation steps to run: 1-10000");
		_toolBar.add(_stepSpinner);
		
		_timeField = new JTextField("Delta-Time");
		_timeField.setToolTipText("Real time (seconds) corresponding to a step");
		_toolBar.add(_timeField);
		
		
		
		
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop the simulator");
		_stopButton.setIcon(new ImageIcon("Resources/icons/stop.png"));
		_stopButton.addActionListener((e) -> Utils.quit(this));
		_toolBar.add(_quitButton);
		
		
		
		
		//Quit Button
		_toolBar.add(Box.createGlue());
		_toolBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("Resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolBar.add(_quitButton);
		
		//TODO create the file chooser
		//_fc = 
		
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
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
