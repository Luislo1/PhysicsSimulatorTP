package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MassLosingPanel extends JPanel {

	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 1);

	public MassLosingPanel(Controller ctrl) {
		initGUI(ctrl);
	}

	private void initGUI(Controller ctrl) {
		setLayout(new BorderLayout());
		MassLosingTextArea textArea = new MassLosingTextArea(ctrl);
		this.add(textArea);
		this.setBorder(BorderFactory.createTitledBorder(_defaultBorder, "Mass Losing"));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(buttonsPanel, BorderLayout.PAGE_START);

		JButton cancelButton = new JButton("SHOW");
		cancelButton.addActionListener((e) -> {
			textArea.showMassLoss();
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("RESET");
		okButton.addActionListener((e) -> {
			textArea.resetMassLoss();
		});
		buttonsPanel.add(okButton);

		setVisible(true);
	}
}
