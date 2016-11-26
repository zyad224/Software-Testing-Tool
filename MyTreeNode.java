package uiComponents;
/* This class is used as the UserObject for any DefaultMutableTreeNode
 * in the overviewTree in the MainView class
 * it contains several needed fields: the type of the node, its name and id in the database
 */

public class MyTreeNode extends Object {

	private char type;
	/* possible types:
	 * f = folder
	 * r = requirement
	 * t = test case
	 * p = project
	 * d = defect
	 * o = report
	 */
	private String name; //name displayed in the tree
	private int index; //index in the tree
	private int ID; // primary key in the database
	
	public MyTreeNode(String name, char type, int index, int ID){
		this.name = name;
		this.type = type;
		this.index = index;
		this.ID = ID;
	}
	
	public String getName(){
		return name;
	}
	
	public char getType(){
		return type;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setName(String s){
		name = s ;
	}
	public void setType(char s){
		type = s;
	}
	
	public void setIndex(int s){
		index = s;
	}
	
	public int getID()
	{
		return ID;
	}
	
	

}
