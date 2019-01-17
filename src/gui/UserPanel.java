package gui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.DataManager;
import dialogGUI.AdminRegistration;
import dialogGUI.ClientRegistration;
import dialogGUI.ManagerRegistration;
import exception.DeleteException;
import rootClassesGUI.LoginFrame;
import userClasses.*;
import utilsGUI.SpreadsheetManager;
import utilsGUI.UtilsPanel;

public class UserPanel extends UtilsPanel {
	
	private static final long serialVersionUID = 1L;

	private JTable userTable; 

	private ArrayList<Manager> managerList;
	
	private ArrayList<Client> clientList;
	
	private ArrayList<Admin> adminList;
	
	private DataManager data;
	
	private DefaultTableModel model;
	
	private String[] columns = {"Id","Profile","First Name","Last Name","Username","Password","Birthday","Image"};
	
	private String[] row;
	
	private String[][] allUsers;
	
	private JPanel userRootPanel;
	
	/*owner of session*/
	private Person person;
	
	public UserPanel(DataManager data,Person person) {
		super();
		managerList= new ArrayList<Manager>();
		clientList= new ArrayList<Client>();
		adminList=new ArrayList<Admin>();
		this.setData(data);
		this.setPerson(person);
		
		adminList=data.getAdminList();
		
		clientList=data.getClientList();
		
		managerList=data.getManagerList();
		
		super.addBtn.setToolTipText("Add a new user");
		super.deleteBtn.setToolTipText("Delete the user selected");
		super.updateBtn.setToolTipText("Update the user selected");
		super.reportBtn.setToolTipText("Create a spreadsheet of table");
//		super.refreshBtn.setToolTipText("Refresh data in table");
		
		userRootPanel = new JPanel();
		
		userRootPanel.setLayout(new BorderLayout());
		
		userRootPanel.add(new JLabel("Users list:"), BorderLayout.NORTH);
		
		userRootPanel.add(addUserTablePanel(),BorderLayout.CENTER);
		
		add(userRootPanel,BorderLayout.CENTER);
		
		
		super.addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Admin",
	                    "Manager",
	                    "Client"};
				int n = JOptionPane.showOptionDialog(null,
						"Which type of user would you like to add?",
								"User creation option",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[2]);
				System.out.println(n);
				
