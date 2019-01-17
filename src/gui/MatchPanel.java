package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.DataManager;
import dialogGUI.AddMatch;
import dialogGUI.BuyTicketDialog;
import footballManagement.Match;
import footballManagement.Stadium;
import footballManagement.Team;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;
import utilsGUI.SpreadsheetManager;
import utilsGUI.UtilsPanel;

public class MatchPanel extends UtilsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel userRootPanel;
	
	private JTable matchTable; 

	private ArrayList<Match> matchList;
	
	private ArrayList<Match> matchListNotStarted;
	
	private ArrayList<Stadium> stadiumList;
	
	private ArrayList<Team> teamList;
	
	private DataManager data;
	
	private JButton matchesNotYetStarted;
	
	private JButton buyTickets;
	
	private DefaultTableModel model;
	
	private String[] columns = {"Match id","Home team","Away team","Date","Stadium"};
	
	private String[] row;
	
	private String[][] allMatches;
	
	private Person user;
	
	public MatchPanel(DataManager data,Person user) {
		super();
		matchList= new ArrayList<Match>();
		matchListNotStarted= new ArrayList<Match>();
		teamList = new ArrayList<Team>();
		stadiumList = new ArrayList<Stadium>();
		this.data=data;
		this.user=user;
		matchList=data.getMatchList();
	
		teamList=data.getTeamList();
		
		stadiumList=data.getStadiumList();
		
	
		userRootPanel = new JPanel();
		
		userRootPanel.setLayout(new BorderLayout());
		
		userRootPanel.add(addMatchTablePanel(matchList),BorderLayout.CENTER);
		
		userRootPanel.add(addNavigationPanel(false),BorderLayout.WEST);
		
		add(userRootPanel,BorderLayout.CENTER);
		
		super.addBtn.setToolTipText("Add a new match");
		super.deleteBtn.setToolTipText("Delete the match selected");
		super.updateBtn.setToolTipText("Update the match selected");
		super.reportBtn.setToolTipText("Create a spreadsheet of table");
		
		if(user instanceof Client) {
			super.deleteBtn.setEnabled(false);
			super.addBtn.setEnabled(false);
			super.updateBtn.setEnabled(false);
		}
		if(user instanceof Manager) {
			super.deleteBtn.setEnabled(false);
		}
		
		super.addBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddMatch(data, stadiumList, teamList,MatchPanel.this);
			}
			
		});
		
		super.deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 // i = the index of the selected row
                int i = matchTable.getSelectedRow();
                Match match = null;
                for(int j=0;j<matchList.size();++j) {
                	if(j==i)
                		match=matchList.get(j);
                }
                if(i >= 0){
                	try {
						data.removeMatch(match.getMatch_id());
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
	                		    "Error during the delete phase.",
	                		    "Delete error",
	                		    JOptionPane.WARNING_MESSAGE);
						e.printStackTrace();
					}
                    // remove a row from jtable
                    model.removeRow(i);
                }
                else{
                	JOptionPane.showMessageDialog(null,
                		    "Please, select a match.",
                		    "Delete error",
                		    JOptionPane.WARNING_MESSAGE);
                }
                refreshWindow();
			}
			
		});
		
		super.updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = matchTable.getSelectedRow();
                Match match = null;
                for(int j=0;j<matchList.size();++j) {
                	if(j==i)
                		match=matchList.get(j);
                }
                if(i >= 0){
                	new UpdateMatch(data, stadiumList, teamList,match,MatchPanel.this);
//                	 JOptionPane.showMessageDialog(null,
//                 		    "The player selected is deleted from database",
//                 		    "Delete action",
//                 		    JOptionPane.WARNING_MESSAGE);
               }else{
                	JOptionPane.showMessageDialog(null,
                		    "Please, select a match.",
                		    "Update error",
                		    JOptionPane.WARNING_MESSAGE);
               }
			}
		}
			
		);
		
		super.reportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SpreadsheetManager((String[]) columns,allMatches);
			}
			
		});
	}


	private Component addNavigationPanel(boolean flag) {
		
		JPanel navigationPanel = new JPanel();
		//JButton allMatchesBtn = new JButton("All matches");
		matchesNotYetStarted = new JButton("Matches not yet started");
		buyTickets = new JButton("Buy tickets");
		buyTickets.setEnabled(flag);
		
		
		JComboBox <String> comboStadium = new JComboBox<String>(); 
		
		comboStadium.addItem("All matches");
		for(Stadium s :data.getStadiumList()){
			comboStadium.addItem(s.getName());
		}
		
		navigationPanel.setLayout(new GridLayout(7,1));
		navigationPanel.add(new JPanel());
		navigationPanel.add(comboStadium);
		navigationPanel.add(new JPanel());
		navigationPanel.add(matchesNotYetStarted);
		navigationPanel.add(new JPanel());
		navigationPanel.add(buyTickets);
		navigationPanel.add(new JPanel());
		
		
		comboStadium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox)e.getSource();
		        String stadiumSelected = (String)cb.getSelectedItem();
		        

		        
		        if(stadiumSelected.equals("All matches")) {
		        	  refreshWindow();
		        }
		        else {
		        
		        	userRootPanel.removeAll();
		        	userRootPanel.add(addMatchTablePanel(data.matchesForStadium(stadiumSelected)),BorderLayout.CENTER);
		        	userRootPanel.add(addNavigationPanel(false),BorderLayout.WEST);
		        	userRootPanel.revalidate();
		        }
			}
			
		});
		
		matchesNotYetStarted.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					data.selectMatchesNotStarted();
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
                		    "Error during the selection of matches not yet started.",
                		    "Selection error",
                		    JOptionPane.WARNING_MESSAGE);
				}
				if(!(data.getMatchNotStartedList().isEmpty())) {
					matchListNotStarted=data.getMatchNotStartedList();
					buyTickets.setEnabled(true);
				}
				userRootPanel.removeAll();
	        	userRootPanel.add(addMatchTablePanel(data.getMatchNotStartedList()),BorderLayout.CENTER);
	        	userRootPanel.add(addNavigationPanel(true),BorderLayout.WEST);
	        	userRootPanel.revalidate();
			}
			
		});
		
		buyTickets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = matchTable.getSelectedRow();	
                if(i >= 0){
                	Match match=matchListNotStarted.get(i);
                	new BuyTicketDialog(match,user,data);
                }
                else{
                	JOptionPane.showMessageDialog(null,
                		    "Please select a match.",
                		    "Buy a tickets error",
                		    JOptionPane.WARNING_MESSAGE);
                }
			}
			
		});
		
		return navigationPanel;
	}

	public void refreshWindow() {
		data.refreshMatch();
		matchList=data.getMatchList();
		matchListNotStarted=data.getMatchNotStartedList();
		userRootPanel.removeAll();
	    userRootPanel.add(addMatchTablePanel(data.getMatchList()),BorderLayout.CENTER);
	    userRootPanel.add(addNavigationPanel(false),BorderLayout.WEST);
	    userRootPanel.revalidate();
	}
	
	private JScrollPane addMatchTablePanel(ArrayList<Match> matches) {
		matchTable= new JTable();
		
		model = new DefaultTableModel(){

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		model.setColumnIdentifiers(columns);

		matchTable.setModel(model);

		matchList=data.getMatchList();
		
		JScrollPane pane = new JScrollPane(matchTable);
		
		int numberOfColumns=5;
		
		row=new String[numberOfColumns];
		allMatches= new String[matchList.size()][numberOfColumns];
		
		int i =0;
		
		for(Match p: matches) {
			row[0]=Integer.toString(p.getMatch_id());
			row[1]=p.getHomeString_team();
			row[2]=p.getAwayString_team();
			row[3]=p.getDate();
			row[4]=p.getStadiumString();
			
			model.addRow(row);
			
			for(int j=0;j<numberOfColumns;++j) {
				allMatches[i][j]=row[j];
			}
			++i;
		}
				
		return pane;
	}

	
}
