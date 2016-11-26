package middleware;

import com.mysql.jdbc.Connection;
import execution.ExecutionList;
import execution.TestRun;
import execution.TestRunStep;
import testCases.Folder;
import testCases.TestCase;
import testCases.TestCaseStep;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DbConnection {
	ResultSet resultSet = null;
	ResultSet testCasesResultSet = null;
	ResultSet testCaseRunsResultSet = null;
	ResultSet testCasePerExecutionListResultSet = null;
	ResultSet executionListsResultSet = null;
	Connection connection = null;
	Statement statememt = null;
	Vector<TestCase> testCaseVector;
	Vector<TestRun> testCaseRunVector;
	Vector<Folder> folderVector;
	Vector<ExecutionList> executionListVector;

	public DbConnection(){
		try{
			connection = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false","root","0000");
			statememt = connection.createStatement();
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	//Get all TestCases from Database
	public Vector<TestCase> getTestCases(){
		testCaseVector = new Vector<TestCase>();
		TestCase testCase = new TestCase();
		try {
			testCasesResultSet = statememt.executeQuery("select * from testcase");
			testCasesResultSet.first();	
			do{ 
				testCase = new TestCase();
				testCase.setId(testCasesResultSet.getInt("idTestCase"));
				testCase.setName(testCasesResultSet.getString("Name"));
				testCase.setDescription(testCasesResultSet.getString("Description"));
				testCase.setDateTime(testCasesResultSet.getString("CreationDateTimes"));
				testCase.setPriority(testCasesResultSet.getString("Priority"));
				testCase.setDesigner(testCasesResultSet.getString("Designer"));
				testCase.setStatus(testCasesResultSet.getString("Status"));
				testCase.setFolderID(testCasesResultSet.getInt("idFolder"));
				testCaseVector.add(testCase);
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseVector;
	}
	
	//Get TestCase by idTestCase
	public TestCase getTestCase(int idTestCase){
		TestCase testCase = new TestCase();
		try {
			testCasesResultSet = statememt.executeQuery("select * from testcase where idTestCase = '"+ idTestCase + "';");
			testCasesResultSet.first();	
			do{ 
				testCase = new TestCase();
				testCase.setId(testCasesResultSet.getInt("idTestCase"));
				testCase.setName(testCasesResultSet.getString("Name"));
				testCase.setDescription(testCasesResultSet.getString("Description"));
				testCase.setDateTime(testCasesResultSet.getString("CreationDateTimes"));
				testCase.setPriority(testCasesResultSet.getString("Priority"));
				testCase.setDesigner(testCasesResultSet.getString("Designer"));
				testCase.setStatus(testCasesResultSet.getString("Status"));
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCase;
	}
	
	//Get Steps of a TestCase by idTestCase
	public Vector<TestCaseStep> getTestSteps(int idTestCase){
		Vector<TestCaseStep> testCaseStepVector = new Vector<TestCaseStep>();
		TestCaseStep testCaseStep = new TestCaseStep();
		try {
			testCasesResultSet = statememt.executeQuery("select * from teststep where idTestCase = '" + idTestCase + "';");
			testCasesResultSet.first();	
			do{ 
				testCaseStep = new TestCaseStep();
				testCaseStep.setName(testCasesResultSet.getString("Name"));
				testCaseStep.setDescription(testCasesResultSet.getString("Description"));
				testCaseStep.setExpectedResult(testCasesResultSet.getString("ExpectedResult"));
				testCaseStep.setStep_id(testCasesResultSet.getInt("idTestStep"));
				testCaseStepVector.add(testCaseStep);
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseStepVector;
	}

	public Vector<TestRunStep> getTestRunSteps(int testRunId){
		Vector<TestRunStep> testCaseStepRunVector = new Vector<TestRunStep>();
		TestRunStep testCaseStepRun = new TestRunStep();
		try {
			testCasesResultSet = statememt.executeQuery("select * from testrunstep where idTestRun = '" + testRunId + "';");
			testCasesResultSet.first();	
			do{ 
				testCaseStepRun = new TestRunStep();
				testCaseStepRun.setId(testCasesResultSet.getInt("idTestRunStep"));
				testCaseStepRun.setStatus(testCasesResultSet.getString("Status"));
				testCaseStepRun.setActualResult(testCasesResultSet.getString("ActualResult"));
				testCaseStepRun.setIdTestCaseStep(testCasesResultSet.getInt("idTestStep"));
				testCaseStepRunVector.add(testCaseStepRun);
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseStepRunVector;
	}

	public TestCaseStep getTestStep(int testStepId){
		TestCaseStep testCaseStep = new TestCaseStep();
		try {
			testCasesResultSet = statememt.executeQuery("select * from teststep where idTestStep = '" + testStepId + "';");
			testCasesResultSet.first();	
			do{ 
				testCaseStep = new TestCaseStep();
				testCaseStep.setName(testCasesResultSet.getString("Name"));
				testCaseStep.setDescription(testCasesResultSet.getString("Description"));
				testCaseStep.setExpectedResult(testCasesResultSet.getString("ExpectedResult"));
				testCaseStep.setStep_id(testCasesResultSet.getInt("idTestStep"));
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseStep;
	}

	public void createFolder(Folder folder, boolean root_flag){
		String query = null;
		if (root_flag)
			 query = "insert into folder (idFolder, Name) Values ('" + folder.getFolderID() + "','" + folder.getName() + "');";
		else
		 query = "insert into folder (idFolder, Name, idParentFolder) Values ('" + folder.getFolderID() + "','" + folder.getName() + "', '" + folder.getParentID() + "');";
		try {
			statememt.executeUpdate(query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}


	}

	public Vector<TestCase> getTestCases(int folderId){
		testCaseVector = new Vector<TestCase>();
		TestCase testCase = new TestCase();
		try {
			testCasesResultSet = statememt.executeQuery("select * from testcase where idFolder = '"+ folderId + "';");
			testCasesResultSet.first();	
			do{ 
				testCase = new TestCase();
				testCase.setId(testCasesResultSet.getInt("idTestCase"));
				testCase.setName(testCasesResultSet.getString("Name"));
				testCase.setDescription(testCasesResultSet.getString("Description"));
				testCase.setDateTime(testCasesResultSet.getString("CreationDateTimes"));
				testCase.setPriority(testCasesResultSet.getString("Priority"));
				testCase.setDesigner(testCasesResultSet.getString("Designer"));
				testCase.setStatus(testCasesResultSet.getString("Status"));
				testCase.setFolderID(testCasesResultSet.getInt("idFolder"));
				testCaseVector.add(testCase);
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseVector;
	}
	
	public Vector<Folder> getFolders(){
		folderVector = new Vector<Folder>();
		Folder folder = new Folder();
		try {
			testCasesResultSet = statememt.executeQuery("select * from Folder");
			testCasesResultSet.first();	
			do{ 
				folder = new Folder();
				folder.setId(testCasesResultSet.getInt("idFolder"));
				folder.setName(testCasesResultSet.getString("Name"));
				folder.setParentId(testCasesResultSet.getInt("idParentFolder"));
				folderVector.add(folder);
			} while(testCasesResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return folderVector;
	}

	public Folder getFolder(int id){
		Folder folder = new Folder();
		try {
			testCasesResultSet = statememt.executeQuery("select * from folder where idFolder = '"+ id + "';");
			testCasesResultSet.first();	
			folder = new Folder();
			folder.setId(testCasesResultSet.getInt("idFolder"));
			folder.setName(testCasesResultSet.getString("Name"));
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return folder;

	}
	
	public void editFolder(Folder folder){
		String _query = "UPDATE folder SET Name = '" + folder.getName() +  "' where idFolder = '" + folder.getFolderID() +"';";
		try {			
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public void createTestCase(TestCase tc, boolean root_flag){
		String query = null;
		if (root_flag)
			query = "insert into testcase (idTestCase, Name, Description, Priority, Designer, CreationDateTimes, Status) Values ('" + tc.getId() + "','" + tc.getName() +"','" + tc.getDescription() + "', '" + tc.getPriority() + "',  '" + tc.getDesigner() + "', '" + tc.getDateTime() + "' , '" + tc.getStatus() + "');";
		else
			query = "insert into testcase (idTestCase, Name, Description, Priority, Designer, CreationDateTimes, Status, idFolder) Values ('" + tc.getId() + "','" + tc.getName() +"','" + tc.getDescription() + "', '" + tc.getPriority() + "',  '" + tc.getDesigner() + "', '" + tc.getDateTime() + "' , '" + tc.getStatus() + "', '" + tc.getFolderID() + "');";
		try {
			statememt.executeUpdate(query);
			for(int i=0;i<tc.getRow();i++)
			{
				String  _query2="INSERT INTO teststep ( Name, Description, ExpectedResult,  idTestCase) Values (?,?,?,?)" ;
				PreparedStatement statement = connection.prepareStatement(_query2);
				Object name = ((tc.getSteps())[i][0]);
				statement.setObject(1,name);
				statement.setObject(2,((tc.getSteps())[i][1]));
				statement.setObject(3,((tc.getSteps())[i][2]));
				statement.setInt(4,(Integer) tc.getId());
				statement.executeUpdate();			
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public void deleteTestCaseSteps(TestCaseStep tc){
		String query = "DELETE FROM teststep where idTestCase = '" + tc.getId() +"';";
		try {
			statememt.executeUpdate(query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public void deleteFolder(int id, Vector<TestCase> testCaseVector){
		String query = "delete from Folder where idFolder = '" + id +"';";
		try {
			statememt.executeUpdate(query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void deleteTestCase(int id){
		String _query = "delete from testcase where idTestCase = '" + id +"';";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void deleteStep(int id){
		String _query = "delete from teststep where idTestStep = '" + id +"';";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void editTestCase(TestCase tc){
		String _query = "UPDATE testcase SET Name = '" + tc.getName() + "',Description='" + tc.getDescription()   
		+ "',Priority='" + tc.getPriority()   + "',Designer='" + tc.getDesigner() + "',CreationDateTimes='" 
		+ tc.getDateTime() + "',Status='" + tc.getStatus() +  "' where idTestCase = '" + tc.getId() +"';";
		try {			
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public int getIdTestCasePerExecutionList (int idTestCase, int idExecutionList){
		int idTestCasePerExecutionList = -1; // set to -1 if test case is not linked to execution list and therefore is not present in this table
		try {
			testCaseRunsResultSet = statememt.executeQuery("select * from testCasePerExecutionList where idTestCase =' " + idTestCase + "' and idExecutionList = '" + idExecutionList +"';");
			testCaseRunsResultSet.first();	
			idTestCasePerExecutionList = testCaseRunsResultSet.getInt("idTestCasePerExecutionList");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return idTestCasePerExecutionList;				
	}
	
	public void createTestCaseRun(TestRun tcr, int idExecutionList)
	{
		
		int id = getIdTestCasePerExecutionList(tcr.getTestCaseID(), idExecutionList);
		String _query = "insert into testrun ( Name, Status,"
				+ " OperatingSystem, DateTime, Tester, idTestCasePerExecutionList) Values ('" 
				+ tcr.getName() +"','Passed',   '" + tcr.getOperatingSystem() + "', '" 
				+ tcr.getTime() + "' , '" + tcr.getTester()+ "','" + id + "');";

		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public void editTestStep (TestCaseStep ts){
		String _query = "update teststep set Name ='"+ ts.getName() + "', description = '" + ts.getDescription()
		+ "', ExpectedResult = '" + ts.getExpectedResult() + "' where idteststep = '" + ts.getId() +"';";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void editTestCaseStep(TestCaseStep tc){
		try {
			for(int i=0;i<tc.getRow();i++)
			{
				String  _query2="INSERT INTO teststep (idTestStep, Name,Description, ExpectedResult, idTestCase) Values (?,?,?,?,?)" ;
				PreparedStatement statement = connection.prepareStatement(_query2);
				statement.setInt(1, (Integer) (tc.getStep_ids())[i]);
				Object name = ((tc.getSteps())[i][0]);
				statement.setObject(2,name);
				statement.setObject(3,((tc.getSteps())[i][1]));
				statement.setObject(4,((tc.getSteps())[i][2]));
				statement.setInt(5,(Integer) tc.getId());
				statement.executeUpdate();
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public Vector<TestRun> getTestCaseRuns(){
		testCaseRunVector = new Vector<TestRun>();
		TestRun testCaseRun = new TestRun();
		try {
			testCaseRunsResultSet = statememt.executeQuery("select * from testrun");
			testCaseRunsResultSet.first();	
			do{ 
				testCaseRun = new TestRun();
				testCaseRun.setId(testCaseRunsResultSet.getInt("idTestRun"));
				testCaseRun.setTestCaseID(testCaseRunsResultSet.getInt("idTestCasePerExecutionList"));
				testCaseRun.setName(testCaseRunsResultSet.getString("Name"));
				testCaseRun.setTime(testCaseRunsResultSet.getString("Datetime"));
				testCaseRun.setStatus(testCaseRunsResultSet.getString("Status"));
				testCaseRun.setTester(testCaseRunsResultSet.getString("Tester"));
				testCaseRun.setOperatingSystem(testCaseRunsResultSet.getString("OperatingSystem"));
				testCaseRunVector.add(testCaseRun);
			} while(testCaseRunsResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseRunVector;		
	}

	public Vector<TestRun> getTestCaseRuns(int id){
		testCaseRunVector = new Vector<TestRun>();
		TestRun testCaseRun = new TestRun();
		try {
			testCaseRunsResultSet = statememt.executeQuery("select * from testrun where idTestCasePerExecutionList = '" + id + "';");
			testCaseRunsResultSet.first();	
			do{ 
				testCaseRun = new TestRun();
				testCaseRun.setId(testCaseRunsResultSet.getInt("idTestRun"));
				testCaseRun.setTestCaseID(testCaseRunsResultSet.getInt("idTestCasePerExecutionList"));
				testCaseRun.setName(testCaseRunsResultSet.getString("Name"));
				testCaseRun.setTime(testCaseRunsResultSet.getString("Datetime"));
				testCaseRun.setStatus(testCaseRunsResultSet.getString("Status"));
				testCaseRun.setTester(testCaseRunsResultSet.getString("Tester"));
				testCaseRun.setOperatingSystem(testCaseRunsResultSet.getString("OperatingSystem"));
				testCaseRunVector.add(testCaseRun);
			} while(testCaseRunsResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseRunVector;		
	}

	public void createTestCaseStepRun(TestRunStep tcsr)
	{
		String _query = "insert into testrunstep (idTestRunStep, ActualResult, Status,"
				+ "  idTestRun, idTestStep) Values ('" + 
				tcsr.getId() + "','" + tcsr.getActualResult() +"','" + tcsr.getStatus() + "',   '" 
				+ tcsr.getIdTestCaseRun() + "' , '" + tcsr.getIdTestCaseStep() + "');";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public TestRun getTestCaseRun(int id){
		TestRun testCaseRun = new TestRun();
		try {
			testCasesResultSet = statememt.executeQuery("select * from testrun where idTestRun = '"+ id + "';");
			testCasesResultSet.first();	
			testCaseRun.setId(testCasesResultSet.getInt("idTestRun"));
			testCaseRun.setName(testCasesResultSet.getString("Name"));
			testCaseRun.setOperatingSystem(testCasesResultSet.getString("OperatingSystem"));
			testCaseRun.setTester(testCasesResultSet.getString("Tester"));
			testCaseRun.setStatus(testCasesResultSet.getString("Status"));
			testCaseRun.setTime(testCasesResultSet.getString("DateTime"));
			testCaseRun.setTestCaseID(testCasesResultSet.getInt("idTestCase"));
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseRun;
	}

	public void setTestRunStatus(int id, String status){
		String _query = "update testrun set Status ='" + status +	"' where idTestRun = '" + id +"';";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void createExecutionList(ExecutionList el){
		String _query = "insert into executionlist (name, creationdatetime) Values ('" 
				+ el.getName() + "','" + el.getCreationDate() + "');";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void addTestCaseToExecutionList(int testCaseId, int executionListId){
		String _query = "insert into testcaseperexecutionlist (idTestCase, idExecutionList) Values ('" 
				+ testCaseId + "','" + executionListId + "');";
		try {
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public Vector<ExecutionList> getExecutionLists() {
		ExecutionList el = new ExecutionList();
		executionListVector = new Vector<ExecutionList>();
		try {
			executionListsResultSet = statememt.executeQuery("select * from executionlist");
			executionListsResultSet.first();	
			do{ 
				el = new ExecutionList();
				el.setId(executionListsResultSet.getInt("idExecutionList"));
				el.setName(executionListsResultSet.getString("Name"));
				el.setCreationDate(executionListsResultSet.getString("CreationDateTime"));
				executionListVector.addElement(el);
			} while(executionListsResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return executionListVector;
	}
	
	public ExecutionList GetExecutionList( int id )
	{
		ExecutionList EL = new ExecutionList();
		try {
			testCasesResultSet = statememt.executeQuery("select * from executionlist where idExecutionList = '"+ id + "';");
			testCasesResultSet.first();	
			EL = new ExecutionList();
			EL.setId(testCasesResultSet.getInt("idExecutionList"));
			EL.setName(testCasesResultSet.getString("Name"));
			EL.setCreationDate(testCasesResultSet.getString("CreationDateTime"));
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return EL;
	}
	
	public Vector<TestCase> getTestCasesFromExecutionList (int executionListId){
		Vector<Integer> testCaseIds = getTestCaseIdsFromExecutionList(executionListId);
		TestCase tc = new TestCase();
		for (int i : testCaseIds){
			tc = new TestCase();
			tc = getTestCase(i);
			testCaseVector.add(tc);
		}
		return testCaseVector;
	}
	
	public Vector<Integer> getTestCaseIdsFromExecutionList(int executionListId){
		Vector<Integer> testCaseIds = new Vector<Integer>();
		testCaseVector = new Vector<TestCase>();
		int idTestCase = 0;
		try {
			testCasePerExecutionListResultSet = statememt.executeQuery("select * from TestCasePerExecutionList where idExecutionList = '" + executionListId + "';");
			testCasePerExecutionListResultSet.first();	
			do{ 
				idTestCase = testCasePerExecutionListResultSet.getInt("idTestCase");
				testCaseIds.add(idTestCase);
			} while(testCasePerExecutionListResultSet.next());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return testCaseIds;
		
	}
	
	public void EditExecutionList(ExecutionList el)
	{
		String _query = "UPDATE executionlist SET Name = '" + el.getName() +  "' where idExecutionList = '" + el.getId() +"';";
		try {			
			statememt.executeUpdate(_query);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}



}