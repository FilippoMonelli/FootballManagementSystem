package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.DataManager;
import dialogGUI.AddTeam;
import footballManagement.Team;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;
import utilsGUI.SpreadsheetManager;
import utilsGUI.UtilsPanel;

public class TeamPanel extends UtilsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable teamTable; 

	private ArrayList<Team> teamList;
	
	private DataManager dataManager;
	
	private DefaultTableModel model;
	
	private String[] columns = {"Id","Name","Country","Status","Value (in Million)","Stadium","Year","Management","Stability","Support"};
	
	private String[] row;
	
	private String[][] allTeams;
	
	private JPanel userRootPanel;
	
	private Person user;
	
	public TeamPanel(DataManager data,Person user) {
		super();
		teamList= new ArrayList<Team>();
		
		this.setUser(user);
		
		dataManager=data;
		
		teamList=dataManager.getTeamList();
	
		userRootPanel = new JPanel();
		
		userRootPanel.setLayout(new BorderLayout());
		
		userRootPanel.add(new JLabel("Team list:"), BorderLayout.NORTH);
		
		userRootPanel.add(addTeamTablePanel(),BorderLayout.CENTER);
		
		add(userRootPanel,BorderLayout.CENTER);
		
		super.addBtn.setToolTipText("Add a new team");
		super.deleteBtn.setToolTipText("Delete the team selected");
		super.updateBtn.setToolTipText("Update the team selected");
		super.reportBtn.setToolTipText("Create a spreadsheet of table");
//		super.refreshBtn.setToolTipText("Refresh data in table");
		
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddTeam(dataManager,TeamPanel.this);
			}
			
		});
		
		super.deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 // i = the index of the selected row
                int i = teamTable.getSelectedRow();
                Team team = null;
                for(int j=0;j<teamList.size();++j) {
                	if(j==i)
                		team=teamList.get(j);
                }
                if(i >= 0){
                	try {
						data.removeTeam(team.getTeam_id());
					} catch (SQLException e) {
						System.out.println("Errore nell0'eliminazione");
						e.printStackTrace();
					}
                    // remove a row from jtable
                    model.removeRow(i);
                    
                }
                else{
                	JOptionPane.showMessageDialog(null,
                		    "Please, select a team.",
                		    "Delete error",
                		    JOptionPane.WARNING_MESSAGE);
                }
                refreshWindow();
			}
			
		});
		
		super.updateBtn.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = teamTable.getSelectedRow();
                Team team = null;
                for(int j=0;j<teamList.size();++j) {
                	if(j==i)
                		team=teamList.get(j);
                }
                if(i >= 0){
                	new UpdateTeam(dataManager,team,TeamPanel.this);
                	
//                	 JOptionPane.showMessageDialog(null,
//                 		    "The player selected is deleted from database",
//                 		    "Delete action",
//                 		    JOptionPane.WARNING_MESSAGE);
               }else{
                	JOptionPane.showMessageDialog(null,
                		    "Please, select a team.",
                		    "Update error",
                		    JOptionPane.WARNING_MESSAGE);
               }
			}
			
		});
		
		
		super.reportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SpreadsheetManager((String[]) columns,allTeams);
			}
			
		});
		
		if(user instanceof Client) {
			super.deleteBtn.setEnabled(false);
			super.addBtn.setEnabled(false);
			super.updateBtn.setEnabled(false);
		}
		if(user instanceof Manager) {
			super.deleteBtn.setEnabled(false);
		}
		
		setVisible(true);
	}

	public void refreshWindow() {
		dataManager.refreshTeam();
		teamList=dataManager.getTeamList();
		userRootPanel.removeAll();
		userRootPanel.add(new JLabel("Team list:"), BorderLayout.NORTH);
		userRootPanel.add(addTeamTablePanel(),BorderLayout.CENTER);
	    userRootPanel.revalidate();
	}

	private JScrollPane addTeamTablePanel() {
		teamTable= new JTable();
		
		
		model = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		model.setColumnIdentifiers(columns);

		teamTable.setModel(model);

//		userTable.setBackground(Color.LIGHT_GRAY);
//		userTable.setForeground(Color.black);

		this.teamList=dataManager.getTeamList();
		
		JScrollPane pane = new JScrollPane(teamTable);
		
		int numberOfColumns=10;
		row=new String[numberOfColumns];
		allTeams= new String[teamList.size()][numberOfColumns];
		
		int i =0;
		
		for(Team p: teamList) {
			row[0]=Integer.toString(p.getTeam_id());
			row[1]=p.getName();
			row[2]=p.getCountryString();
			row[3]=p.getStatus();
			row[4]=Float.toString(p.getValue());
			row[5]=p.getStadiumString();
			row[6]=Integer.toString(p.getYear());
			row[7]=Integer.toString(p.getTeamAttribute().getManagement());
			row[8]=Integer.toString(p.getTeamAttribute().getStability());
			row[9]=Integer.toString(p.getTeamAttribute().getSupport());
			
			model.addRow(row);
			
			for(int j=0;j<numberOfColumns;++j) {
				allTeams[i][j]=row[j];
//				System.out.println(allUsers[i][j]+" i vale:" +i+ " j vale: "+j );
			}
			++i;
		}
				
		return pane;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}
}
