package simulator.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoDistanceTable extends InfoTable{

	InfoDistanceTable(String title, DistancesTableModel distancestableModel) {
		super(title, distancestableModel);
		initbutton();
	}
	
	private void initbutton() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		add(buttonsPanel, BorderLayout.PAGE_START);
		
		JButton resetButton = new JButton("Reset Distances");
		resetButton.addActionListener((e) -> {
			((DistancesTableModel) _tableModel).resetTotalDistances();
		});
		buttonsPanel.add(resetButton);
	}

}
