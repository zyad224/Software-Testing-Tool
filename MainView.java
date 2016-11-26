package main;

import javax.swing.JFrame;

import renderers.TreeRenderer;
import testCases.DetailedTestCase;
import testCases.Folder;
import testCases.TestCase;
import testCases.TestCaseView;
import uiComponents.MyTreeNode;
import uiComponents.MyPopupMenu;


import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Enumeration;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import execution.ExecutionList;
import middleware.DbConnection;

public class MainView {

	private JFrame frame;
	private static JTree overviewTree; // The original tree 
	private JTabbedPane tabbedPane; 
	public TestCaseView testCaseView; 
	private static DbConnection db = new DbConnection(); // connection to the database
	private static Vector<TestCase> testCaseVector = db.getTestCases();//testCaseView.getTestCasesVector();
	private static Vector<Folder> FolderVector = db.getFolders();
	private static Vector<ExecutionList> ElVector = db.getExecutionLists();
	//	private static Vector<Folder> FolderVector = db.getFolder();

	private boolean []isExpanded; // An array of whether folders are expanded or not, indices of the array represent the ID of the folders
	private int [] ExpandedID;

	private MyTreeNode []s ; // Array of Customized Object Nodes, indices of the array represent the indices of the tree
	private MyTreeNode []s1;
	private  DefaultMutableTreeNode [] Folder1 ; //Array of DefaultMutableTreeNodes to hold []s or other DefaultMutableTreeNodes, indices of the array represent the indices of the tree
	private MyTreeNode []sR ; // Same array as []s but for the Refresh Method
	private  DefaultMutableTreeNode [] Folder1R ; // Same array as []Folder1R but for the Refresh Method

	private DefaultMutableTreeNode rootNode_TC; // rootNode for TestCases
	private DefaultMutableTreeNode rootNode_EL; // rootNode for Execution List


	public MainView() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("New ");
		menuBar.add(mnNewMenu);

		JMenuItem mntmProject_1 = new JMenuItem("Project");
		mnNewMenu.add(mntmProject_1);

		JMenuItem mntmRelease_1 = new JMenuItem("Release");
		mnNewMenu.add(mntmRelease_1);

		JMenu mnNewMenu_1 = new JMenu("Open");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmProject = new JMenuItem("Project");
		mnNewMenu_1.add(mntmProject);

		JMenuItem mntmRelease = new JMenuItem("Release");
		mnNewMenu_1.add(mntmRelease);

		JMenu mnSave = new JMenu("Save");
		menuBar.add(mnSave);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		scrollPane.setMinimumSize(new Dimension(100,350));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Test Cases", null, scrollPane_1, null);

		JScrollPane scrollPane_EX = new JScrollPane();
		tabbedPane.addTab("Execution", null, scrollPane_EX, null);

		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Requirements", null, scrollPane_2, null);

		JScrollPane scrollPane_3 = new JScrollPane();
		tabbedPane.addTab("Defects", null, scrollPane_3, null);

		JScrollPane scrollPane_4 = new JScrollPane();
		tabbedPane.addTab("Report", null, scrollPane_4, null);



		//instead of mytreenode pass an object to the userobject of default mutable treenode containing the fields you need


		isExpanded = new boolean [testCaseVector.size()+ FolderVector.size()+2];  // the size of the array is as so because the indices are according to the ID of the folder
		ExpandedID = new int[testCaseVector.size()+FolderVector.size() +2];
		s = new MyTreeNode [testCaseVector.size()+FolderVector.size() +2]; 
		Folder1 = new DefaultMutableTreeNode [testCaseVector.size()+FolderVector.size() +2];




		rootNode_TC = new DefaultMutableTreeNode("Test Cases");

		int x = 1 ;  // index of a node in a tree starts from 1, because the root has index 0
		for(Folder f: FolderVector){ 

			s[x] = new MyTreeNode(f.getName(),'f', x, f.getFolderID());

			ExpandedID[x] = f.getFolderID();
			isExpanded[f.getFolderID()] = false;
			Folder1[x] = new DefaultMutableTreeNode(s[x]);

			if (f.getParentID() == 0) //if a folder has no parent, then it is placed under the root
			{
				rootNode_TC.add(Folder1[x]);
				x++; // increase the indices by 1
			}



			else {

				// to check if a folder has subfolders we go over all the saved folders and check if the current folder to be entered has the same parent ID as the saved folder ID
				for (int u = 1 ; u < s.length; u++ )
				{

					if(s[u]!=null)
					{

						if(s[u].getType() == 'f')
						{
							if(s[u].getID() == f.getParentID())
							{
								// add folder x under Parent folder u
								Folder1[u].add(Folder1[x]); 
								x++;
							}
						}
					}
				}
			}

			int l = x - 1; // looping inside testcaseVector to obtain testcases inside the folder

			for(TestCase tc : testCaseVector)
			{

				if (tc.getFolderID() == f.getFolderID())
				{

					s[x] =new MyTreeNode(tc.getName(), 't', x, tc.getId());

					Folder1[l].add(new DefaultMutableTreeNode(s[x]));
					x++;

				}

			}

		}

