package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() { // Contains ControlPanel, contentPanel with both tables and StatusBar
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		JPanel controlPanel = new ControlPanel(_ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);

		JPanel statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);

		// this is the panel of the tables (it uses vertical BoxLayout)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		JPanel groupsTable = new InfoTable("Groups", new GroupsTableModel(_ctrl));
		groupsTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupsTable);

		JPanel bodiesTable = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		bodiesTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesTable);
		
		JPanel accDistanceTable = new InfoDistanceTable("Accumulated Distances", new DistancesTableModel(_ctrl));
		accDistanceTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(accDistanceTable);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Utils.quit(MainWindow.this);

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
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);

	}

}
