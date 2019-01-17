package gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import data.DataManager;
import footballManagement.Country;
import footballManagement.Player;
import footballManagement.Team;

public class UpdateInfoPlayer extends JDialog {

private static final long serialVersionUID = 1L;
	
	protected JLabel labelName,labelSurname,labelCountry,labelTeam;
	protected JLabel labelPhysical,labelSpeed,labelMental,labelAttack,labelDefense,labelTechnique;

	protected JTextField textFieldName,textFieldSurname;
	
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

	private JButton updateBtn;
	private Player player;
	
	
	public UpdateInfoPlayer(ArrayList<Country> countryList, ArrayList<Team> teamList,DataManager data,Player p,PlayerPanel rootPanel) {
		
		super.setTitle("Update player");
		this.setPlayer(p);
		
		this.playerPanel=rootPanel;
		this.data=data;
		setTitle("New player");
		setLocation(100,50);
		setSize(650,400);
		setLayout(new BorderLayout());
		
		this.countryList=countryList;
		this.teamList=teamList;
		
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		
		
		String role = p.getFootball_role();
		
		System.out.println((role));
		
		if(role.equals("Goalkeeper"))
			radioBtnGoalkeeper.setSelected(true);
		if(role.equals("Defender"))
			radioBtnDefender.setSelected(true);
		if(role.equals("Midfielder"))
			radioBtnMidfielder.setSelected(true);
		if(role.equals("Forward"))
			radioBtnForward.setSelected(true);
		
		
		textFieldName.setText(p.getName());
		textFieldSurname.setText(p.getSurname());
		physicalComboBox.setSelectedItem(Integer.toString(p.getPlayerAttribute().getPhysical()));
		mentalComboBox.setSelectedItem(Integer.toString(p.getPlayerAttribute().getMental()));
		speedComboBox.setSelectedItem(Integer.toString(p.getPlayerAttribute().getSpeed()));
		attackComboBox.setSelectedItem(Integer.toString(p.getPlayerAttribute().getAttack()));
		defenseComboBox.setSelectedItem(Integer.toString(p.getPlayerAttribute().getDefense()));
		techniqueComboBox.setSelectedItem(Integer.toString(p.getPlayerAttribute().getTechnique()));
		countryComboBox.setSelectedItem(p.getCountryString());
		teamComboBox.setSelectedItem(p.getTeamString());
		
//		setResizable(false);
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
		updateBtn=new JButton("Update");
		JPanel panel = new JPanel();
		panel.add(updateBtn);
		panel.setVisible(true);
		add(panel, BorderLayout.SOUTH);
		
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*super methods*/
				String role=null;
				role=selectRole();
				Team t = selectTeam();
				Country c = selectCountry();
				
//				System.out.println(player.getId());
//				System.out.println(textFieldName.getText());
//				System.out.println(textFieldSurname.getText());
//				System.out.println(c.getCountry_id());
//				System.out.println(t.getTeam_id());
//				System.out.println(role);
//				System.out.println(physicalComboBox.getSelectedIndex());
//				System.out.println(speedComboBox.getSelectedIndex());
//				System.out.println(mentalComboBox.getSelectedIndex());
//				System.out.println(attackComboBox.getSelectedIndex());
//				System.out.println(defenseComboBox.getSelectedIndex());
//				System.out.println(techniqueComboBox.getSelectedIndex());
//				System.out.println(data.toString());
				
				try {
					data.updatePlayer(player.getId(),textFieldName.getText(), textFieldSurname.getText(),
							c.getCountry_id(), t.getTeam_id(), role , physicalComboBox.getSelectedIndex(),
							speedComboBox.getSelectedIndex(), mentalComboBox.getSelectedIndex(),
							attackComboBox.getSelectedIndex(), defenseComboBox.getSelectedIndex(),
							techniqueComboBox.getSelectedIndex());
//					System.out.println("Aggiornamento andato a buon fine");
					
//					System.out.println(data.toString());
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
				playerPanel.refreshWindow();
				setVisible(false);
				dispose();
			}
			
		});
		return panel;
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
	
	
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
	
}
