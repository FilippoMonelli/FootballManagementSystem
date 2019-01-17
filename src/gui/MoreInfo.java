package gui;

import java.awt.Component;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JTextPane;

public class MoreInfo extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextPane textPane;
	
	
	public MoreInfo() {
		super();
		setTitle("More info");
		setLocation(600,300);
		setSize(300,220);
		add(addText());
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}


	private Component addText() {
		textPane=new JTextPane();
		
		textPane.setText("You can navigate through the various menus with the menu bar.\n\n"+
						"Depending on your user profile, you can perform different operations for each menu.\n\n"+
						"You can export the data contained in the menus as an Excel file.\n\n"+
						"Each ticket you decide to buy will be saved in a PDF file.");
		
		textPane.setEditable(false);
		return textPane;
	}
	
	

}
