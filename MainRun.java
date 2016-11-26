package main;

import java.awt.EventQueue;

public class MainRun {
	
	public static MainView window;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainView();
					window.getFrame().setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
