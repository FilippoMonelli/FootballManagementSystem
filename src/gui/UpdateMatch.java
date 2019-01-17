package gui;


import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

import data.DataManager;
import footballManagement.Match;
import footballManagement.Stadium;
import footballManagement.Team;

public class UpdateMatch extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Match match;
	
	protected ArrayList<Stadium> stadiums;
	protected ArrayList<Team> teams;
	
	protected JButton updateBtn;
	
	protected JCalendar calendar;

	protected JComboBox<String> homeTeamComboBox,awayTeamComboBox;
	protected JComboBox<String> stadiumComboBox;
	
	protected JLabel homeTeam,awayTeam,stadiumLabel;
	
	protected DataManager data;
	
	protected JPanel centerPanel,southPanel;
	
	private MatchPanel matchPanel;
	
	public UpdateMatch(DataManager data, ArrayList<Stadium> stadiums, ArrayList<Team> teams, Match match,MatchPanel rootPanel) {
		
		this.stadiums = stadiums;
		this.teams = teams;
		this.match=match;
		this.data=data;
		this.matchPanel=rootPanel;
		setTitle("Update the match");
		setSize(420,250);
		setLayout(new BorderLayout());
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		/*IMPORTANT*/
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}

	
	
	private JPanel addCenterPanel() {
		centerPanel=new JPanel();
		centerPanel.setLayout(new BorderLayout());
		
		centerPanel.add(addWestPanel(),BorderLayout.WEST);
		centerPanel.add(addEastPanel(), BorderLayout.EAST);
		
		return centerPanel;
	}

	private JPanel addWestPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8,1));
		
		homeTeam=new JLabel("Home team");
		awayTeam=new JLabel("Away team");
		stadiumLabel=new JLabel("Stadium");
		
		homeTeamComboBox=new JComboBox<String>();
		awayTeamComboBox=new JComboBox<String>();
		stadiumComboBox=new JComboBox<String>();
		
		for(Team t: teams) {
			homeTeamComboBox.addItem(t.getName());
			awayTeamComboBox.addItem(t.getName());
		}
		
		for(Stadium s:stadiums) {
			stadiumComboBox.addItem(s.getName());
		}
		
		homeTeamComboBox.setSelectedItem(match.getHomeString_team());
		awayTeamComboBox.setSelectedItem(match.getAwayString_team());
		stadiumComboBox.setSelectedItem(match.getStadiumString());
		
		System.out.println("Home team: "+match.getHomeString_team()+
					" away team: "+match.getAwayString_team()+
					" stadium: "+match.getStadiumString());
		
		panel.add(new JPanel());
		panel.add(homeTeam);
		panel.add(homeTeamComboBox);
		panel.add(awayTeam);
		panel.add(awayTeamComboBox);
		panel.add(stadiumLabel);
		panel.add(stadiumComboBox);
		panel.add(new JPanel());
		
		
		
		return panel;
	}

	private JPanel addEastPanel() {
		JPanel panel = new JPanel();
		
		calendar = new JCalendar();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date inputDate = null;
	       try {
	    	System.out.println("Match date"+match.getDate());
			inputDate = dateFormat.parse(match.getDate().replaceAll("-", "/"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	        
		calendar.setDate(inputDate);
		
		panel.add(calendar);
		
		return panel;
	}

	private JPanel addSouthPanel() {
		southPanel=new JPanel();
		updateBtn=new JButton("Update");
		
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int year,month,day;
				day=calendar.getDayChooser().getDay();
				month=calendar.getMonthChooser().getMonth()+1;
				year=calendar.getYearChooser().getYear();
				String update=String.format("%d/%d/%d",year,month,day);
				Team home = null,away = null;
				Stadium stadium = null;
				
				for (Team t:teams) {
					if(t.getName().equals(homeTeamComboBox.getSelectedItem().toString())) {
						home=t;
					}
					if(t.getName().equals(awayTeamComboBox.getSelectedItem().toString())) {
						away=t;
					}
				}
				
				for (Stadium t:stadiums) {
					if(t.getName().equals(stadiumComboBox.getSelectedItem().toString())) {
						stadium=t;
					}
				}	
				
				try {
					data.updateMatch(match.getMatch_id(),home.getTeam_id(),
							away.getTeam_id(),stadium.getStadiumId(),
							update);
				} catch (SQLException e) {
					System.out.println("Errore nell'update del match");
					e.printStackTrace();
				}
				matchPanel.refreshWindow();
				setVisible(false);
				dispose();
			}
			
		});
		
		
		southPanel.add(updateBtn);
		return southPanel;
	}

}
