package utilsGUI;

import java.awt.BorderLayout;

import java.awt.Component;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


public class UtilsPanel extends JPanel {

	private static final long serialVersionUID = 1L;


	protected JButton addBtn,deleteBtn,updateBtn,reportBtn;

	private JPanel southPanel;
	
	private JPanel centrePanel;
	
	public UtilsPanel() {
		setLayout(new BorderLayout());
		add(addCentrePanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		setVisible(true);
	}

	
	protected Component addCentrePanel() {
		centrePanel=new JPanel();
		return centrePanel;
	}
	
	protected Component addSouthPanel() {
		southPanel= new JPanel();
		southPanel.setLayout(new GridLayout(1,9));
		addBtn=new JButton("Add");
		deleteBtn=new JButton("Delete");
		updateBtn=new JButton("Update");
//		refreshBtn=new JButton("Refresh");
		reportBtn=new JButton("Export");
		
		southPanel.add(new JPanel());
		southPanel.add(addBtn);
		southPanel.add(new JPanel());
		southPanel.add(deleteBtn);
		southPanel.add(new JPanel());
		southPanel.add(updateBtn);
		southPanel.add(new JPanel());
//		southPanel.add(refreshBtn);
//		southPanel.add(new JPanel());
		southPanel.add(reportBtn);
		southPanel.add(new JPanel());
		
		return southPanel;
	}
	
}
