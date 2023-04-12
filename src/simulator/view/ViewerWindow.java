package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ViewerWindow extends JFrame implements SimulatorObserver {
	
	private Controller _ctrl;
	private SimulationViewer _viewer;
	private JFrame _parent;
	
	ViewerWindow(JFrame parent, Controller ctrl) {
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = parent;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		// ScrollBar
		JScrollPane viewerScroll = new JScrollPane(_viewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(viewerScroll);
		
		this.setContentPane(mainPanel);
		
		_viewer = new Viewer();
		mainPanel.add(_viewer, BorderLayout.CENTER);
		
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				_ctrl.removeObserver(ViewerWindow.this);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				
				
			}
			
		});
		
		pack();
		if (_parent != null) 
			setLocation(_parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2,
					    _parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
		setVisible(true);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_viewer.update();	
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_viewer.reset();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) { // TODO Do we need to check if there can be more than 1 group repeated?
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()){
		    BodiesGroup value = entry.getValue();
		    _viewer.addGroup(value);
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_viewer.addGroup(g);
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_viewer.addBody(b);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		
	}
	
}
