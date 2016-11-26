package renderers;

import java.awt.Component;
import execution.ExecutionList;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import main.MainRun;
import middleware.DbConnection;
import testCases.Folder;
import testCases.TestCase;
import uiComponents.MyTreeNode;



// The class has a problem in differentiating between Execution List module and testcase module when it comes to rendering the trees
// A possible solution is to implement another Renderer for the Execution List Tree because the indices of the nodes might conflict with that of the test case module

public class TreeRenderer extends DefaultTreeCellRenderer {  // TreeRenderer is completely customized because the node object shown to the user consists of 4 different data types. 
	
    Icon testCase;
    Icon openFolder;
    Icon closedFolder;
    private static DbConnection db = new DbConnection();
	private static Vector<TestCase> testCaseVector = db.getTestCases();
	private static Vector<Folder> FolderVector = db.getFolders();
	private static Vector<ExecutionList> ExecutionListVector  = db.getExecutionLists();
	private Folder folder = new Folder();
	private static TestCase testCase2 = new TestCase();
	private static ExecutionList EL2 = new ExecutionList();
	private TestCase editedTestCase = new TestCase();
	private Folder editedFolder = new Folder();
	private ExecutionList editedExecutionList = new ExecutionList();
	private boolean []isExpanded_R;
	private int []ID_R;
	private int [] ChildTCs;
    private MyTreeNode[] myTreecopy;

    public TreeRenderer(boolean []isExpanded, int []FolderID) { // the constructor receives the IsExpanded array to determine the expanded folders
        testCase = new ImageIcon("icons/test_tube.png"); // Images/Icons
        openFolder = new ImageIcon("icons/open_folder.png");
        closedFolder = new ImageIcon("icons/closed_folder.png");
        myTreecopy = new MyTreeNode[testCaseVector.size()+FolderVector.size() +2];
        isExpanded_R = new boolean [testCaseVector.size()+FolderVector.size() +2];
        ID_R = new int [testCaseVector.size()+FolderVector.size() +2];
        ChildTCs = new int [testCaseVector.size()+FolderVector.size() +2];
        for(int i = 1 ; i <FolderVector.size()+1;i++)
        {
        	isExpanded_R [i]= isExpanded[i]; 
        	ChildTCs [i] = 0;

        }

        
    }

    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        
 
        // here, we obtain a copy of the tree nodes, and arrange the indices according to the type and the expansion of a folder
        
        
        FolderVector = db.getFolders();
        testCaseVector = db.getTestCases();
        ExecutionListVector  = db.getExecutionLists();
        
        if(row==0){ //root
        	
        	setOpenIcon(openFolder);
        	setClosedIcon(closedFolder);
        
        	return this;
        	
        	}
        else {
        	DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)value;
            MyTreeNode nodeInfo =
                    (MyTreeNode)(node.getUserObject());
          
