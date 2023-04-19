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
		setLayout(new BorderLayout()); //TODO ver ejemplo de JSONBuilderDialog.java con el prepare renderer para el resizing

		// add border
		this.setBorder(BorderFactory.createTitledBorder(_defaultBorder, _title));

		// the model
		_tableInfo = new JTable(_tableModel);

		this.add(new JScrollPane(_tableInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		
		// TODO bottom black border can't be seen.

		
		setVisible(true);

	}

}
