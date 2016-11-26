package testCases;

public class TestCaseStep {
	private String name;
	private String description;
	private String expectedResult;
	private int[] step_ids ;
	private int  step_id ;
	private String Attachment; 
	private int id;
	private int nrow,ncol;
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
	
	public void setName(String s){
		name = s;
	}
	public void setDescription(String s){
		description = s;
	}
	public void setExpectedResult(String s){
		expectedResult = s;
	}
	public void setStep_ids(int nrow, int s[]){
		this.nrow=nrow;
		step_ids = new int [this.nrow];
		for(int i=0;i<nrow;i++)
		
				step_ids[i] = s[i];
	}
	
	public int[] getStep_ids( )
	{
		return step_ids;
	}
	
	public int getStep_id( )
	{
		return step_id;
	}
	
	public void setStep_id(int s)
	{
		step_id = s ;
	}
	
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public String getExpectedResult(){
		return expectedResult;
	}
	public void setAttachment(String s)
	{
		Attachment = s ;
	}
	public String getAttachment(){
		return Attachment;
	}
	
	public void setId(int s){
		id = s;
	}
	public int getId(){
		return id;
	}

}
