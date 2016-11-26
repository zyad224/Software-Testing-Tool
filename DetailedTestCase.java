package testCases;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import main.MainRun;
import middleware.DbConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.*;

public class DetailedTestCase extends JPanel {

	private static TestCase testCase = new TestCase();
	private static Vector<TestCaseStep> testCaseStepVector = new Vector<TestCaseStep>();
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;
	private DbConnection db = new DbConnection();
	private Object[][] data_step;
	private int[] data_step_ID;
	private int[] data_step_ID2;
	private int row = 0;
	private JTextField textField_1;
	private TestCase editedTestCase = new TestCase();
	private TestCaseStep editedTestCaseStep = new TestCaseStep();
	private JLabel textField_2;
	private JTextField textField_4;
	private JTextField textField_6;
	private String selection;
	private int DbPriorityIndex;
	private int ml_AddStep = 0;
	private int ml_DeleteStep = 0;
	private int rowNumber = 0 ;


	public DetailedTestCase(int index) {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(13dlu;default)"),
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.MIN_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:max(55dlu;default)"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("fill:default:grow"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), FormSpecs.RELATED_GAP_ROWSPEC, }));

		testCase = db.getTestCase(index);

		String[] display = new String[7];
		display[0] = Integer.toString(index);
		display[1] = testCase.getName();
		display[2] = testCase.getDescription();
		display[3] = testCase.getPriority();
		display[4] = testCase.getDesigner();
		display[5] = testCase.getDateTime();
		display[6] = testCase.getStatus();

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 2, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(29dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(98dlu;default)"), },
				new RowSpec[] { FormSpecs.MIN_ROWSPEC, }));

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblName, "2, 1");

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));

		if (textField.getText().length() == 0)
			textField.setText(display[1]);
		editedTestCase.setName(display[1]);
		textField.setEnabled(true);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				textField.setEnabled(true);
				textField.requestFocus();
			}
		});

		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				editedTestCase.setName(textField.getText());
				textField.setEnabled(true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				editedTestCase.setId(index);
				textField.getDocument().addDocumentListener(new DocumentListener() {

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
						editedTestCase.setName(textField.getText());
					}
				});
			}
		});

		panel_1.add(textField, "4, 1");
		textField.setColumns(8);

		JPanel panel_3 = new JPanel();
		add(panel_3, "2, 4, fill, fill");
		panel_3.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(167dlu;default)"), },
				new RowSpec[] { FormSpecs.MIN_ROWSPEC, }));

		JLabel lblDescription_1 = new JLabel("Description:");
		lblDescription_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_3.add(lblDescription_1, "2, 1, right, default");

		textField_1 = new JTextField();
		if (textField_1.getText().length() == 0)
			textField_1.setText(display[2]);

		editedTestCase.setDescription(display[2]);
		textField_1.setEnabled(true);

		textField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				textField_1.setEnabled(true);
				textField_1.requestFocus();

			}
		});

		textField_1.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				editedTestCase.setDescription(textField_1.getText());
				textField_1.setEnabled(true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				editedTestCase.setId(index);
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
						editedTestCase.setDescription(textField_1.getText());
					}
				});
			}
		});
		panel_3.add(textField_1, "4, 1, fill, default");
		textField_1.setColumns(10);

		JPanel panel = new JPanel();
		add(panel, "2, 6, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(33dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblNewLabel = new JLabel("Creation Date:");
		panel.add(lblNewLabel, "2, 2, right, default");

		textField_2 = new JLabel();
		if (textField_2.getText().length() == 0)
			textField_2.setText(display[5]);

		editedTestCase.setDateTime(display[5]);

		textField_2.setEnabled(true);

		panel.add(textField_2, "4, 2, 7, 1, fill, default");

		JLabel lblNewLabel_1 = new JLabel("Designer:");
		panel.add(lblNewLabel_1, "10, 6, right, default");

		textField_4 = new JTextField();

		textField_4.setText(display[4]);
		editedTestCase.setDesigner(display[4]);
		textField_4.setEnabled(true);

		textField_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				textField_4.setEnabled(true);
				textField_4.requestFocus();
			}
		});

		textField_4.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				editedTestCase.setDesigner(textField_4.getText());
				textField_4.setEnabled(true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				editedTestCase.setId(index);
				textField_4.getDocument().addDocumentListener(new DocumentListener() {

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
						editedTestCase.setDesigner(textField_4.getText());
					}
				});
			}
		});

		panel.add(textField_4, "12, 6, fill, default");
		textField_4.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Status:");
		panel.add(lblNewLabel_2, "2, 4, right, default");

		textField_6 = new JTextField();
		if (textField_6.getText().length() == 0)
			textField_6.setText(display[6]);

		editedTestCase.setStatus(display[6]);
		textField_6.setEnabled(true);

		textField_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				textField_6.setEnabled(true);

				textField_6.requestFocus();

			}
		});

		textField_6.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				editedTestCase.setStatus(textField_6.getText());
				textField_6.setEnabled(true);

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				editedTestCase.setId(index);
				textField_6.getDocument().addDocumentListener(new DocumentListener() {

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
						editedTestCase.setStatus(textField_6.getText());
					}
				});
			}
		});

		panel.add(textField_6, "4, 4, fill, default");
		textField_6.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Test ID: " + display[0]);
		panel.add(lblNewLabel_3, "10, 4, right, default");

		JLabel lblNewLabel_4 = new JLabel("Priority:");
		panel.add(lblNewLabel_4, "2, 6, right, default");

		JComboBox<String> comboBox = new JComboBox<String>();
		panel.add(comboBox, "4, 6, fill, default");
		String one = "1";
		String two = "2";
		comboBox.addItem(one); // 0
		comboBox.addItem(two); // 1

		DbPriorityIndex= Integer.parseInt(display[3]) -1 ; 

		comboBox.setSelectedIndex(DbPriorityIndex);

		comboBox.setEnabled(true);
		selection = display[3];
		editedTestCase.setPriority(selection);
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				comboBox.setEnabled(true);
				comboBox.requestFocus();

			}
		});

		comboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				selection = (String) comboBox.getSelectedItem();
				editedTestCase.setPriority(selection);
				comboBox.setEnabled(true);
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				editedTestCase.setId(index);
				selection = (String) comboBox.getSelectedItem();
				editedTestCase.setPriority(selection);
			}
		});

		testCaseStepVector = db.getTestSteps(index);
		data_step = new Object[testCaseStepVector.size()][4];
		data_step_ID2 = new int[testCaseStepVector.size()];

		data_step_ID = new int[testCaseStepVector.size()];
		row = 0;

		for (TestCaseStep tc : testCaseStepVector) {
			try {
				data_step_ID[row] = tc.getStep_id();
				data_step[row][0] = tc.getName();
				data_step[row][1] = tc.getDescription();
				data_step[row][2] = tc.getExpectedResult();
				row++;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		for (int i = 0 ; i <row; i++)
		{
			data_step[i][0]= i+1;
		}


		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "2, 8, fill, fill");
		scrollPane.setPreferredSize(new Dimension(400, 100));

		table = new JTable();
		table.setModel(new DefaultTableModel(data_step,
				new String[] { "Step Id", "Description", "Expected Result", "Attachment" }) {

			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Object.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

		});


		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem AddStep = new JMenuItem("Add Step");
		JMenuItem deleteStep = new JMenuItem("Delete Step");
		popupMenu.add(AddStep);
		popupMenu.add(deleteStep);
		table.setComponentPopupMenu(popupMenu);
		table.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				ml_AddStep = 0;
				ml_DeleteStep = 0;

				if (SwingUtilities.isRightMouseButton(e)) {
					Point p = e.getPoint();

					// get the row index that contains that coordinate
					rowNumber = table.rowAtPoint(p);
					table.columnAtPoint(p);

					// Get the ListSelectionModel of the JTable
					ListSelectionModel model = table.getSelectionModel();
					table.setFocusable(true);
					model.setSelectionInterval(rowNumber, rowNumber);
					AddStep.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e1) {
							if (ml_AddStep == 0) {
								ml_AddStep = 1;
								DefaultTableModel model = (DefaultTableModel) table.getModel();

								if (row == 0) {
									row++;
									data_step_ID = Arrays.copyOf(data_step_ID, data_step_ID.length + 1);

									table.getSelectedRow();
									table.setFocusable(true);

								} else {
									data_step_ID = Arrays.copyOf(data_step_ID, data_step_ID.length + 1);
									data_step_ID2 = Arrays.copyOf(data_step_ID2, data_step_ID2.length + 1);
									int rightClickedRow = table.getSelectedRow();

									if(rightClickedRow==(table.getRowCount()-1)){		
										model.addRow(new Object[] { "", "" });
										table.setValueAt(rightClickedRow+1+1, rightClickedRow+1, 0);
									}
									else{
										model.insertRow(rightClickedRow+1, new Object[] { "", "" });

										for (int i = table.getRowCount()-1 ; i>rightClickedRow+1; i--){
											table.setValueAt(i+1, i, 0);
										}

										table.setValueAt(rightClickedRow+1+1, rightClickedRow+1, 0);
									}

									table.setFocusable(true);
									model.fireTableDataChanged();

								}
								model.fireTableDataChanged();
							}

						}
					});

					deleteStep.addMouseListener(new MouseAdapter() 
					{
						public void mousePressed(MouseEvent e2) {
							if (ml_DeleteStep == 0) {
								ml_DeleteStep = 1;
								int rightClickedRow = table.getSelectedRow();
								DefaultTableModel model = (DefaultTableModel) table.getModel();
								model.removeRow(rightClickedRow);
								for (int i = rightClickedRow ; i<table.getRowCount(); i++){
									if ( i == (table.getRowCount() -1 )){
										table.setValueAt(table.getRowCount(), i, 0);
									}
									else{
										table.setValueAt(i+1, i, 0);
									}
								}
								model.fireTableDataChanged();
							}
						}
					});

				}
				table.setFocusable(true);
			}
		});
		
		table.setFocusable(true);			
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				table.setSurrendersFocusOnKeystroke(false);
				if (e.getKeyChar() == '\n') {
					e.consume();
					ListSelectionModel model1 = table.getSelectionModel();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					if (row == 0) {
						row++;
						data_step_ID = Arrays.copyOf(data_step_ID, data_step_ID.length + 1);

						table.getSelectedRow();
						table.setFocusable(true);
					} else {

						data_step_ID = Arrays.copyOf(data_step_ID, data_step_ID.length + 1);
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


							model1.setSelectionInterval(rightClickedRow+1, rightClickedRow+1);
						}
						else if (rightClickedRow-1 ==(table.getRowCount()-1)){
							table.setValueAt(rightClickedRow-1+1+1, rightClickedRow+1, 0);
							model.addRow(new Object[] { "", "" });
							model1.setSelectionInterval(rightClickedRow, rightClickedRow);
						}
						else{
							model.insertRow(rightClickedRow, new Object[] { "", "" });
							for (int i = table.getRowCount()-1 ; i>rightClickedRow; i--){
								table.setValueAt(i+1, i, 0);
							}
							table.setValueAt(rightClickedRow+1, rightClickedRow, 0);
							table.setFocusable(true);
							model1.setSelectionInterval(rightClickedRow, rightClickedRow);
						}
						table.setFocusable(true);			
					}
					model.fireTableDataChanged();		
				}
			}
		});
		table.setFocusable(true);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setMaxWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setMaxWidth(214748364);
		table.setEnabled(true);

		JPanel panel_2 = new JPanel();
		add(panel_2, "2, 10, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int col = model.getColumnCount();
				int row = model.getRowCount();
				Object[][] stepTableData = new Object[row][col];
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < 3; j++) {
						if (table.isEditing())
							table.getCellEditor().stopCellEditing();
						stepTableData[i][j] = model.getValueAt(i, j);
					}
				}
				editedTestCase.setId(index);
				editedTestCaseStep.setId(index);
				db.deleteTestCaseSteps(editedTestCaseStep);
				editedTestCaseStep.setStep_ids(row, data_step_ID);
				editedTestCaseStep.setId(index);
				editedTestCaseStep.setSteps(row, col, stepTableData);
				db.editTestCaseStep(editedTestCaseStep);
				db.editTestCase(editedTestCase);
				MainRun.window.refreshTree();
			}
		});
		panel_2.add(btnNewButton, "4, 2");

	}

}
