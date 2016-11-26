/*
 This Class shows to the user the form that will be filled 
 to create new test case. it contains the name, description,steps
 , and priority.
 */

package testCases;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import main.MainRun;
import middleware.DbConnection;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;

public class CreateTestCase extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel textField;
	private JTextField txtZyad;
	private JLabel status;
	private DbConnection db = new DbConnection();
	private static Vector<TestCase> testCasesVector = new Vector<TestCase>();
	private JTextField textField_1;
	private  String selection;
	private Object[] data;
	private int row;
	private int i;
	private int size ; 


	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	public CreateTestCase(int FolderID, boolean root_flag) {

		TestCase createdTestCase = new TestCase();
		
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(4dlu;default)"),
				ColumnSpec.decode("max(321dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.MIN_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("-97px"),},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.MIN_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("14px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("22px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.MIN_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(29dlu;default)"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		JPanel panel_2 = new JPanel();
		add(panel_2, "2, 2, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("40px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(135dlu;default)"),},
				new RowSpec[] {
						RowSpec.decode("17px"),}));

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(lblName, "1, 1, right, top");

		textField_1 = new JTextField();



		textField_1.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			public void insertUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			public void removeUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			private void update(DocumentEvent documentEvent) {
				createdTestCase.setName(textField_1.getText());
			}
		});
		panel_2.add(textField_1, "3, 1, fill, default");
		textField_1.setColumns(10);
		if(!root_flag){
			createdTestCase.setFolderID(FolderID);
		}
		
			
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(lblDescription, "2, 4, fill, top");

		JTextArea textArea = new JTextArea();
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			public void insertUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			public void removeUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			private void update(DocumentEvent documentEvent) {
				createdTestCase.setDescription(textArea.getText());
			}
		});
		add(textArea, "2, 6, fill, fill");
		createdTestCase.setDateTime(new Date().toString());

		// retrieving the last test ID
		testCasesVector = db.getTestCases();

		data = new Object [testCasesVector.size()];
		row = 0 ;


		for (TestCase tc : testCasesVector)
		{
			try {
				data[row] = tc.getId();		
				row++;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		size = 0 ;
		if (row == 0)
		{
			size = 1;
		}
		else{

			i = (Integer) data[row-1] ;
			size = i + 1;
		}	
		JPanel panel = new JPanel();
		add(panel, "2, 8, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(44dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
				new RowSpec[] {
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblCreationDate = new JLabel("Creation Date:");
		panel.add(lblCreationDate, "1, 1, right, default");
		textField = new JLabel();	
		textField.setText(new Date().toString());

		panel.add(textField, "3, 1, fill, default");
		JLabel lblDesigner = new JLabel("Designer:");
		panel.add(lblDesigner, "7, 1, right, default");

		txtZyad = new JTextField();
		txtZyad.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			public void insertUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			public void removeUpdate(DocumentEvent documentEvent) {
				update(documentEvent);
			}
			private void update(DocumentEvent documentEvent) {
				createdTestCase.setDesigner(txtZyad.getText());
			}
		});
		panel.add(txtZyad, "9, 1, fill, default");
		txtZyad.setColumns(10);

		JLabel lblStatus = new JLabel("Status:");
		panel.add(lblStatus, "1, 3, right, default");

		status = new JLabel("New");
		panel.add(status, "3, 3, fill, default");

		JLabel lblTestId = new JLabel("Test ID:");
		panel.add(lblTestId, "7, 3, right, default");
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setText(Integer.toString(size));
		createdTestCase.setId(size);

		panel.add(lblNewLabel, "9, 3");

		JLabel label = new JLabel("");
		label.setMinimumSize(new Dimension(100,10));
		add(label, "4, 8");
		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 10, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(48dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(52dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default)"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblPriority = new JLabel("Priority:");
		panel_1.add(lblPriority, "1, 1, right, default");

		JComboBox comboBox = new JComboBox();
		panel_1.add(comboBox, "3, 1, fill, default");
		String one="1";
		String two="2";
		String three="3";

		comboBox.addItem(one);
		comboBox.addItem(two);
		comboBox.addItem(three);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 13, fill, fill");

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null},
				},
				new String[] {
						"Step ID", "Description", "Expected Result"
				}
				) {
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setFocusable(true);		
		table.setValueAt(1,0,0);

		table.addMouseListener( new MouseAdapter()
		{
			public void mousePressed( MouseEvent e )
			{
				// get the coordinates of the mouse click
				Point p = e.getPoint();
				// get the row index that contains that coordinate
				int rowNumber = table.rowAtPoint( p );
				// Get the ListSelectionModel of the JTable
				ListSelectionModel model = table.getSelectionModel();
				table.setFocusable(true);
				// variable for the beginning and end selects only that one row.
				model.setSelectionInterval( rowNumber, rowNumber );
			}
		});


		table.getSelectionModel().clearSelection();
		table.getColumnModel().getColumn(0).setMaxWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setMaxWidth(214748364);

		/*
		 create button
		 */
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection = (String) comboBox.getSelectedItem();
				createdTestCase.setPriority(selection);
				String status = "New";
				createdTestCase.setStatus(status);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int col=model.getColumnCount();
				int row=model.getRowCount();
				Object[][] stepTableData= new Object[row][col];
				for(int i=0;i<row;i++)
				{
					for(int j=0;j<3;j++)
					{
						if (table.isEditing())
							table.getCellEditor().stopCellEditing();
						stepTableData[i][j]=model.getValueAt(i, j);
					}
				}
				createdTestCase.setSteps(row, col, stepTableData);
				db.createTestCase(createdTestCase,root_flag);
				MainRun.window.refreshTree();
				MainRun.window.getOverviewTree().setSelectionRow(0);

			}
		});


		/*
		 When the user press enters on the steps table, a new
		 row is automatically added
		 */
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' ){
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					int rightClickedRow = table.getSelectedRow();
					if(rightClickedRow==0){
						model.addRow(new Object[] { "", "" });
						if (table.getRowCount() == 3)
						{
							table.setValueAt(3, 2, 0);
						}
						else{
							table.setValueAt(table.getRowCount(), table.getRowCount()-1, 0);
						}

					}
					else if (rightClickedRow-1 ==(table.getRowCount()-1)){
						table.setValueAt(rightClickedRow-1+1+1, rightClickedRow+1, 0);
						model.addRow(new Object[] { "", "" });
					}
					else{
						model.insertRow(rightClickedRow, new Object[] { "", "" });
						for (int i = table.getRowCount()-1 ; i>rightClickedRow; i--){
							table.setValueAt(i+1, i, 0);
						}
						table.setValueAt(rightClickedRow+1, rightClickedRow, 0);
						table.setFocusable(true);

					}

				}
			}
		});
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(new Dimension(400,100));
		add(btnNewButton, "2, 17, right, top");
	}

}
