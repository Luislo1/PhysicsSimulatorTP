package simulator.view;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	
	String _title;
	TableModel _tableModel;
	
	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		
	}

}
