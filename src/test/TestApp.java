package test;
 
import java.awt.EventQueue;

import rootClassesGUI.LoginFrame;


/**
*
* @author Filippo Monelli
*/


public class TestApp {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
}
