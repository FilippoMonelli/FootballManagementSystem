package dialogGUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

import data.DataManager;
import footballManagement.Stadium;
import footballManagement.Team;
import gui.MatchPanel;

public class AddMatch extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<Stadium> stadiums;
	protected ArrayList<Team> teams;
	
	protected JButton addBtn;
	
	protected JCalendar calendar;

	protected JComboBox<String> homeTeamComboBox,awayTeamComboBox;
	protected JComboBox<String> stadiumComboBox;
	
	protected JLabel homeTeam,awayTeam,stadiumLabel;
	
	protected DataManager data;
	
	protected JPanel centerPanel,southPanel;
	protected JButton registerBtn;

	private MatchPanel matchPanel;
	
	public AddMatch(DataManager data,ArrayList<Stadium> stadiums, ArrayList<Team> teams,MatchPanel rootPanel) {
		super();
		this.stadiums = stadiums;
		this.teams = teams;
		this.matchPanel=rootPanel;
		setTitle("Insert new match");
		setSize(420,250);
		setLayout(new BorderLayout());
		
		this.data=data;
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
		
		panel.add(calendar);
		
		return panel;
	}

	private JPanel addSouthPanel() {
		southPanel=new JPanel();
		registerBtn=new JButton("Add a match");
		
		registerBtn.addActionListener(new ActionListener() {

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
				
				if(!(homeTeamComboBox.getSelectedItem().toString().equals(awayTeamComboBox.getSelectedItem().toString()))) {
				
					try {
						data.insertMatch(home.getTeam_id(),
								away.getTeam_id(),stadium.getStadiumId(),
								update);
						data.refreshMatch();
					} catch (SQLException e) {
						System.out.println("Errore nell'inserimento del match");
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null,
						    "Please select two different teams.",
						    "Insert error.",
						    JOptionPane.WARNING_MESSAGE);
				}
				matchPanel.refreshWindow();
				setVisible(false);
				dispose();
			}
			
		});
		
		southPanel.add(registerBtn);
		return southPanel;
	}

	
	
	
	
}