				switch (n) {
				case 0:
					new AdminRegistration(data,false,UserPanel.this);
					break;
				case 1:
					new ManagerRegistration(data,false,UserPanel.this);
					break;
				case 2:
					new ClientRegistration(data,false,UserPanel.this);
					break;
				}
			}
			
		});
		
		
		super.deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 // i = the index of the selected row
                int i = userTable.getSelectedRow();
                Person p = null;
                int allSize = (adminList.size()+managerList.size()+clientList.size());
               
                for(int j=0;j<allSize;++j) {
                	if(j==i) {
                		if(j<adminList.size()) {
                			/*row selected is a Admin*/
                			p=adminList.get(j);
                		}else if(j>=adminList.size()&&j<adminList.size()+managerList.size()) {
                			/*row selected is a Manager*/
                			p=managerList.get(j-adminList.size());
                		}else if(j>=adminList.size()+managerList.size()&&j<allSize) {
                			/*row selected is a Client*/
                			p=clientList.get(j-(adminList.size()+managerList.size()));
                		}
                	}
                }
                if(i >= 0){
                	if(!person.equals(p)) {
                		int n = JOptionPane.showConfirmDialog(
                			null,
                			"Are you sure to delete\n"+
                					p.getUsername()+"?",
                					"Delete user",
                					JOptionPane.YES_NO_OPTION);
                		if(n==0) {
                			deleteAction(p);
                			// remove a row from jtable
                			model.removeRow(i);
                			refreshWindow();
                		}
                	}else {
                		int n = JOptionPane.showConfirmDialog(
                    			null,
                    			"Are you sure to delete yourself\n"+
                    					"from database?",
                    					"Delete user",
                    					JOptionPane.YES_NO_OPTION);
                		if(n==0) {
                			deleteAction(p);
                			JOptionPane.showMessageDialog(null,
                         		    "You delete yourself.\nYou'll be disconnected",
                         		    "Delete information",
                         		    JOptionPane.INFORMATION_MESSAGE);
                			closeWindows();
                			new LoginFrame();
                			
                		}
                		
                	}
                }
                else {
                     	JOptionPane.showMessageDialog(null,
                     		    "Please, select a user.",
                     		    "Delete error",
                     		    JOptionPane.WARNING_MESSAGE);
                  
                }
			}
			
		});
		
		
		super.updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 int i = userTable.getSelectedRow();
				 
	                Person p = null;
	                int allSize = (adminList.size()+managerList.size()+clientList.size());
	               if(i>=0) {
	                for(int j=0;j<allSize;++j) {
	                	if(j==i) {
	                		if(j<adminList.size()) {
	                			/*row selected is a Admin*/
	                			System.out.println("J vale (admin)"+j);
	                			p=adminList.get(j);
	                		}else if(j-adminList.size()<managerList.size()) {
	                			/*row selected is a Manager*/
	                			System.out.println("J vale (manager) "+(j-adminList.size()));
	                			p=managerList.get(j-adminList.size());
	                		}else if(j-adminList.size()-managerList.size()<clientList.size()) {
	                			/*row selected is a Client*/
	                			System.out.println("j vale (clienti)"+(j-(adminList.size()+managerList.size())));
	                			p=clientList.get(j-(adminList.size()+managerList.size()));
	                		}
	                	}
	                }
	                System.out.println("utente selezionato"+p.toString());
	                	
	                		// update a row from jtable
	                		
	                		new UpdateInfoUser(p,data,UserPanel.this);
	                		
	                	System.out.println(p.toString());
	                }
	                else{
	                     	JOptionPane.showMessageDialog(null,
	                     		    "Please, select a user.",
	                     		    "Update error",
	                     		    JOptionPane.WARNING_MESSAGE);
	                }
			}
			
		});
		
	
		super.reportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SpreadsheetManager((String[]) columns,allUsers);
			}
			
		});
		
	}

	public void closeWindows() {
		/*HOW TO CLOSE ALL WINDOW*/
		Window[] frame= JFrame.getWindows();        					
		for(Window f:frame) {
			f.setVisible(false);
			f.dispose();
		}
	}
	
	public void deleteAction(Person p) {
		if(p instanceof Client) {
			try {
				data.removeClient(p.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DeleteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(p instanceof Manager) {
			try {
				data.removeManager(p.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DeleteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(p instanceof Admin){
			try {
				data.removeAdmin(p.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DeleteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void refreshWindow() {
		data.refreshAdminList();
		data.refreshClientList();
		data.refreshManagerList();
		adminList=data.getAdminList();
		managerList=data.getManagerList();
		clientList=data.getClientList();
		userRootPanel.removeAll();
		userRootPanel.add(new JLabel("Players list:"), BorderLayout.NORTH);
		userRootPanel.add(addUserTablePanel(),BorderLayout.CENTER);
		userRootPanel.revalidate();
	}

	private JScrollPane addUserTablePanel() {
		userTable= new JTable();
		
		adminList=data.getAdminList();
		managerList=data.getManagerList();
		clientList=data.getClientList();
		
		model = new DefaultTableModel(){

			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		model.setColumnIdentifiers(columns);

		userTable.setModel(model);

		JScrollPane pane = new JScrollPane(userTable);
		
		int numberOfColumns=8;
		int sizeOfAllUsers=adminList.size()+managerList.size()+clientList.size()+1;
		row=new String[numberOfColumns];
		allUsers=new String[sizeOfAllUsers][numberOfColumns];
		int i=0;
		

		
		for(Person p: adminList) {
			row[0]=Integer.toString(p.getId());
			row[1]="Admin";
			row[2]=p.getName();
			row[3]=p.getSurname();
			row[4]=p.getUsername();
			row[5]=p.getPassword();
			row[6]=p.getBirthday();
			row[7]=p.getImage();	
			model.addRow(row);
			
			for(int j=0;j<numberOfColumns;++j) {
				allUsers[i][j]=row[j];
			}
			++i;
			
		}
		
		for(Person p: managerList) {
			row[0]=Integer.toString(p.getId());
			row[1]="Manager";
			row[2]=p.getName();
			row[3]=p.getSurname();
			row[4]=p.getUsername();
			row[5]=p.getPassword();
			row[6]=p.getBirthday();
			row[7]=p.getImage();
			model.addRow(row);

			for(int j=0;j<numberOfColumns;++j) {
				allUsers[i][j]=row[j];
			}
			++i;
		}
		
		for(Person p: clientList) {
			row[0]=Integer.toString(p.getId());
			row[1]="Client";
			row[2]=p.getName();
			row[3]=p.getSurname();
			row[4]=p.getUsername();
			row[5]=p.getPassword();
			row[6]=p.getBirthday();
			row[7]=p.getImage();
			model.addRow(row);
			
//			System.out.println();
//			System.out.println();
//			System.out.println("Utente selezionato: "+p.toString());
			for(int j=0;j<numberOfColumns;++j) {
				allUsers[i][j]=row[j];
//				System.out.println(allUsers[i][j]+" i vale:" +i+ " j vale: "+j );
			}
			++i;
		}
//		
//		for(String[] s:allUsers) {
//			for(String p:s) {
//				System.out.println(" "+p);
//			}
//		}
		
		
		return pane;
	}


	public DataManager getData() {
		return data;
	}


	public void setData(DataManager data) {
		this.data = data;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}


	