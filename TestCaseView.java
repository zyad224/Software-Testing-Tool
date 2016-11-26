/*
This Class gets all the testcases in a specific folder from the database
and shows the user the ID, Name, Description of each testcase available in that folder.
When a user press on a testcase, it shows to the user the detailes of this testcase
*/


package testCases;

import java.util.Vector;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import middleware.DbConnection;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TestCaseView extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Vector<TestCase> testCasesVector = new Vector<TestCase>();
	public static JTable table;
	private DbConnection dbConnection;
	private Object[][] data;
	private int row;

	public TestCaseView(int FolderID) {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),}));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "1, 1, 18, 12, fill, fill");


		dbConnection = new DbConnection();
		if(FolderID == -1)
			testCasesVector = dbConnection.getTestCases();
		else
			testCasesVector = dbConnection.getTestCases(FolderID);

		data = new Object[testCasesVector.size()][3];
		row = 0;

		
		//Get all the info of the testcases and fill the table
		for (TestCase tc : testCasesVector)
		{
			try {
				data[row][0] = tc.getId();
				data[row][1] = tc.getName();
				data[row][2] = tc.getDescription();
				row++;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		table = new JTable();
		table.setModel(new DefaultTableModel(
				data,
				new String[] {
						"Test ID", "Name", "Description"
				}
				) {			
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
					false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setMinWidth(10);
		table.getColumnModel().getColumn(0).setMaxWidth(140);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.setFocusable(true);
		table.getSelectionModel().clearSelection();
		scrollPane.setViewportView(table);
	}

}
