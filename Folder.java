package testCases;

public class Folder {
	private String Folder_Name;
	private int Folder_ID;
	private int Parent_ID;
	
	public String getName(){
		
		return Folder_Name;
	}
	
	public int getFolderID()
	{
		return Folder_ID;
	}
	
	public int getParentID()
	{
		return Parent_ID;
	}
	
	public void setName(String s)
	{
		Folder_Name = s;
	}
	
	public void setId(int s)
	{
		Folder_ID = s;
	}
	
	public void setParentId(int s)
	{
		Parent_ID = s;
	}



	
}
