package dialogGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import data.DataManager;
import gui.UserPanel;
import rootClassesGUI.LoginFrame;
import userClasses.Admin;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;

public class ManagerRegistration extends JDialog{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel labelName, labelSurname, labelUsername, labelPassword;
	protected JTextField textFieldName, textFieldSurname, textFieldUsername, textFieldPassword;
	private JPanel  centerPanel, southPanel;
	
	String[] petStrings = { "monkey", "dog" };
	
	private JLabel picture;
	protected JComboBox<String> profileImage;
	private JPanel picturePanel;

	protected String loginType=null;
	
	protected DataManager data;
	
	protected JButton registerBtn;
	
	protected JCalendar birthday;
	
	protected boolean status= false;
	
	protected UserPanel userPanel=null;
	
	protected ArrayList<Person> people;
	
	protected ActionListener registerListener;
	
	
	public ManagerRegistration(DataManager data,boolean status,UserPanel rootPanel) {
		setTitle("Manager registration");
		setSize(400,400);
		setLayout(new BorderLayout());
		setLocation(400,200);
		this.status=status;
		this.userPanel=rootPanel;
		loginType="manager";
		
		this.data=data;
		
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}

	private JPanel addCenterPanel() {
		centerPanel=new JPanel();
		centerPanel.setLayout(new GridLayout(4,4));
		labelName=new JLabel("Name");
		labelSurname=new JLabel("Surname");
		labelUsername=new JLabel("Username");
		labelPassword=new JLabel("Password");
		textFieldName=new JTextField();
		textFieldSurname=new JTextField();
		textFieldUsername=new JTextField();
		textFieldPassword=new JTextField();
		
		centerPanel.add(labelName);
		centerPanel.add(textFieldName);
		centerPanel.add(labelSurname);
		centerPanel.add(textFieldSurname);
		centerPanel.add(labelUsername);
		centerPanel.add(textFieldUsername);
		centerPanel.add(labelPassword);
		centerPanel.add(textFieldPassword);
		
		
		
		return centerPanel;
	}
	
	private Component addSouthPanel() {
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		birthday=new JCalendar();
		
		
		profileImage = new JComboBox<String>(petStrings);
        profileImage.setSelectedIndex(0);
        
        
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
        updateLabel(petStrings[profileImage.getSelectedIndex()]);
        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
 
        JPanel pictureCalendarPanel= new JPanel();
        
        pictureCalendarPanel.setLayout(new GridLayout(1,2));
        
        picturePanel = new JPanel();
        picturePanel.setLayout(new BorderLayout());
        
        picturePanel.add(profileImage,BorderLayout.NORTH);
        picturePanel.add(picture,BorderLayout.CENTER);
        
		pictureCalendarPanel.add(picturePanel);
		pictureCalendarPanel.add(birthday);
		southPanel.add(pictureCalendarPanel);
		
		
		JPanel registerBtnPanel = new JPanel();
		registerBtn = new JButton("Register");
		registerBtnPanel.add(registerBtn);
		southPanel.add(registerBtnPanel,BorderLayout.SOUTH);
	
		registerBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!checkUniqueUsername(textFieldUsername.getText())) {
					JOptionPane.showMessageDialog(null,
	            		    "Username already exists in the database,\nchoose another username.",
	            		    "Username error",
	            		    JOptionPane.WARNING_MESSAGE);
				}else {
				int month=birthday.getMonthChooser().getMonth()+1;
				int n = -2;
				String info="Name: "+textFieldName.getText()+
						"\nSurname: "+ textFieldSurname.getText()+
						"\nUnsername:"+textFieldUsername.getText()+
						"\nPassword:"+textFieldPassword.getText()+
						"\n"+birthday.getDayChooser().getDay()+"/"+
						month+"/"+
						birthday.getYearChooser().getYear()+
						"\nprofile image:"+petStrings[profileImage.getSelectedIndex()];
				
				System.out.println(info);
				if(loginType.equals("manager")){
				n = JOptionPane.showConfirmDialog(
					    null,
					    "Do you want to register?\n"+
					     info,
					    "Manager registration",
					    JOptionPane.YES_NO_OPTION);
				System.out.println(n);
				}
				if(n==0 && loginType.equals("manager")) {
					try {
						data.registerManager(textFieldName.getText(), textFieldSurname.getText(), textFieldUsername.getText(),
								textFieldPassword.getText(), birthday.getDayChooser().getDay(), month, 
								birthday.getYearChooser().getYear(), petStrings[profileImage.getSelectedIndex()]);
						if(status) 
							new LoginFrame();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					data.refreshManagerList();
					if(userPanel!= null) {
						userPanel.refreshWindow();
					}
					setVisible(false);
					dispose();
				}
				}/*end_else*/	
			}
			
		});
		
		profileImage.addActionListener(new ActionListener() {
			
			JComboBox<String> cb;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cb = (JComboBox<String>)e.getSource();
		        String petName = (String)cb.getSelectedItem();
		        updateLabel(petName);
			}

        });
		
		return southPanel;
	}

	

    protected void updateLabel(String name) {
        ImageIcon icon =new ImageIcon("./images/Profile/" + name + ".jpg");
        picture.setIcon(icon);
        picture.setToolTipText("A drawing of a " + name.toLowerCase());
        picture.setText(null);
    }
	

    protected boolean checkUniqueUsername(String username) {
    	people = new ArrayList<Person>();
    	
    	for(Admin a: data.getAdminList()) {
    		people.add(a);
    	}
    	for(Manager m:data.getManagerList()) {
    		people.add(m);
    	}
    	for(Client c: data.getClientList()) {
    		people.add(c);
    	}
    	
    	for(Person p: people) {
    		if(p.getUsername().equals(username))
    			return false;
    	}
    	
    	return true;
    }
	
	
	
}
