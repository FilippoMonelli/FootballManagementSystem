package gui;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import data.DataManager;
import userClasses.Admin;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;

public class UpdateInfoUser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private int n;
	
	private boolean myInfoStatus;
	
	private UserPanel userPanel;
	
	public UpdateInfoUser(Person person,DataManager data,UserPanel rootPanel) {
		super();
		setTitle("Admin update info of user");
		setSize(750,500);
		infoPanel=new InfoPanel(person,data,false);
		this.userPanel=rootPanel;
		infoPanel.updateBtn.addActionListener(new ActionListener() {

			private int year;
			private int day;
			private int month;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!myInfoStatus) {
					n = JOptionPane.showConfirmDialog(
							null,
							"Do you want update info?\n",
							"Update user's info",
							JOptionPane.YES_NO_OPTION);
				}
				if(n==0) {
					String s = person.getBirthday();
					String[] tokens = s.split("-");
					for (String t : tokens)
					  System.out.println(t);
					setYear(Integer.parseInt(tokens[0]));
					setMonth(Integer.parseInt(tokens[1]));
					setDay(Integer.parseInt(tokens[2]));
					
					if(person instanceof Client) {
						try {
							data.updateClient(person.getId(), infoPanel.textFieldName.getText(), infoPanel.textFieldSurname.getText(),
									infoPanel.textFieldUsername.getText(), infoPanel.textFieldPassword.getText(),
									infoPanel.birthday.getDayChooser().getDay(), infoPanel.birthday.getMonthChooser().getMonth()+1, 
									infoPanel.birthday.getYearChooser().getYear(),
									infoPanel.petStrings[infoPanel.profileImage.getSelectedIndex()]);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if(person instanceof Manager) {
						try {
							data.updateManager(person.getId(), infoPanel.textFieldName.getText(), infoPanel.textFieldSurname.getText(),
									infoPanel.textFieldUsername.getText(), infoPanel.textFieldPassword.getText(),
									infoPanel.birthday.getDayChooser().getDay(), infoPanel.birthday.getMonthChooser().getMonth()+1, 
									infoPanel.birthday.getYearChooser().getYear(),
									infoPanel.petStrings[infoPanel.profileImage.getSelectedIndex()]);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(person instanceof Admin) {
						try {
							data.updateAdmin(person.getId(), infoPanel.textFieldName.getText(), infoPanel.textFieldSurname.getText(),
									infoPanel.textFieldUsername.getText(), infoPanel.textFieldPassword.getText(),
									infoPanel.birthday.getDayChooser().getDay(), infoPanel.birthday.getMonthChooser().getMonth()+1, 
									infoPanel.birthday.getYearChooser().getYear(),
									infoPanel.petStrings[infoPanel.profileImage.getSelectedIndex()]);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					data.refreshAdminList();
					data.refreshManagerList();
					data.refreshClientList();
					userPanel.refreshWindow();
					setVisible(false);
					dispose();
				}
			}


			public void setMonth(int month) {
				this.month = month;
			}


			public void setDay(int day) {
				this.day = day;
			}


			public void setYear(int year) {
				this.year = year;
			}
			
		});
		add(infoPanel);
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}
	
}
