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
import dialogGUI.AddPlayer;
import exception.DeleteException;
import footballManagement.Country;
import footballManagement.Player;
import footballManagement.Team;
import userClasses.Client;
import userClasses.Person;
import utilsGUI.SpreadsheetManager;
import utilsGUI.UtilsPanel;


public class PlayerPanel extends UtilsPanel {
	private static final long serialVersionUID = 1L;

	private JTable userTable; 

	private ArrayList<Player> playerList;

	private ArrayList<Country> countryList;
	private ArrayList<Team> teamList;
	
	private DataManager data;
	
	private DefaultTableModel model;
	
	private JScrollPane pane;
	
	private String[] row;
	
	private String[][] allPlayers;
	
	private String[] columns = {"First Name","Last Name","Country","Team","Football role","Attack","Defense","Mental","Physical","Speed","Technique"};
	
	private JPanel userRootPanel;
	
	private Person user;
	
	public PlayerPanel(DataManager data,Person user) {
		super();
		playerList= new ArrayList<Player>();
		teamList = new  ArrayList<Team>();
		countryList= new ArrayList<Country>();
		this.setUser(user);
		this.setData(data);
	
		super.addBtn.setToolTipText("Add a new player");
		super.deleteBtn.setToolTipText("Delete the player selected");
		super.updateBtn.setToolTipText("Update the player selected");
		super.reportBtn.setToolTipText("Create a spreadsheet of table");

		if(user instanceof Client) {
			super.deleteBtn.setEnabled(false);
		}
		
		teamList=data.getTeamList();
		try {
			countryList=data.selectAllCountry();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerList=data.getPlayerList();
	
		userRootPanel = new JPanel();
		
		userRootPanel.setLayout(new BorderLayout());
		
		userRootPanel.add(new JLabel("Players list:"), BorderLayout.NORTH);
		
		userRootPanel.add(addPlayerTablePanel(),BorderLayout.CENTER);
		
		add(userRootPanel,BorderLayout.CENTER);
		
		
		super.addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddPlayer(countryList,teamList,data,PlayerPanel.this);
				
			}
					
		});
		
		
		super.deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 // i = the index of the selected row
                int i = userTable.getSelectedRow();
                Player player = null;
                for(int j=0;j<playerList.size();++j) {
                	if(j==i)
                		player=playerList.get(j);
                }
                if(i >= 0){
                    // remove a row from jtable
                	System.out.println("Player selected: "+player.toString());
                    try {
						data.removePlayer(player.getId());
					} catch (SQLException e) {
						System.out.println("Error in executeUpdate()");
					} catch (DeleteException e) {
						System.out.println("Error during DELETE");
					}
                    model.removeRow(i);
                }
                else{
                	JOptionPane.showMessageDialog(null,
                		    "Please, select a player.",
                		    "Delete error",
                		    JOptionPane.WARNING_MESSAGE);
                }
                refreshWindow();
			}
			
		});
		
		
		super.updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = userTable.getSelectedRow();
                Player player = null;
                for(int j=0;j<playerList.size();++j) {
                	if(j==i)
                		player=playerList.get(j);
                }
                if(i >= 0){
                	new UpdateInfoPlayer(countryList,teamList,data,player,PlayerPanel.this);
                	
//                	 JOptionPane.showMessageDialog(null,
//                 		    "The player selected is deleted from database",
//                 		    "Delete action",
//                 		    JOptionPane.WARNING_MESSAGE);
               }else{
                	JOptionPane.showMessageDialog(null,
                		    "Please, select a player.",
                		    "Update error",
                		    JOptionPane.WARNING_MESSAGE);
               }
			}
		
		});
		

		super.reportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SpreadsheetManager((String[])columns, allPlayers);
				
			}
			
		});
		
	}

	public void refreshWindow() {
		data.refreshPlayerList();
		playerList=data.getPlayerList();
		userRootPanel.removeAll();
		userRootPanel.setVisible(false);
		userRootPanel.add(new JLabel("Players list:"), BorderLayout.NORTH);
		userRootPanel.add(addPlayerTablePanel(),BorderLayout.CENTER);
		userRootPanel.setVisible(true);
	}

	private JScrollPane addPlayerTablePanel() {
		userTable= new JTable();
		
		
		playerList=data.getPlayerList();
		
		
		model = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		model.setColumnIdentifiers(columns);

		userTable.setModel(model);

//		userTable.setBackground(Color.LIGHT_GRAY);
//		userTable.setForeground(Color.black);

		pane = new JScrollPane(userTable);
		
		int numberOfColumns=11;
		
		row=new String[numberOfColumns];
		allPlayers=new String[playerList.size()][numberOfColumns];
		int i=0;
		
		for(Player p: playerList) {
			row[0]=p.getName();
			row[1]=p.getSurname();
			row[2]=p.getCountryString();
			row[3]=p.getTeamString();
			row[4]=p.getFootball_role();
			row[5]=Integer.toString(p.getPlayerAttribute().getAttack());
			row[6]=Integer.toString(p.getPlayerAttribute().getDefense());
			row[7]=Integer.toString(p.getPlayerAttribute().getMental());
			row[8]=Integer.toString(p.getPlayerAttribute().getPhysical());
			row[9]=Integer.toString(p.getPlayerAttribute().getSpeed());
			row[10]=Integer.toString(p.getPlayerAttribute().getTechnique());
		
			model.addRow(row);
			for(int j=0;j<numberOfColumns;++j) {
				allPlayers[i][j]=row[j];
			}
			++i;
		}
				
		return pane;
	}


	public DataManager getData() {
		return data;
	}


	public void setData(DataManager data) {
		this.data = data;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

}
