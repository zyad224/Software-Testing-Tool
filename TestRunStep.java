/*
 This Class describes the Step Run. Each Step run 
 has id, actual result, status(passed/failed)
 testcaserun_ID, and testcasestep_ID. it contains setters and
 getters for the TestCaseStepRun object
 */

package execution;

public class TestRunStep {

	private int id;
	private String actualResult;
	private String status;
	private int idTestCaseRun;    //foreign key
	private int idTestCaseStep;   //foreign key
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String ActualResult) {
		this.actualResult = ActualResult;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
	public int getIdTestCaseRun(){
		return idTestCaseRun;
	}
	public void setIdTestCaseRun(int idTestCaseRun ){
		this.idTestCaseRun=idTestCaseRun;
	}
	
	
	public int getIdTestCaseStep(){
		return idTestCaseStep;
	}
	public void setIdTestCaseStep(int idTestCaseStep ){
		this.idTestCaseStep=idTestCaseStep;
	}
	
		
	
	
	
}
