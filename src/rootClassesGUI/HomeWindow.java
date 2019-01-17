package rootClassesGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.DataManager;
import dialogGUI.AboutDialog;
import gui.InfoPanel;
import gui.MatchPanel;
import gui.PlayerPanel;
import gui.StadiumPanel;
import gui.TeamPanel;
import gui.TicketPanel;
import gui.UserPanel;
import gui.WelcomePanel;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;
import utils.DBManager;

public class HomeWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String playersIcon = "./images/Utility/players.png";
	private final String teamsIcon = "./images/Utility/team.png";
	private final String matchIcon = "./images/Utility/match.png";
	private final String userIcon = "./images/Utility/user.png";
	private final String ticketIcon = "./images/Utility/ticket.png";
	private final String infoIcon = "./images/Home/info.png";
	private final String logoutIcon = "./images/Home/logout.png";
	private final String helpIcon = "./images/Help/help.png";
	private final String stadiumIcon= "./images/Utility/stadium.png";
	
	private final String welcomeImage= "./images/welcome.jpg";
	
	private JPanel centrePanel;
	
	private Person user;
	private DBManager dbmanager;
	
	private DataManager data;
	
	private JPanel myPanel;
	private JMenuBar myMenuBar;
	

	public HomeWindow(Person user,DataManager data) {
		this.user=user;
		this.data=data;
		setTitle("Welcome "+user.getName());
		setSize(900,700);
		setLocation(100,10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(getMyMenuBar());
		myPanel=new JPanel();
		myPanel.setLayout(new BorderLayout());
		myPanel.add(new WelcomePanel(welcomeImage,new Dimension(900,700),data),BorderLayout.CENTER);
		
		add(myPanel);
		setVisible(true);
	}

	private JMenuBar getMyMenuBar() {
		myMenuBar =new JMenuBar();
		myMenuBar.add(setHome());
//	
		myMenuBar.add(setUtility());
		myMenuBar.add(setHelp());
		return myMenuBar;
	}
	

	
	private JMenu setUtility() {
		JMenu utility = new JMenu("Utility");
		
		JMenuItem teams= new JMenuItem("Teams");
		JMenuItem players= new JMenuItem("Players");
		JMenuItem users=new JMenuItem("Users");
		JMenuItem match=new JMenuItem("Matches");
		JMenuItem ticket=new JMenuItem("Tickets");
		JMenuItem stadium=new JMenuItem("Stadiums");
		
		
		players.setIcon(new ImageIcon(playersIcon));
		teams.setIcon(new ImageIcon(teamsIcon));
		users.setIcon(new ImageIcon(userIcon));
		match.setIcon(new ImageIcon(matchIcon));
		ticket.setIcon(new ImageIcon(ticketIcon));
		stadium.setIcon(new ImageIcon(stadiumIcon));
		
		utility.add(players);
		utility.add(teams);
		utility.add(users);
		utility.add(match);
		utility.add(ticket);
		utility.add(stadium);
		
		
		if(user instanceof Client || user instanceof Manager) {
			users.setEnabled(false);
			users.setEnabled(false);
		}
		
		teams.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new TeamPanel(data,user),BorderLayout.CENTER);
				myPanel.revalidate();
			}
			
		});
		/*METODO per scrivere messaggio quandio mi fermo su oggetto*/
//		players.setToolTipText("");
		
		players.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new PlayerPanel(data,user),BorderLayout.CENTER);
				myPanel.revalidate();
				
			}

			
		});
		
		users.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new UserPanel(data,user),BorderLayout.CENTER);
				myPanel.revalidate();
			}
	
		});
		
		match.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new MatchPanel(data,user),BorderLayout.CENTER);
				myPanel.revalidate();
			}
			
		});
		
		ticket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new TicketPanel(data,user),BorderLayout.CENTER);
				myPanel.revalidate();
			}
			
		});
		
		
		stadium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new StadiumPanel(data,user),BorderLayout.CENTER);
				myPanel.revalidate();
			}
			
		});
		
		return utility;
		
	}

	private JMenu setHelp() {
		JMenu help= new JMenu("Help");
		
//		JMenuItem welcome = new JMenuItem("Welcome");
		JMenuItem about = new JMenuItem("About FMS");
				
//		welcome.setIcon(new ImageIcon(welcomeIcon));
		about.setIcon(new ImageIcon(helpIcon));
		
//		help.add(welcome);
		help.add(about);
		
//		welcome.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				myPanel.removeAll();
//				myPanel.add(new WelcomePanel(welcomeImage,new Dimension(900,700),data,user,this.),BorderLayout.CENTER);
//				myPanel.revalidate();
//			}
//			
//		});
		
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutDialog();
			}
			
		});
		
		return help;
	}
	
	private JMenu setHome() {
		JMenu menu = new JMenu("Home");
		
		JMenuItem logout= new JMenuItem("Logout");
		JMenuItem info= new JMenuItem("Update my info");
		
		logout.setIcon(new ImageIcon(logoutIcon));
		info.setIcon(new ImageIcon(infoIcon));
		
		menu.add(info);
		menu.addSeparator();
		menu.add(logout);
		
		info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myPanel.removeAll();
				myPanel.add(new InfoPanel(user,data,true),BorderLayout.CENTER);
				myPanel.revalidate();
			}

			
		});
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(
					    null,
					    "Do you want to exit?",
					    "Confirm log out",
					    JOptionPane.YES_NO_OPTION);
				if(n==0){
					new LoginFrame();
					setVisible(false);
					dispose();
				}
				System.out.println("N VALE:"+n);
			}
			
		});
		return menu;
	}

	
	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public DBManager getDbmanager() {
		return dbmanager;
	}

	public void setDbmanager(DBManager dbmanager) {
		this.dbmanager = dbmanager;
	}

	public JPanel getCentrePanel() {
		return centrePanel;
	}

	public void setCentrePanel(JPanel centrePanel) {
		this.centrePanel = centrePanel;
	}

	
}
