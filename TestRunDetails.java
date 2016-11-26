/*
 This Class is responsible for the data of the 
 test run configuration such as test run name, operating system,
 date, creation date
 */

package executor;

import javax.swing.JPanel;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import execution.TestRun;
import middleware.DbConnection;
import testCases.TestCase;

import com.jgoodies.forms.layout.FormSpecs;

import java.awt.Font;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestRunDetails extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel textField;
	private JTextField txtZyad;
	private JTextField textField_1;
    private int testid;
    private Object[] data;
    private  int row;
    private static int counter=0;
	private DbConnection db = new DbConnection();
	private static Vector<TestCase> testCasesVector = new Vector<TestCase>();
	private static Vector<TestRun> testCaseRunVector = new Vector<TestRun>();

	
	@SuppressWarnings("unused")
	public TestRunDetails(int testID, int ElId) {
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		setBounds(100, 100, 496, 350);
		
		JLabel lblTestCaseName = new JLabel("TestCase Name");
		lblTestCaseName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(lblTestCaseName, "2, 2, center, default");
		
		JLabel lblRun = new JLabel("Run#:");
		lblRun.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(lblRun, "2, 4, center, default");
		
		
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "2, 6, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,}));
		JLabel lblCreationDate = new JLabel("Creation Date:");
		panel_1.add(lblCreationDate, "1, 1, right, default");
		
		textField = new JLabel();	
		textField.setText(new Date().toString());
		panel_1.add(textField, "3, 1, fill, default");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "2, 8, fill, fill");

		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(70dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(28dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(23dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(67dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblOperatingSystem = new JLabel("Operating System:");
		panel.add(lblOperatingSystem, "1, 1, right, default");
		
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panel.add(textField_1, "3, 1, fill, default");
		
		
		
		
		
		JLabel lblDesigner = new JLabel("Tester:");
		panel.add(lblDesigner, "7, 1, right, default");
		
		txtZyad = new JTextField();
		panel.add(txtZyad, "9, 1, fill, default");
		txtZyad.setColumns(10);
			
			JLabel lblTestId = new JLabel("Test ID:");
			panel.add(lblTestId, "1, 3, right, default");
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
			panel.add(lblNewLabel, "3, 3, left, default");
			
			JPanel panel_2 = new JPanel();
			getContentPane().add(panel_2, "2, 10, fill, fill");
			panel_2.setLayout(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("max(230dlu;default):grow"),
					FormSpecs.RELATED_GAP_COLSPEC,
					FormSpecs.DEFAULT_COLSPEC,},
				new RowSpec[] {
					FormSpecs.DEFAULT_ROWSPEC,}));
			
			
			
			
			
			testid=testID;
			testCasesVector = db.getTestCases();
			testCaseRunVector=db.getTestCaseRuns();
			
			
			data = new Object [testCaseRunVector.size()];
			row = 0 ;
			
			for (TestRun tcr : testCaseRunVector)
			{
				try {
					data[row] = tcr.getTestCaseID();
					
					row++;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			row =0 ;
			counter=0;
			for (TestRun tcr : testCaseRunVector)
			{
				try {
					if (testid == (Integer) data[row])
					{
						counter++;
					}
					row++;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			counter++;
			
			
			
			
			for (TestCase tc : testCasesVector)
			{
				if(testid==tc.getId())
				{
					lblTestId.setText("Test ID:    " + Integer.toString(tc.getId()));
					lblTestCaseName.setText("Test Name:  "+ tc.getName() );
					lblRun.setText("Run#:" + counter);
				}
					
			}
			
			
			/*
			 * When pressing create, the testrun is created
			 * and inserted in the database. After that the
			 * executor is called and executes that test run
			 */
			JButton btnCreate = new JButton("Create");
			btnCreate.addMouseListener(new MouseAdapter() {
				@Override
				
				public void mouseClicked(MouseEvent e) {
	
					//testcase run created and inserted into database
					TestRun TestCaseRun= new TestRun();
					TestCaseRun.setTime(new Date().toString());
					TestCaseRun.setName(lblTestCaseName.getText());
					TestCaseRun.setOperatingSystem(textField_1.getText());
					TestCaseRun.setTester(txtZyad.getText());
					TestCaseRun.setTestCaseID(testid);
					
					
					db.createTestCaseRun(TestCaseRun, ElId);
					testCaseRunVector=db.getTestCaseRuns();
					dispose();
 
					Executor executor = new Executor(testid, testCaseRunVector.lastElement().getId());
					executor.setVisible(true);
				}
			});
			panel_2.add(btnCreate, "1, 1, right, default");
	
	
		

		

		


	}

}
