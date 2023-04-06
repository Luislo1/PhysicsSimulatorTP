package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private ForceLawsDialog _flDialog;
	
	// TODO

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	// TODO check rezizing problem.
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_START);

		// TODO make sure correct layout; use glue etc
		_openButton = new JButton();
		_openButton.setToolTipText("Load an input file into the simulator");
		_openButton.setIcon(new ImageIcon("Resources/icons/open.png"));
		_openButton.addActionListener((e) -> {
			 int returnVal = _fc.showOpenDialog(Utils.getWindow(this));
			 if (returnVal == JFileChooser.APPROVE_OPTION) { //TODO check if this is the correct way
				 _ctrl.reset();
				 File file = _fc.getSelectedFile();
				 file.getPath();
				 InputStream in;
				try {
					in = new FileInputStream(file);
					 _ctrl.loadData(in); // TODO fix problem, it crushes here.
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			 }
			 
		});
		
		_toolBar.add(_openButton);
		_toolBar.addSeparator();

		_selectButton = new JButton();
		_selectButton.setToolTipText("Select force laws for groups");
		_selectButton.setIcon(new ImageIcon("Resources/icons/physics.png"));
		_selectButton.addActionListener((e) -> {
			if (_flDialog == null) {
				Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
				_flDialog = new ForceLawsDialog(parent, _ctrl);
			}
			int status = _flDialog.open();	
		});
		_toolBar.add(_selectButton);

		_viewerButton = new JButton();
		_viewerButton.setToolTipText("Open viewer window");
		_viewerButton.setIcon(new ImageIcon("Resources/icons/viewer.png"));
		_viewerButton.addActionListener((e) -> {
			JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
			ViewerWindow viewerWindow  = new ViewerWindow(parent, _ctrl);

		});
		_toolBar.add(_viewerButton);
		_toolBar.addSeparator();

		_runButton = new JButton();
		_runButton.setToolTipText("Run the simulator");
		_runButton.setIcon(new ImageIcon("Resources/icons/run.png"));
		_runButton.addActionListener((e) -> {
			// TODO disable all buttons except stop
			_stopped = false;
			 _ctrl.setDeltaTime(Double.parseDouble(_timeField.getText())); //TODO complete with the numbers in each of
			// the components
			 run_sim( (Integer) _stepSpinner.getValue()); //TODO check if getValue is grabbing correctly the int
		});
		_toolBar.add(_runButton);

		//_toolBar.add(Box.createGlue());
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop the simulator");
		_stopButton.setIcon(new ImageIcon("Resources/icons/stop.png"));
		_stopButton.addActionListener((e) -> _stopped = true);
		_toolBar.add(_stopButton);

		_toolBar.add(Box.createGlue());
		JLabel stepLabel = new JLabel("Steps:");
		_toolBar.add(stepLabel);
		SpinnerModel model = new SpinnerNumberModel(10000, 0, 100000, 100); // TODO Max value of the spinner.
		_stepSpinner = new JSpinner(model); 
		_stepSpinner.setToolTipText("Simulation steps to run: 1-10000");
		_toolBar.add(_stepSpinner);

		
		_toolBar.add(Box.createGlue());
		JLabel deltaLabel = new JLabel("Delta-Time: ");
		_toolBar.add(deltaLabel);
		_timeField = new JTextField("2500.0");
		_timeField.setToolTipText("Real time (seconds) corresponding to a step");
		_toolBar.add(_timeField);

		// Quit Button
		_toolBar.add(Box.createGlue());
		_toolBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("Resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolBar.add(_quitButton);

		// TODO create the file chooser
		 _fc = new JFileChooser();

	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {

			try {
				_ctrl.run(1);
			} catch (Exception e) {
				Utils.showErrorMsg("Simulator has crashed");
				// TODO enable all buttons
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			// TODO enable all buttons

			_stopped = true;
		}
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO modify jtextfield with the current delta time

	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
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
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