		int y = FolderVector.size()+1;  //after adding all folders under the root, add the testcases under the root.
		for(TestCase tc : testCaseVector)
		{
			if(tc.getFolderID() == 0){


				MyTreeNode s=new MyTreeNode(tc.getName(), 't', y, tc.getId());

				rootNode_TC.add(new DefaultMutableTreeNode(s));
				y++;
			}
		}


		overviewTree = new JTree(rootNode_TC); // Overview Tree for the TestCases Module

		// Expansion listener of folders
		TreeExpansionListener treeExpandListener = new TreeExpansionListener() {

			public void treeExpanded(TreeExpansionEvent event) {
				TreePath path = event.getPath();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				MyTreeNode nodeInfo = null ;
				nodeInfo = (MyTreeNode) node.getUserObject();
				isExpanded[nodeInfo.getID()] = true;
				overviewTree.setCellRenderer(new TreeRenderer(isExpanded, ExpandedID));



			}
			public void treeCollapsed(TreeExpansionEvent event) {
				TreePath path = event.getPath();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				MyTreeNode nodeInfo = null ;
				nodeInfo = (MyTreeNode) node.getUserObject();
				isExpanded[nodeInfo.getID()] = false;
				overviewTree.setCellRenderer(new TreeRenderer(isExpanded, ExpandedID));
			}

		};
		overviewTree.setEditable(true);
		overviewTree.setShowsRootHandles(true);
		overviewTree.setRootVisible(true);
		overviewTree.addTreeExpansionListener(treeExpandListener);



		overviewTree.setCellRenderer(new TreeRenderer(isExpanded, ExpandedID));  

