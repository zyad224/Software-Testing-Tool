/*
This Class describes the test case run
It contains setters and getters for the test case run object
 */


package execution;

public class TestRun {
	private String name;
	private String status;
	private String operatingSystem;
	private int id;
	private int testCaseID;
	private String tester;
	private String date;
	private String time;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTestCaseID() {
		return testCaseID;
	}
	public void setTestCaseID(int testCaseID) {
		this.testCaseID = testCaseID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}
	
	
}
