/*
 * This Class executes the testcase
 * it shows the user the steps of the testcase
 * The user should be able to create a defect if a step fails.
 */

package executor;

import java.awt.Color;
import java.util.Vector;
import java.util.Date;

import java.awt.Font;
import javax.swing.ImageIcon;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;
import testCases.TestCase;
import testCases.TestCaseStep;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import middleware.DbConnection;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Executor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int  testID;
	private int idTestCase_Run;
	private  Vector<TestCaseStep> testCaseStepVector = new Vector<TestCaseStep>();
	private DbConnection db = new DbConnection();
	private  int stepsCounter=0;
	private String  tempActualResult = " ";
	private int tempStepId;
	private String tempAttachment;
	private Vector<TempTestCaseStepRun> actualResultVector;
	private TempTestCaseStepRun tempTestCaseStepRun;
	private JTable table;
	private Object[][] data;
	private int row;
	private String expectedResult;

	public Executor( int testID, int idTestCaseRun) {

		//////////////////////////////////////////GUI//////////////////////////////////////////////////

		setTitle("Test Case Executor");
		setBounds(100, 100, 462, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout(new MigLayout("", "[55.00px,grow]", "[28.00px:25.00px][70.00,grow][][41.00][1.00px,grow][19.00]"));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 1,grow");
		this.testID=testID;
		idTestCase_Run=idTestCaseRun;
		TestCase tc = db.getTestCase(testID);
		testCaseStepVector=db.getTestSteps(this.testID);// get stepvector

		data = new Object[testCaseStepVector.size()][4];
		row = 0;		
		for (TestCaseStep tcs : testCaseStepVector)
		{
			try {
				data[row][1] = tcs.getName();
				data[row][2] = "No Run";
				data[row][3] = new Date();
				row++;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		table = new JTable();
		table.setModel(new DefaultTableModel(
				data,
				new String[] {
						 "Step Name", "Status", "Execution Date"
				}
				));
		table.getColumnModel().getColumn(2).setMinWidth(150);
		scrollPane.setViewportView(table);


		JLabel lblStepDescription = new JLabel("Step Name");
		lblStepDescription.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblStepDescription, "cell 0 2");


		JTextPane description = new JTextPane();
		description.setEditable(false);
		description.setBackground(Color.LIGHT_GRAY);
		contentPane.add(description, "cell 0 3,alignx left,aligny center");

		JPanel panel_1 = new JPanel();

		contentPane.add(panel_1, "cell 0 4,grow");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("120px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
						RowSpec.decode("19px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),}));
		JLabel lblExpectedResult = new JLabel("Expected Result");
		panel_1.add(lblExpectedResult, "1, 1, left, top");
		lblExpectedResult.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel label_1 = new JLabel("Actual Result");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(label_1, "3, 1");

		JTextPane textPane_1 = new JTextPane();
		panel_1.add(textPane_1, "1, 3, fill, fill");

		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		panel_1.add(textPane, "3, 3, fill, fill");
		setContentPane(contentPane);


		JLabel lblTestCaseName = new JLabel("Test Case Name");
		lblTestCaseName.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblTestCaseName, "cell 0 0,alignx left,growy");

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, "cell 0 5,grow");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
				new RowSpec[] {
						FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel failed=new JLabel("");
		panel.add(failed,"3, 1");
		JLabel passed= new JLabel("");
		panel.add(passed,"1, 1");
		failed.setIcon(new ImageIcon("icons/failed.png"));
		passed.setIcon(new ImageIcon("icons/passed.png"));

		JButton btnEndExecution = new JButton("End Execution");
		panel.add(btnEndExecution, "7, 1");
		////////////////////////////////////////////////////////////////////////////////////////////		

		lblTestCaseName.setText(tc.getName());
		actualResultVector=new Vector<TempTestCaseStepRun>();
		TempTestCaseStepRun trial = new TempTestCaseStepRun();
		trial.tempActualResult = " ";
		actualResultVector.setSize(testCaseStepVector.size());


		//Initializing the actualResult with ""
		for(int i=0;i<actualResultVector.size();i++)
		{
			actualResultVector.setElementAt(trial, i);
		}



		tempTestCaseStepRun=new TempTestCaseStepRun();
		lblStepDescription.setText("Description:");
		description.setText((testCaseStepVector.elementAt(stepsCounter).getDescription().toString()));
		textPane_1.setText((testCaseStepVector.elementAt(stepsCounter).getExpectedResult().toString()));


		/*When clicking the false button the executor 
		 *creates a defect and makes this step false
		 */
		failed.addMouseListener ( new MouseAdapter ()
		{
			public void mousePressed ( MouseEvent e )
			{  
				tempStepId=testCaseStepVector.elementAt(stepsCounter).getStep_id();
				tempAttachment=testCaseStepVector.elementAt(stepsCounter).getAttachment();
				tempTestCaseStepRun=new TempTestCaseStepRun();
				tempTestCaseStepRun.tempActualResult=tempActualResult;
				tempTestCaseStepRun.tempStepID=tempStepId;
				tempTestCaseStepRun.attachment=tempAttachment;
				JOptionPane.showMessageDialog(null, "Step Failed");
				tempTestCaseStepRun.status = "Failed";

				table.getModel().setValueAt("Failed", stepsCounter, 2);
				table.getModel().setValueAt(new Date(), stepsCounter, 3);

				if((stepsCounter!=testCaseStepVector.size()-1))
				{
					actualResultVector.setElementAt(tempTestCaseStepRun, stepsCounter);
					//stepsCounter++;
					lblStepDescription.setText((testCaseStepVector.elementAt(stepsCounter).getName().toString())+"/"+testCaseStepVector.size());
					description.setText((testCaseStepVector.elementAt(stepsCounter).getDescription().toString()));
					textPane_1.setText((testCaseStepVector.elementAt(stepsCounter).getExpectedResult().toString()));
					textPane.setText(actualResultVector.elementAt(stepsCounter).tempActualResult);	        	  
				}
				else
				{
					actualResultVector.setElementAt(tempTestCaseStepRun, stepsCounter);
				}
			}
		} );

		/*

		 When clicking on the right button the executor
		 swipe between the steps of the testcase

		 */
		passed.addMouseListener ( new MouseAdapter ()
		{
			public void mousePressed ( MouseEvent e )
			{
				tempStepId=testCaseStepVector.elementAt(stepsCounter).getStep_id();
				tempAttachment=testCaseStepVector.elementAt(stepsCounter).getAttachment();
				tempTestCaseStepRun=new TempTestCaseStepRun();
				tempTestCaseStepRun.tempActualResult=tempActualResult;
				tempTestCaseStepRun.tempStepID=tempStepId;
				tempTestCaseStepRun.attachment=tempAttachment;

				table.getModel().setValueAt("Succeeded", stepsCounter, 2);
				table.getModel().setValueAt(new Date(), stepsCounter, 3);


				if((stepsCounter!=testCaseStepVector.size()-1))
				{
					actualResultVector.setElementAt(tempTestCaseStepRun, stepsCounter);
					stepsCounter++;
					table.setRowSelectionInterval(stepsCounter, stepsCounter);
					lblStepDescription.setText((testCaseStepVector.elementAt(stepsCounter).getName().toString())+"/"+testCaseStepVector.size());
					description.setText((testCaseStepVector.elementAt(stepsCounter).getDescription().toString()));
					textPane_1.setText((testCaseStepVector.elementAt(stepsCounter).getExpectedResult().toString()));   
					textPane.setText(actualResultVector.elementAt(stepsCounter).tempActualResult);  
				}
				else
				{
					actualResultVector.setElementAt(tempTestCaseStepRun, stepsCounter);
				}
			}
		} );

		textPane.getDocument().addDocumentListener(new DocumentListener() {

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
				tempActualResult=textPane.getText();
				tempTestCaseStepRun.tempActualResult=tempActualResult;

			}
		});

		textPane_1.getDocument().addDocumentListener(new DocumentListener() {

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
				expectedResult =textPane_1.getText();
				
			}
		});

		btnEndExecution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestCaseStep ts = new TestCaseStep();
				ts = testCaseStepVector.elementAt(stepsCounter);
				ts.setExpectedResult(expectedResult);
				db.editTestStep(ts);
				ExecutorDialog executorDialog = new ExecutorDialog(actualResultVector, idTestCase_Run);
				executorDialog.setVisible(true);
				if(!executorDialog.isActive()) dispose(); 
			}
		});

		table.addMouseListener(new MouseAdapter ()
		{
			public void mousePressed ( MouseEvent e )
			{
				stepsCounter = table.getSelectedRow();
				lblStepDescription.setText((testCaseStepVector.elementAt(stepsCounter).getName().toString())+"/"+testCaseStepVector.size());
				description.setText((testCaseStepVector.elementAt(stepsCounter).getDescription().toString()));
				textPane_1.setText((testCaseStepVector.elementAt(stepsCounter).getExpectedResult().toString()));
				textPane.setText(actualResultVector.elementAt(stepsCounter).tempActualResult);

			}
		} );


	}

}