            if (nodeInfo.getType() == 'e') // if the node is a execution list
            {
            	
            	int j = 1 ;
            	for (ExecutionList el : ExecutionListVector )
            	{
            		myTreecopy[j] = new MyTreeNode(el.getName(),'e', j,  el.getId());
            		j++;
            		testCaseVector= db.getTestCasesFromExecutionList(el.getId());
            		
            		for (TestCase tc: testCaseVector)
            		{
            			if(isExpanded_R[el.getId()])
            			{
            				myTreecopy[j] = new MyTreeNode(tc.getName(), 't', j, tc.getId());
            				j++;
            			}
            			
            		}
            		
            	}
            	if(-1 == value.toString().indexOf('@')) // in case an execution list is renamed
            	{
            		myTreecopy[row].setName(value.toString());
            		editedExecutionList.setName(value.toString());
            		int x = myTreecopy[row].getIndex();
            		EL2 = db.GetExecutionList(myTreecopy[row].getID());

        			editedExecutionList.setCreationDate(EL2.getCreationDate());
        			
        			
        			editedExecutionList.setId(myTreecopy[row].getID());

        			db.EditExecutionList(editedExecutionList);

            		value = myTreecopy[x];
            		

                   
                   setIcon(testCase);
                   setText( myTreecopy[row].getName());

            		
            	}
            	value = myTreecopy[row];
            	

        		Component ret = super.getTreeCellRendererComponent(tree, myTreecopy[row],
                        sel, expanded, leaf, row, hasFocus);

        		setText( nodeInfo.getName());
        		if (expanded)
                {
                	setLeafIcon(openFolder);
                }
                else {
                	
                	

                	setLeafIcon(closedFolder);
                }
                return ret;
            }
    		
        	
        }
        
        
         
        
        int j = 1;
       
        for (Folder f: FolderVector){
        	if (f.getParentID() == 0){
        		
        	
        	myTreecopy[j] = new MyTreeNode(f.getName(),'f', j,  f.getFolderID());
        	j++;
        	}

        	 for(TestCase tc : testCaseVector)
      		{
         		 if (tc.getFolderID() == f.getFolderID() ){

         			 
         			 if(isExpanded_R[f.getFolderID()]){
         				 ChildTCs[f.getFolderID()]++;
         			 myTreecopy[j]=new MyTreeNode(tc.getName(), 't', j, tc.getId());
         			 j++;
         			 
         			 }
         			 
      	    		
         		 }
         		 
         		 
      		}
        		for (Folder f1: FolderVector){
                	if (f.getFolderID() == f1.getParentID())
                	{
                		if(isExpanded_R[f.getFolderID()]){
                			myTreecopy[j] = new MyTreeNode(f1.getName(),'f', j,  f1.getFolderID());
							j++;
                	}
                }
                
        	}
        	
        	
        	ChildTCs[f.getFolderID()] = 0 ;
        	
        	 
        	
        	 
        }

        
       
        int i = j ;
        
        for(TestCase tc : testCaseVector)
		{
        	if (tc.getFolderID() == 0)
        	{
        		myTreecopy[i]=new MyTreeNode(tc.getName(), 't', i, tc.getId());
    			i++;
        	}
			
			
		}
        
        
        if (leaf && row > 0 && isTestCase(value, row)) {

        	if(-1 == value.toString().indexOf('@')) // if testcase is renamed
        	{

        		myTreecopy[row].setName(value.toString());
        		editedTestCase.setName(value.toString());
        		int x = myTreecopy[row].getIndex();
        		testCase2 = db.getTestCase(myTreecopy[row].getID());

    			editedTestCase.setStatus(testCase2.getStatus());
    			editedTestCase.setPriority(testCase2.getPriority());
    			editedTestCase.setDesigner(testCase2.getDesigner());
    			editedTestCase.setDescription(testCase2.getDescription());
    			editedTestCase.setDateTime(testCase2.getDateTime());
    			
        		editedTestCase.setId(myTreecopy[row].getID());
        		db.editTestCase(editedTestCase);
        		MainRun.window.refreshTree(); 
        		value = myTreecopy[x];
        		
               
               setIcon(testCase);
               setText( myTreecopy[row].getName());

        		
        	}
       	
        	value = myTreecopy[row];
 
            Component ret = super.getTreeCellRendererComponent(tree, value,
                    sel, expanded, leaf, row, hasFocus);
            setIcon(testCase);
            
            setText( myTreecopy[row].getName());
            return ret;

        } else { // leaf is not a testcase
        
        	if(row==0){ // root
        	setOpenIcon(openFolder);
        	setClosedIcon(closedFolder);
        
        	return this;
        	
        	}
        	else{ // either folder or execution list
        		DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)value;
                MyTreeNode nodeInfo =
                        (MyTreeNode)(node.getUserObject());
              
                if (nodeInfo.getType() == 'e')
                {
                	value = myTreecopy[row];
                	
            		Component ret = super.getTreeCellRendererComponent(tree, myTreecopy[row],
                            sel, expanded, leaf, row, hasFocus);
            		setText( nodeInfo.getName());
            		if (expanded)
                    {
                    	setLeafIcon(openFolder);
                    }
                    else {
                    	
                    	
                    	setLeafIcon(closedFolder);
                    }
                    return ret;
                }
        		

        		
        		if(-1 == value.toString().indexOf('@'))
            	{
            		
            		myTreecopy[row].setName(value.toString());
            		
					editedFolder.setName(value.toString());
            		int x = myTreecopy[row].getIndex();
            		folder = db.getFolder(myTreecopy[row].getID());

        			
            		editedFolder.setId(myTreecopy[row].getID());
            		db.editFolder(editedFolder);

            		value = myTreecopy[row];
            		
            		Component ret = super.getTreeCellRendererComponent(tree, value,
                            sel, expanded, leaf, row, hasFocus);
            		if (expanded)
                    {
                    	setLeafIcon(openFolder);
                    }
                    else {
                    	
                    	
                    	setLeafIcon(closedFolder);
                    }

            		MainRun.window.refreshTree(); 
                   return ret;
            	}
        		
        	
        		  
                }
        		value = myTreecopy[row];
            	
        		Component ret = super.getTreeCellRendererComponent(tree, myTreecopy[row],
                        sel, expanded, leaf, row, hasFocus);
        		setText( myTreecopy[row].getName());
        		if (expanded)
                {
                	setLeafIcon(openFolder);
                }
                else {
                	
                	
                	setLeafIcon(closedFolder);
                }
                return ret;
        	}

    }

    protected String getName(Object value){
    	 DefaultMutableTreeNode node =
                 (DefaultMutableTreeNode)value;
         MyTreeNode nodeInfo =
                 (MyTreeNode)(node.getUserObject());
         return nodeInfo.getName();
         
    }
    protected boolean isTestCase(Object value, int row) {
    	
    	if(row<=myTreecopy.length)
    	if (myTreecopy[row].getType() == 't')
    	{
    		return true;
    	}
    	return false;

    }
}