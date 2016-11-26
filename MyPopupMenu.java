package uiComponents;

import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import execution.ExecutionList;
import executor.TestRunDetails;
import main.MainRun;
import middleware.DbConnection;
import testCases.CreateTestCase;
import testCases.TestCase;
import testCases.TestCaseStep;
import testCases.Folder;

public class MyPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	private JMenuItem mntmExecute = new JMenuItem("Execute");
	private JMenuItem mntmRename = new JMenuItem("Rename");
	private JMenuItem mntmDelete = new JMenuItem("Delete");
	private JMenuItem mntmCreateFolder = new JMenuItem("Create Folder");
	private JMenuItem mntmCreateTestCase = new JMenuItem("Create Test Case");
	private JMenuItem mntmCreateExecutionList = new JMenuItem("Create Execution List");
	private JMenuItem mntmCopy = new JMenuItem("Copy");
	private JMenuItem mntmPaste = new JMenuItem("Paste");
	private JMenuItem mntmAttachFile = new JMenuItem("Attach File");
	private JSeparator separator = new JSeparator();
	private JSeparator separator1 = new JSeparator();
	private static DbConnection db = new DbConnection();
	private DefaultMutableTreeNode selectedNode;
	private MyTreeNode obj;
	private Object[] data;
	private int row,size,i;
	private static Vector<TestCase> testCasesVector = new Vector<TestCase>();
	private static Vector<TestCaseStep> testCaseStepVector = new Vector<TestCaseStep>();
	private static Vector<Folder> FolderVector = db.getFolders();
	private static Vector<ExecutionList> ExecutionListVector =  db.getExecutionLists();
	static MyTreeNode objCopy;
	static DefaultMutableTreeNode newNode;
	static int  CpyCounter=0;
	private Folder folder = new Folder(); 
	private int folderID = 0 ;
	
	private boolean root_flag = false;
	
	public MyPopupMenu(JTree tree, boolean[] isExpanded, int [] ExpandedID){
				
				selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(selectedNode == tree.getModel().getRoot()) // if selectedNode is a root
				{
					root_flag = true;
					if(MainRun.window.getTabbedPane().getSelectedIndex() == 0){
					add(mntmCreateFolder);
					add(mntmCreateTestCase);
					}
					else if(MainRun.window.getTabbedPane().getSelectedIndex() == 1)
						add(mntmCreateExecutionList);
				}
				else {
					obj = (MyTreeNode) selectedNode.getUserObject();
					root_flag = false;
				}
				

		mntmCreateExecutionList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExecutionList el = new ExecutionList();
				size = 0 ;
		        int last_id = 0;
		        ExecutionListVector = db.getExecutionLists();
		        for (ExecutionList el1 : ExecutionListVector) // get the last ID in the list and generate the new ID
				{
					
						last_id = el1.getId();
					
				}
		    	if (ExecutionListVector.size() == 0)
				{
					size = 1;
				}
				else{

					size = last_id + 1;
				}
		    	
		    	el.setName("New Folder");
				el.setId(size);
				db.createExecutionList(el);
			
				tree.setEditable(true);

				MainRun.window.getTabbedPane().setSelectedIndex(1);


				
				
			}
		});
		
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testCasesVector = db.getTestCases();
				if(obj.getType() == 'f'){
					db.deleteFolder(obj.getID(), testCasesVector);
				}
				else db.deleteTestCase(obj.getID());
				MainRun.window.refreshTree();
				MainRun.window.getOverviewTree().setSelectionRow(0);

			}
		});
		
		mntmAttachFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
			        }
				
				MainRun.window.refreshTree();
			}
		});
		
		mntmExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
				MyTreeNode parentObj = (MyTreeNode) parent.getUserObject();
				int elId = parentObj.getID();
				TestRunDetails TestRunDetails=new TestRunDetails(obj.getID(), elId);
				TestRunDetails.setVisible(true);
				
				
			}
		});
		
		mntmCreateTestCase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(root_flag == false && obj.getType() == 'f')
				{
					folderID = obj.getID();
				}
				MainRun.window.getTabbedPane().setSelectedIndex(0);
				MainRun.window.getOverviewTree().setSelectionRow(tree.getRowForPath(tree.getSelectionPath()));
				MainRun.window.getOpenTab().setViewportView(new CreateTestCase(folderID,root_flag));

				
			}		
		});
		
		mntmCreateFolder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tree.setEditable(true);
		        folder = new Folder(); 
		        size = 0 ;
		        int last_id = 0;
		        FolderVector = db.getFolders();
		        for (Folder f : FolderVector)
				{
					
						last_id = f.getFolderID();
						System.out.println("LastFOLDER: "+ last_id);
					
				}
		    	if (FolderVector.size() == 0)
				{
					size = 1;
				}
				else{

					size = last_id + 1;
				}
		    	if (!root_flag)
		    	{
		    		System.out.println("SUBFOLDER");
		    		folder.setParentId(obj.getID());
		    	}
		    	
				folder.setName("New Folder");
				folder.setId(size);
				db.createFolder(folder, root_flag);
				System.out.println("CREATE FOLDER");
				tree.setEditable(true);
				MainRun.window.refreshTree();
			}		
		});
		
		mntmCopy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 newNode = (DefaultMutableTreeNode)selectedNode.clone();
				 objCopy=(MyTreeNode) newNode.getUserObject();
			}
		});
		
		mntmRename.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 tree.setEditable(true);
				 System.out.println("Index Rename: " +obj.getIndex());
		            tree.startEditingAtPath(tree.getPathForRow(tree.getRowForPath(tree.getSelectionPath())));

		      
			}
		});
		
		mntmPaste.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TestCase testCase = new TestCase();
				testCase = db.getTestCase(objCopy.getID());
				testCasesVector = db.getTestCases();
				testCaseStepVector=db.getTestSteps(objCopy.getID());
				data = new Object [testCasesVector.size()];
				row = 0 ;


				for (TestCase tc : testCasesVector)
				{

					try {
						data[row] = tc.getId();
						
						row++;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				 size = 0 ;
				
				if (row == 0)
				{
					size = 1;
				}
				else
				{
					
					i = (Integer) data[row-1] ;
					size = i + 1;
				}
				
				
				testCase.setId(size);
				testCase.setName(testCase.getName()+ "  <copy>");
				Object[][]  stepTableData= new Object[testCaseStepVector.size()][4];
				int i=0;
				for(TestCaseStep tcs:testCaseStepVector)
			    {
			     		  stepTableData[i][0]=tcs.getName();
						  stepTableData[i][1]=tcs.getDescription();
						  stepTableData[i][2]=tcs.getExpectedResult();
						  stepTableData[i][3]=tcs.getAttachment();
						  i++;
			     	
			      }
					
				testCase.setSteps(testCaseStepVector.size(), 4, stepTableData);
				MainRun.window.refreshTree();

				
					
			}
		});
		
		// Add menu items to menu based on node type
		if(!root_flag){
			switch(obj.getType()){
			case 'f':
				add(mntmRename);
				add(mntmDelete);
				add(separator);
				add(mntmCopy);
				add(mntmPaste);
				add(separator1);
				add(mntmCreateTestCase);
				add(mntmCreateFolder);
				break;
			case 't':
				if(MainRun.window.getTabbedPane().getSelectedIndex() == 1)
				{
					add(mntmExecute);
					add(separator);
				}
				add(mntmRename);
				add(mntmDelete);
				add(separator1);
				add(mntmCopy);
				break;
			case 'e':
				add(mntmRename);
				break;
			}
			
		}
	}
		
	
	
	

}
