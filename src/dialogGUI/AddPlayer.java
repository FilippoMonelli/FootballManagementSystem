package dialogGUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import data.DataManager;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;

import footballManagement.Country;
import footballManagement.Team;
import gui.PlayerPanel;

public class AddPlayer extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JLabel labelName,labelSurname,labelCountry,labelTeam;
	protected JLabel labelPhysical,labelSpeed,labelMental,labelAttack,labelDefense,labelTechnique;

	protected JTextField textFieldName,textFieldSurname;
	
	protected JButton actionBtn;
	
	private ArrayList<Country> countryList;
	private ArrayList<Team> teamList;
	
	String[] footballRole= {"Forward","Midfielder","Defender","Goalkeeper"};
	
	protected JPanel  centerPanel, southPanel;
	
	protected JPanel eastPanel,westPanel;
	
	protected JComboBox<String> countryComboBox;
	
	protected JComboBox<String> teamComboBox;
	
	private ButtonGroup rolesBtnGroup;
	
	protected JRadioButton radioBtnForward;
	protected JRadioButton radioBtnMidfielder;
	protected JRadioButton radioBtnDefender;
	protected JRadioButton radioBtnGoalkeeper;
	
	
	String[] attributeValue= {"0","1","2","3","4","5","6","7","8","9","10"};
	
	protected JComboBox<String> physicalComboBox;
	protected JComboBox<String> speedComboBox;
	protected JComboBox<String> mentalComboBox;
	protected JComboBox<String> attackComboBox;
	protected JComboBox<String> defenseComboBox;
	protected JComboBox<String> techniqueComboBox;
	
	private DataManager data;
	
	private PlayerPanel playerPanel;
	
	public AddPlayer(ArrayList<Country> countryList, ArrayList<Team> teamList,DataManager data,PlayerPanel rootPanel) {
		super();
		
//		Window[] frame= JFrame.getWindows();
//		/*HOW TO CLOSE ALL WINDOW*/
//		for(Window f:frame) {
//			f.setVisible(false);
//			f.dispose();
//		}
		
		this.data=data;
		this.playerPanel=rootPanel;
		setTitle("New player");
		setLocation(100,50);
		setSize(650,400);
		setLayout(new BorderLayout());
		
		this.countryList=countryList;
		this.teamList=teamList;
		
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		
		setResizable(false);
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setVisible(true);
		
	}

	

	public JPanel addCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		centerPanel.add(addEastPanel(),BorderLayout.EAST);
		centerPanel.add(addWestPanel(),BorderLayout.WEST);
		return centerPanel;
	}
	
	private JPanel addWestPanel() {
		westPanel= new JPanel();
		
		westPanel.setLayout(new GridLayout(10,1));
/*start creation panel*/		
		westPanel.add(new JPanel());
		
		labelName=new JLabel("Name");
		labelSurname=new JLabel("Surname");
		labelCountry=new JLabel("Country");
		labelTeam = new JLabel("Team");
		textFieldName=new JTextField();
		textFieldSurname=new JTextField();
		
		countryComboBox=new JComboBox<String>();
		teamComboBox=new JComboBox<String>();
		

		for(Country c:countryList) {
			countryComboBox.addItem(c.getCountry_name());
		}
		
		for(Team t: teamList) {
			teamComboBox.addItem(t.getName());
		}
		
		westPanel.add(labelName);
		westPanel.add(textFieldName);
		westPanel.add(labelSurname);
		westPanel.add(textFieldSurname);
		westPanel.add(labelCountry);
		westPanel.add(countryComboBox);
		westPanel.add(labelTeam);
		westPanel.add(teamComboBox);
		
		westPanel.add(new JPanel());
/*end creation panel*/		
		return westPanel;
	}



	private JPanel addEastPanel() {
		eastPanel= new JPanel();
		eastPanel.setLayout(new BorderLayout());
		
		eastPanel.add(addRadioBtnPanel(),BorderLayout.NORTH);
		eastPanel.add(addAttributePanel(),BorderLayout.CENTER);
		
		
		return eastPanel;
	}



	private JPanel addAttributePanel() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(6,2));
		
		labelPhysical = new JLabel ("Physical");
		labelSpeed=new JLabel("Speed");
		labelMental=new JLabel("Mental");
		labelAttack=new JLabel("Attack");
		labelDefense=new JLabel("Defense");
		labelTechnique=new JLabel("Technique");
		speedComboBox=new JComboBox<String>(attributeValue);
		mentalComboBox = new JComboBox<String>(attributeValue);
		physicalComboBox=new JComboBox<String>(attributeValue);
		attackComboBox = new JComboBox<String>(attributeValue);
		defenseComboBox=new JComboBox<String>(attributeValue);
		techniqueComboBox = new JComboBox<String>(attributeValue);
		
		panel.add(labelPhysical);
		panel.add(physicalComboBox);
		panel.add(labelSpeed);
		panel.add(speedComboBox);
		panel.add(labelMental);
		panel.add(mentalComboBox);
		panel.add(labelAttack);
		panel.add(attackComboBox);
		panel.add(labelDefense);
		panel.add(defenseComboBox);
		panel.add(labelTechnique);
		panel.add(techniqueComboBox);
		
		return panel;
	}



	private JPanel addRadioBtnPanel() {
		JPanel panel = new JPanel();
		rolesBtnGroup= new ButtonGroup();
		radioBtnForward = new JRadioButton("Forward");
		radioBtnMidfielder = new JRadioButton("Midfielder");
		radioBtnDefender = new JRadioButton("Defender");
		radioBtnGoalkeeper = new JRadioButton("Goalkeeper");
		
		rolesBtnGroup.add(radioBtnGoalkeeper);
		rolesBtnGroup.add(radioBtnDefender);
		rolesBtnGroup.add(radioBtnMidfielder);
		rolesBtnGroup.add(radioBtnForward);
		
		radioBtnGoalkeeper.setSelected(true);
		
		panel.add(radioBtnGoalkeeper);
		panel.add(radioBtnDefender);
		panel.add(radioBtnMidfielder);
		panel.add(radioBtnForward);
		return panel;
	}



	public JPanel addSouthPanel() {
		southPanel=new JPanel();
		actionBtn=new JButton("Register");
		
		actionBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String role = null;
				
				role=selectRole();
				
				
				Country c=selectCountry();
				Team t = selectTeam();
				
				
				
				
//				System.out.println(textFieldName.getText() + textFieldSurname.getText() +
//							c.getCountry_id()+ t.getTeam_id()+ role + physicalComboBox.getSelectedIndex() + 
//							speedComboBox.getSelectedIndex()+ mentalComboBox.getSelectedIndex()+
//							attackComboBox.getSelectedIndex()+ defenseComboBox.getSelectedIndex()+
//							techniqueComboBox.getSelectedIndex());
				
				try {
					data.insertPlayer(textFieldName.getText(), textFieldSurname.getText(),
							c.getCountry_id(), t.getTeam_id(), role , physicalComboBox.getSelectedIndex(),
							speedComboBox.getSelectedIndex(), mentalComboBox.getSelectedIndex(),
							attackComboBox.getSelectedIndex(), defenseComboBox.getSelectedIndex(),
							techniqueComboBox.getSelectedIndex());
					System.out.println("Scrittura player andata a buon fine");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				playerPanel.refreshWindow();
				setVisible(false);
				dispose();
			}
		});
		
		southPanel.add(actionBtn);
		
		return southPanel;
	}
	
	protected String selectRole() {
		String role = null;
		if(radioBtnForward.isSelected()) 
			role=radioBtnForward.getActionCommand();
		if(radioBtnMidfielder.isSelected())
			role=radioBtnMidfielder.getActionCommand();
		if(radioBtnDefender.isSelected())
			role=radioBtnDefender.getActionCommand();
		if(radioBtnGoalkeeper.isSelected())
			role=radioBtnGoalkeeper.getActionCommand();
		return role;
	}
	
	protected Country selectCountry() {
		Country c=null;
		for (Country b:countryList ) {
			if(b.getCountry_name()==countryComboBox.getSelectedItem()) {
				c=b;
			}
		}
		return c;
	}
	
	protected Team selectTeam() {
		Team t=null;
		
		for(Team b:teamList) {
			if(b.getName()==teamComboBox.getSelectedItem()) {
				t=b;
			}
		}
		
		return t;
	}
	
	
}
