package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
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
	private JButton _maxSpeedButton;
	private JSpinner _stepSpinner;
	private JTextField _timeField;
	private ForceLawsDialog _flDialog;
	private MaxSpeedTableModel _maxSpeedTableModel;
	private static final int DEFAULT_STEPS_MAXSPEED = 100;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() { // Contains all buttons, the step spinner and delta-time field
		setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_START);

		
		_maxSpeedTableModel = new MaxSpeedTableModel(_ctrl, DEFAULT_STEPS_MAXSPEED);
		
		_maxSpeedButton = new JButton();
		_maxSpeedButton.setToolTipText("Max Speed");
		_maxSpeedButton.setIcon(new ImageIcon("Resources/icons/stats.png"));
		_maxSpeedButton.addActionListener((e) -> { 
			Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
			new MaxSpeedDialog(parent, _maxSpeedTableModel);
		});
		_toolBar.add(_maxSpeedButton);
		
		_openButton = new JButton();
		_openButton.setToolTipText("Load an input file into the simulator");
		_openButton.setIcon(new ImageIcon("Resources/icons/open.png"));
		_openButton.addActionListener((e) -> { // Open the filechooser and select the chosen file to load the data
			openFile();
		});
		_toolBar.add(_openButton);
		_toolBar.addSeparator();

		_selectButton = new JButton();
		_selectButton.setToolTipText("Select force laws for groups");
		_selectButton.setIcon(new ImageIcon("Resources/icons/physics.png"));
		_selectButton.addActionListener((e) -> { // Open the force laws dialog. Creates a new one only if none has been
													// created yet
			if (_flDialog == null) {
				Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
				_flDialog = new ForceLawsDialog(parent, _ctrl);
			}
			_flDialog.open();
		});
		_toolBar.add(_selectButton);

		_viewerButton = new JButton();
		_viewerButton.setToolTipText("Open viewer window");
		_viewerButton.setIcon(new ImageIcon("Resources/icons/viewer.png"));
		_viewerButton.addActionListener((e) -> { // Creates new viewer window. Multiple can be opened at the same time
			JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
			new ViewerWindow(parent, _ctrl);
		});
		_toolBar.add(_viewerButton);
		_toolBar.addSeparator();

		_runButton = new JButton();
		_runButton.setToolTipText("Run the simulator");
		_runButton.setIcon(new ImageIcon("Resources/icons/run.png"));
		_runButton.addActionListener((e) -> { // Runs the simulator with the amount of steps using delta-time
			_stopped = false;
			enableAllButtons(_stopped);
			_stopButton.setEnabled(!_stopped);
			try {
				_ctrl.setDeltaTime(Double.parseDouble(_timeField.getText()));
				run_sim((Integer) _stepSpinner.getValue());
			} catch (Exception ex) {
				Utils.showErrorMsg("Delta-time must be a valid value");
				enableAllButtons(true);
			}
		});
		_toolBar.add(_runButton);

		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop the simulator");
		_stopButton.setIcon(new ImageIcon("Resources/icons/stop.png"));
		_stopButton.addActionListener((e) -> _stopped = true); // stops the simulator
		_toolBar.add(_stopButton);

		JLabel stepLabel = new JLabel("Steps:");
		_toolBar.add(stepLabel);
		SpinnerModel model = new SpinnerNumberModel(10000, 0, 100000, 100); // Step spinner starting at 10000, changes
																			// in intervals of 100 from 0 to 100000
																			// steps
		_stepSpinner = new JSpinner(model);
		_stepSpinner.setMinimumSize(new Dimension(70, 50));
		_stepSpinner.setPreferredSize(new Dimension(70, 50));
		_stepSpinner.setMaximumSize(new Dimension(70, 50));
		_stepSpinner.setToolTipText("Simulation steps to run: 1-10000");
		_toolBar.add(_stepSpinner);

		JLabel deltaLabel = new JLabel("Delta-Time: ");
		_toolBar.add(deltaLabel);
		_timeField = new JTextField("2500.0"); // Delta-time text field that can be edited
		_timeField.setToolTipText("Real time (seconds) corresponding to a step");
		_timeField.setMinimumSize(new Dimension(50, 50));
		_timeField.setPreferredSize(new Dimension(50, 50));
		_timeField.setMaximumSize(new Dimension(50, 50));
		_toolBar.add(_timeField);

		// Quit Button
		_toolBar.add(Box.createGlue());
		_toolBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("Resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolBar.add(_quitButton);

		_fc = new JFileChooser("U:\\hlocal\\TP2Exam\\resources\\examples\\input");

	}

	private void openFile() {
		int returnVal = _fc.showOpenDialog(Utils.getWindow(this));
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_ctrl.reset();
			File file = _fc.getSelectedFile();
			file.getPath();
			InputStream in;
			try {
				in = new FileInputStream(file);
				_ctrl.loadData(in);
			} catch (Exception e1) {
				Utils.showErrorMsg("Incorrect file");
			}
		}
	}

	private void run_sim(int n) { // Run the simulator n steps with recursive calls. Use invokeLater() to update
									// the simulator each step
		if (n > 0 && !_stopped) {

			try {
				_ctrl.run(1);
			} catch (Exception e) {
				Utils.showErrorMsg("Simulator has crashed");
				_stopped = true;
				enableAllButtons(_stopped);
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			_stopped = true;
			enableAllButtons(_stopped);
		}
	}

	private void enableAllButtons(boolean activate) { // Sets all buttons to the boolean activate
		_quitButton.setEnabled(activate);
		_openButton.setEnabled(activate);
		_selectButton.setEnabled(activate);
		_viewerButton.setEnabled(activate);
		_runButton.setEnabled(activate);
		_stopButton.setEnabled(activate);
		_maxSpeedButton.setEnabled(activate);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		Double deltaTime = dt;
		_timeField.setText(deltaTime.toString());
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		Double deltaTime = dt;
		_timeField.setText(deltaTime.toString());
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
