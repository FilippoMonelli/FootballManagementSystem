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
import dialogGUI.AddStadium;
import footballManagement.Stadium;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;
import utilsGUI.SpreadsheetManager;
import utilsGUI.UtilsPanel;

public class StadiumPanel extends UtilsPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable stadiumTable; 

	private ArrayList<Stadium> stadiumList;
	
	private DataManager data;
	
	private DefaultTableModel model;
	
	private String[] columns = {"Name","City","Seats","Year foundation"};
	
	private String[] row;
	
	private String[][] allStadium;
	
	private JPanel userRootPanel;
	
	private Stadium stadium;
	
	private Person user;
	
	public StadiumPanel(DataManager data,Person user) {
		super();
		stadiumList= new ArrayList<Stadium>();
		
		this.setData(data);
		
		this.setUser(user);
		
		stadiumList=data.getStadiumList();
	
		userRootPanel = new JPanel();
		
		userRootPanel.setLayout(new BorderLayout());
		
		userRootPanel.add(new JLabel("Stadium list:"), BorderLayout.NORTH);
		
		userRootPanel.add(addStadiumTablePanel(),BorderLayout.CENTER);
		
		add(userRootPanel,BorderLayout.CENTER);
		
		super.addBtn.setToolTipText("Add a new stadium");
		super.deleteBtn.setToolTipText("Delete the stadium selected");
		super.updateBtn.setToolTipText("Update the stadium selected");
		super.reportBtn.setToolTipText("Create a spreadsheet of table");
		
		if(user instanceof Client) {
			super.deleteBtn.setEnabled(false);
			super.addBtn.setEnabled(false);
			super.updateBtn.setEnabled(false);
		}
		if(user instanceof Manager) {
			super.deleteBtn.setEnabled(false);
		}
		
		super.addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddStadium(data,StadiumPanel.this);
			}
			
		});
		
		super.deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 // i = the index of the selected row
                int i = stadiumTable.getSelectedRow();
                stadium = null;
                for(int j=0;j<stadiumList.size();++j) {
                	if(j==i)
                		stadium=stadiumList.get(j);
                }
                if(i >= 0){
                    // remove a row from jtable
                	try {
						data.removeStadium(stadium.getStadiumId());
						model.removeRow(i);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
							    "Delete error in database.",
							    "Delete error.",
							    JOptionPane.WARNING_MESSAGE);
					}
                    
                }
                else{
                	JOptionPane.showMessageDialog(null,
						    "Please select a row.",
						    "Delete error.",
						    JOptionPane.WARNING_MESSAGE);
                }
                refreshWindow();
			}
			
		});
		
		super.updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i = stadiumTable.getSelectedRow();
				Stadium stadium = null;
                for(int j=0;j<stadiumList.size();++j) {
                	if(j==i)
                		stadium=stadiumList.get(j);
                }
                if(i >= 0){
                	new UpdateStadium(data,StadiumPanel.this,stadium);
                	
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
			public void actionPerformed(ActionEvent arg0) {
				new SpreadsheetManager(columns, allStadium);
			}
			
		});
//		System.out.println(stadiumList.toString());
		
	}

	public void refreshWindow() {
		data.refreshStadium();
		stadiumList=data.getStadiumList();
		userRootPanel.removeAll();
		userRootPanel.add(new JLabel("Stadium list:"), BorderLayout.NORTH);
	    userRootPanel.add(addStadiumTablePanel(),BorderLayout.CENTER);
	    userRootPanel.revalidate();
	}

	private JScrollPane addStadiumTablePanel() {
		stadiumTable= new JTable();
		
		this.stadiumList=data.getStadiumList();
		
		model = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		model.setColumnIdentifiers(columns);

		stadiumTable.setModel(model);

//		userTable.setBackground(Color.LIGHT_GRAY);
//		userTable.setForeground(Color.black);

		JScrollPane pane = new JScrollPane(stadiumTable);
		
		int numberOfColumns=4;
		
		row=new String[numberOfColumns];
		
		allStadium= new String[stadiumList.size()][numberOfColumns];
		
		int i =0;
		
		for(Stadium p: stadiumList) {
		
			row[0]=p.getName();
			row[1]=p.getCity();
			row[2]=Integer.toString(p.getSeats());
			row[3]=Integer.toString(p.getYear());
			model.addRow(row);
			
			for(int j=0;j<numberOfColumns;++j) {
				allStadium[i][j]=row[j];
//				System.out.println(allUsers[i][j]+" i vale:" +i+ " j vale: "+j );
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

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

}
