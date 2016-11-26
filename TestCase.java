package testCases;
public class TestCase {
	private String name;
	private String description;
	private String priority;
	private int id;
	private String designer;
	private String DateTime;
	private String status;
	private int Folder_ID;
	private int nrow,ncol;
	private int stepId; 
	private Object[][] stepTableDataTemp;
	
	public void setSteps(int nrow,int ncol,Object stepTableData[][]){
		this.nrow=nrow;
		this.ncol=ncol;
		stepTableDataTemp=new Object[this.nrow][this.ncol];
		for(int i=0;i<nrow;i++)
			for(int j=0;j<ncol;j++)
				stepTableDataTemp[i][j]=stepTableData[i][j];					
	}
	
	public Object[][] getSteps()
	{
		return stepTableDataTemp;
	}
	
	public int getRow()
	{
		return nrow;
	}
	
	public int getCol()
	{
		return ncol;
	}
	public int getStepId()
	{
		return stepId;
	}
	
	public void setName(String s){
		name = s;
	}
	public void setDescription(String s){
		description = s;
	}
	public void setPriority(String s){
		priority = s;
	}
	public void setId(int s){
		id = s;
	}
	public void setDesigner(String s){
		designer = s;
	}
	public void setDateTime(String s){
		DateTime = s;
	}
	public void setStatus(String s){
		status = s;
	}
	public void setFolderID(int s){
		Folder_ID = s ;
	}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public String getPriority(){
		return priority;
	}
	public int getId(){
		return id;
	}
	public String getDesigner(){
		return designer;
	}
	public String getDateTime(){
		return DateTime;
	}
	public String getStatus(){
		return status;
	}
	public int getFolderID(){
		return Folder_ID;
	}
	
	
	
}
