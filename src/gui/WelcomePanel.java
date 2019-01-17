package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JPanel;

import data.DataManager;
import rootClassesGUI.HomeWindow;
import utilsGUI.BackgroundImagePanel;

public class WelcomePanel extends BackgroundImagePanel{

//	private final String playersIcon = "./images/Utility/players.png";
//	private final String teamsIcon = "./images/Utility/team.png";
//	private final String matchIcon = "./images/Utility/match.png";
//	private final String ticketIcon = "./images/Utility/ticket.png";
//	private final String stadiumIcon= "./images/Utility/stadium.png";
	
	private static final long serialVersionUID = 1L;

	private JButton moreInfoBtn; 
	
//	private JButton teamBtn,matchBtn,playerBtn,stadiumBtn,ticketBtn;
	
	private BackgroundImagePanel panel;
	
//	private DataManager data;
//	private Person user;
	private HomeWindow root;
	
	public WelcomePanel (String path, Dimension d,DataManager data){
		super(path, d);
//		this.data=data;
		setLayout(new BorderLayout());
		add(addWestPanel(),BorderLayout.WEST);
		add(addCenterPanel(),BorderLayout.CENTER);
	}

	private Component addCenterPanel() {
		JPanel root=new JPanel();
		root.setLayout(new GridLayout(1,3));
		root.setBackground(new Color(0,0,0,0));
		JPanel p1= new JPanel();
		p1.setBackground(new Color(0,0,0,0));
		JPanel p2= new JPanel();
		p2.setBackground(new Color(0,0,0,0));
		JPanel btnPanel= new JPanel();
		btnPanel.setBackground(new Color(0,0,0,0));
		/*btnPanel.setLayout(new GridLayout(10,1));
		
		teamBtn=new JButton("Go in team");
		JPanel teamPanel=new JPanel();
		teamPanel.setBackground(new Color(0,0,0,0));
		matchBtn=new JButton("Go in match");
		JPanel matchPanel=new JPanel();
		matchPanel.setBackground(new Color(0,0,0,0));
		playerBtn= new JButton("Go in player");
		JPanel playerPanel=new JPanel();
		playerPanel.setBackground(new Color(0,0,0,0));
		stadiumBtn=new JButton("Go in stadium");
		JPanel stadiumPanel=new JPanel();
		stadiumPanel.setBackground(new Color(0,0,0,0));
		ticketBtn=new JButton("Go in buy a ticket");
		JPanel ticketPanel=new JPanel();
		ticketPanel.setBackground(new Color(0,0,0,0));
		
		teamBtn.setIcon(new ImageIcon(teamsIcon));
		matchBtn.setIcon(new ImageIcon(matchIcon));
		playerBtn.setIcon(new ImageIcon(playersIcon));
		stadiumBtn.setIcon(new ImageIcon(stadiumIcon));
		ticketBtn.setIcon(new ImageIcon(ticketIcon));
		
		teamPanel.add(teamBtn);
		matchPanel.add(matchBtn);
		playerPanel.add(playerBtn);
		stadiumPanel.add(stadiumBtn);
		ticketPanel.add(ticketBtn);
		
		btnPanel.add(emptyPanel());
		btnPanel.add(emptyPanel());
		btnPanel.add(emptyPanel());
		btnPanel.add(teamPanel);
		btnPanel.add(matchPanel);
		btnPanel.add(playerPanel);
		btnPanel.add(stadiumPanel);
		btnPanel.add(ticketPanel);
		
		
		teamBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(panel,BorderLayout.CENTER);
				revalidate();
			}
			
		});
		
		matchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(new MatchPanel(data, user),BorderLayout.CENTER);
				revalidate();
			}
			
		});
		
		playerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(new PlayerPanel(data, user),BorderLayout.CENTER);
				revalidate();
			}
			
		});
		
		stadiumBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(new StadiumPanel(data, user),BorderLayout.CENTER);
				revalidate();
			}
			
		});
		
		ticketBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(new MatchPanel(data, user),BorderLayout.CENTER);
				revalidate();
			}
			
		});*/
		
		
		root.add(p1);
		root.add(p2);
		root.add(btnPanel);
		
		return root;
	}

	private Component addWestPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,0,0,0));
		panel.setLayout(new GridLayout(12,1));
		
		moreInfoBtn=new JButton("More info");
		
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(emptyPanel());
		panel.add(moreInfoBtn);
		panel.add(emptyPanel());
		
		moreInfoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MoreInfo();
			}
		
		});
		
		return panel;
		
	}

	
	private JPanel emptyPanel() {
		JPanel panel=new JPanel();
		
		panel.setBackground(new Color(0,0,0,0));
		
		return panel;
	}

	public BackgroundImagePanel getPanel() {
		return panel;
	}

	public void setPanel(BackgroundImagePanel panel) {
		this.panel = panel;
	}

	public HomeWindow getRoot() {
		return root;
	}

	public void setRoot(HomeWindow root) {
		this.root = root;
	}
}