		overviewTree.addTreeSelectionListener(new TreeSelectionListener() {  // tree selection listener
			public void valueChanged(TreeSelectionEvent e) {		    	
				overviewTree.addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) overviewTree.getLastSelectedPathComponent();
						MyTreeNode nodeInfo = null ;

						if (node == overviewTree.getModel().getRoot()){   // in case of Root is selected, the view will present all the testcases
							scrollPane_1.setViewportView(new TestCaseView(-1));
						}
						else
						{
							nodeInfo = (MyTreeNode) node.getUserObject();
							switch (nodeInfo.getType()){  // switch on the type of the node
							case 'f':
								scrollPane_1.setViewportView(new TestCaseView(nodeInfo.getID())); // in case of Folder is selected, test case view shows the details of all the testcases inside that folder
								break;
							case 't':  
								scrollPane_1.setViewportView(new DetailedTestCase(nodeInfo.getID()));
								break;

							}

						}
					}



				});
			}



		});



		// filter tree elements based on tab selection
		s1 = new MyTreeNode[ElVector.size()+ testCaseVector.size()+2];
		ChangeListener changeListener = new ChangeListener() {	 
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if(index == 1) // Execution tab
				{	


					rootNode_EL = new DefaultMutableTreeNode("Execution List");


					int x = 1 ;
					for ( ExecutionList el: ElVector)
					{
						testCaseVector= db.getTestCasesFromExecutionList(el.getId());

						s1[x] = new MyTreeNode (el.getName(),'e', x, el.getId() ) ;
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(s1[x]);
						rootNode_EL.add(node);
						x++;

						for (TestCase tc: testCaseVector)
						{

							s1[x] = new MyTreeNode(tc.getName(), 't', x, tc.getId());
							node.add(new DefaultMutableTreeNode(s1[x]));
							x++;



						}

					}
					DefaultTreeModel Tree1 = new DefaultTreeModel(rootNode_EL);
					overviewTree.setModel(Tree1);
				}
				if(index == 0) // TestCase tab
				{
					DefaultTreeModel Tree1 = new DefaultTreeModel(rootNode_TC);
					overviewTree.setModel(Tree1);

				}


			}
		};
		tabbedPane.addChangeListener(changeListener);




		// mouse listener for the customized pop menu
		overviewTree.addMouseListener ( new MouseAdapter ()
		{
			public void mousePressed ( MouseEvent e )
			{
				if ( SwingUtilities.isRightMouseButton ( e ) )
				{

					TreePath path = overviewTree.getPathForLocation ( e.getX (), e.getY () );
					overviewTree.setSelectionPath(path);
					Rectangle pathBounds = overviewTree.getUI ().getPathBounds ( overviewTree, path );
					if ( pathBounds != null && pathBounds.contains ( e.getX (), e.getY () ) )
					{
						overviewTree.setCellRenderer(new TreeRenderer(isExpanded, ExpandedID));
						JPopupMenu menu = new MyPopupMenu (overviewTree, isExpanded, ExpandedID);
						menu.show ( overviewTree, pathBounds.x+20, pathBounds.y + pathBounds.height );

					}
				}
			}
		} );
		scrollPane.setViewportView(overviewTree);



	}

	public JFrame getFrame(){
		return frame;
	}

	public JTree getOverviewTree(){
		return overviewTree;
	}

	public JScrollPane getOpenTab(){
		return (JScrollPane) tabbedPane.getSelectedComponent();
	}

	public JTabbedPane getTabbedPane(){
		return tabbedPane;
	}

	public Vector<TestCase> getVector(){
		return testCaseVector;
	}
	//Edited Hassan

	int o = 0;

	public boolean [] getIsExpanded(){
		return isExpanded;
	}
	public void refreshTree(){ // refresh tree method currently works only for the testcase module

		testCaseVector = db.getTestCases();
		FolderVector = db.getFolders();
		rootNode_TC = new DefaultMutableTreeNode("Test Cases");
		s = new MyTreeNode [testCaseVector.size()+FolderVector.size() +2];
		Folder1 = new DefaultMutableTreeNode [testCaseVector.size()+FolderVector.size() +2];



		int x = 1 ;  
		for(Folder f: FolderVector){
			s[x] = new MyTreeNode(f.getName(),'f', x, f.getFolderID());

			ExpandedID[x] = f.getFolderID();
			Folder1[x] = new DefaultMutableTreeNode(s[x]);

			if (f.getParentID() == 0)
			{
				rootNode_TC.add(Folder1[x]);
				x++;
			}



			else {

				for (int u = 1 ; u < s.length; u++ )
				{
					if(s[u]!=null)
					{
						if(s[u].getType() == 'f')
						{
							if(s[u].getID() == f.getParentID())
							{
								Folder1[u].add(Folder1[x]);
								x++;
							}
						}
					}
				}
			}

			int l = x-1;
			for(TestCase tc : testCaseVector)
			{

				if (tc.getFolderID() == f.getFolderID())
				{
					s[x] =new MyTreeNode(tc.getName(), 't', x, tc.getId());

					Folder1[l].add(new DefaultMutableTreeNode(s[x]));
					x++;

				}

			}

		}

		int y = FolderVector.size()+1;
		for(TestCase tc : testCaseVector)
		{
			if(tc.getFolderID() == 0){


				MyTreeNode s=new MyTreeNode(tc.getName(), 't', y, tc.getId());
				rootNode_TC.add(new DefaultMutableTreeNode(s));
				y++;
			}
		}
		DefaultTreeModel Tree1 = new DefaultTreeModel(rootNode_TC);
		overviewTree.setModel(Tree1);
		overviewTree.setCellRenderer(new TreeRenderer(isExpanded, ExpandedID));
		for(Folder f: FolderVector)
		{
			if(isExpanded[f.getFolderID()]){  // To expand the already expanded folders
				for (int u = 1 ; u < s.length; u++ )
				{
					if(s[u]!=null)
					{
						if(s[u].getType() == 'f')
						{
							if(s[u].getID() == f.getFolderID())
							{

								Enumeration<DefaultMutableTreeNode> e = rootNode_TC.depthFirstEnumeration();
								while (e.hasMoreElements()) {
									DefaultMutableTreeNode node = e.nextElement();
									if (node != overviewTree.getModel().getRoot() )
									{
										MyTreeNode nodeInfo = null ;
										nodeInfo = (MyTreeNode) node.getUserObject();
										if (nodeInfo.getName().equalsIgnoreCase(s[u].getName())) 
										{
											TreePath p = new TreePath(node.getPath());
											overviewTree.expandRow(overviewTree.getRowForPath(p));

										}


									}
								}
							}
						}
					}

				}
			}


		}


	}
}