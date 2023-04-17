package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import extra.jtable.EventEx;
import extra.jtable.EventsTableModel;

public class InfoTable extends JPanel {

	String _title;
	TableModel _tableModel;
	private JTable _tableInfo;
	
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 1);

	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	
	private void initGUI() {
		// table
		JPanel infoPanel = new JPanel(new BorderLayout());
		this.add(infoPanel, BorderLayout.CENTER);

		// add border
		infoPanel.setBorder(BorderFactory.createTitledBorder(_defaultBorder, _title, TitledBorder.LEFT, TitledBorder.TOP));

		// the model
		_tableInfo = new JTable(_tableModel);

		infoPanel.add(new JScrollPane(_tableInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		
		// TODO bottom black border can't be seen.

		
		setVisible(true);

	}

}
