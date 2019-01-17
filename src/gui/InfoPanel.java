package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JCalendar;

import data.DataManager;
import userClasses.Admin;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;

public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel labelName, labelSurname, labelUsername, labelPassword;
	protected JTextField textFieldName, textFieldSurname, textFieldUsername, textFieldPassword;
	protected JPanel  centerPanel, southPanel;
	
	String[] petStrings = { "monkey", "dog" };
	
	protected JLabel picture;
	protected JComboBox<String> profileImage;
	protected JPanel picturePanel;

	private DataManager data;
	
	private Person person;
	
	private JPanel centerPanelDx,centerPanelSx;
	
	protected JButton updateBtn;
	
	protected JCalendar birthday;

	
	private int n = -1;
	
	private boolean myInfoSelection = false; 
	
	public InfoPanel(Person user,DataManager data,boolean status){
		setLayout(new BorderLayout());
		
		this.data=data;
		
		this.myInfoSelection=status;
		
		try {
			this.person=data.selectPerson(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		setVisible(true);
		
	}
	
	

	private JPanel addCenterPanel() {
		centerPanel=new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JPanel centerRootPanel = new JPanel();
		centerRootPanel.setLayout(new GridLayout(1,2));
		
		centerPanel.add(new JPanel(),BorderLayout.WEST);
		centerPanelSx=new JPanel();
		centerPanelSx.setLayout(new GridLayout(10,1));
		JPanel labelNamePanel= new JPanel();
		labelName=new JLabel("Name");
		labelNamePanel.add(labelName);
		JPanel labelSurnamePanel= new JPanel();
		labelSurname=new JLabel("Surname");
		labelSurnamePanel.add(labelSurname);
		JPanel labelUsernamePanel= new JPanel();
		labelUsername=new JLabel("Username");
		labelUsernamePanel.add(labelUsername);
		JPanel labelPasswordPanel= new JPanel();
		labelPassword=new JLabel("Password");
		labelPasswordPanel.add(labelPassword);
		
		JPanel textFieldNamePanel = new JPanel();
		textFieldNamePanel.setLayout(new GridLayout(1,1));
		textFieldName=new JTextField(person.getName());
		textFieldNamePanel.add(textFieldName);
		JPanel textFieldSurnamePanel = new JPanel();
		textFieldSurnamePanel.setLayout(new GridLayout(1,1));
		textFieldSurname=new JTextField(person.getSurname());
		textFieldSurnamePanel.add(textFieldSurname);
		JPanel textFieldUsernamePanel = new JPanel();
		textFieldUsernamePanel.setLayout(new GridLayout(1,1));
		textFieldUsername=new JTextField(person.getUsername());
		textFieldUsernamePanel.add(textFieldUsername);
		JPanel textFieldPasswordPanel = new JPanel();
		textFieldPasswordPanel.setLayout(new GridLayout(1,1));
		textFieldPassword=new JTextField(person.getPassword());
		textFieldPasswordPanel.add(textFieldPassword);
		
		centerPanelSx.add(new JPanel());
		centerPanelSx.add(labelNamePanel);
		centerPanelSx.add(textFieldNamePanel);
		centerPanelSx.add(labelSurnamePanel);
		centerPanelSx.add(textFieldSurnamePanel);
		centerPanelSx.add(labelUsernamePanel);
		centerPanelSx.add(textFieldUsernamePanel);
		centerPanelSx.add(labelPasswordPanel);
		centerPanelSx.add(textFieldPasswordPanel);
		centerPanelSx.add(new JPanel());

		
		centerPanelDx=addImageCalendarPanel();
		
		centerRootPanel.add(centerPanelSx);
		centerRootPanel.add(centerPanelDx);
		
		centerPanel.add(centerRootPanel,BorderLayout.CENTER);
	
		
		centerPanel.add(new JPanel(),BorderLayout.EAST);
		
		return centerPanel;
	}
	
	private JPanel addImageCalendarPanel() {
		JPanel imgBirthPanel = new JPanel();
		imgBirthPanel.setLayout(new GridLayout(2,1));
		
		birthday=new JCalendar();
		
		profileImage = new JComboBox<String>(petStrings);
		
		int i=0;
		for(;i<petStrings.length;++i) {
			if(petStrings[i].equals(person.getImage())) {
				break;
			}
		}
		
        profileImage.setSelectedIndex(i);
       
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date inputDate = null;
        try {
			inputDate = dateFormat.parse(person.getBirthday().replaceAll("-", "/"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        birthday.setDate(inputDate);
        
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
        updateLabel(petStrings[profileImage.getSelectedIndex()]);
 
        picturePanel = new JPanel();
        picturePanel.setLayout(new BorderLayout());
        
        picturePanel.add(profileImage,BorderLayout.NORTH);
        picturePanel.add(picture,BorderLayout.CENTER);
        
        JPanel panelTop=new JPanel();
        JPanel panelBot=new JPanel();
        
        panelTop.setBorder(new TitledBorder(new EtchedBorder(), "Profile image"));
        
        panelBot.setBorder(new TitledBorder(new EtchedBorder(), "Date of birthday"));
              
        panelTop.add(picturePanel);
        panelBot.add(birthday);
        
        imgBirthPanel.add(panelTop);
        imgBirthPanel.add(panelBot);
				
		profileImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
		        String petName = (String)cb.getSelectedItem();
		        updateLabel(petName);
			}

        });
		
		return imgBirthPanel;
	}

	
	protected Component addSouthPanel() {
		southPanel = new JPanel();
		JPanel updateBtnPanel = new JPanel();
		updateBtnPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		updateBtn = new JButton("Update");
		updateBtn.setSize(50,10);
		updateBtnPanel.add(updateBtn);
		southPanel.add(updateBtnPanel,BorderLayout.SOUTH);
		
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int month=birthday.getMonthChooser().getMonth()+1;
				
	
				
				
				String info="Name: "+textFieldName.getText()+
						"\nSurname: "+ textFieldSurname.getText()+
						"\nUsername: "+textFieldUsername.getText()+
						"\nPassword: "+textFieldPassword.getText()+
						"\n"+birthday.getDayChooser().getDay()+"/"+
						month+"/"+
						birthday.getYearChooser().getYear()+
						"\nprofile image: "+petStrings[profileImage.getSelectedIndex()];
				
				System.out.println(info);
				
				if(myInfoSelection) {
				n = JOptionPane.showConfirmDialog(
					    null,
					    "Do you want update your info?\n"+
					    info,
					    "Update info",
					    JOptionPane.YES_NO_OPTION);
				
			System.out.println(n);
				}
			
				if(n==0) {
					try {
						if(person instanceof Client) {
						data.updateClient(person.getId(), textFieldName.getText(), textFieldSurname.getText(),
								textFieldUsername.getText(), textFieldPassword.getText(),
								birthday.getDayChooser().getDay(), month, birthday.getYearChooser().getYear(),
								petStrings[profileImage.getSelectedIndex()]);
						}else if(person instanceof Manager) {
							data.updateManager(person.getId(), textFieldName.getText(), textFieldSurname.getText(),
									textFieldUsername.getText(), textFieldPassword.getText(),
									birthday.getDayChooser().getDay(), month, birthday.getYearChooser().getYear(),
									petStrings[profileImage.getSelectedIndex()]);
						}else if(person instanceof Admin) {
							data.updateAdmin(person.getId(), textFieldName.getText(), textFieldSurname.getText(),
									textFieldUsername.getText(), textFieldPassword.getText(),
									birthday.getDayChooser().getDay(), month, birthday.getYearChooser().getYear(),
									petStrings[profileImage.getSelectedIndex()]);
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
								"Attention: error during update information.",
								"Update error",
								JOptionPane.WARNING_MESSAGE);
					}
					
				}
				
			}
			
		});
		return updateBtnPanel;
	}
	

    protected void updateLabel(String name) {
        ImageIcon icon =new ImageIcon("./images/Profile/" + name + ".jpg");
        picture.setIcon(icon);
        picture.setToolTipText("A drawing of a " + name.toLowerCase());
        picture.setText(null);
        
    }



	public int getN() {
		return n;
	}



	public void setN(int n) {
		this.n = n;
	}

	
	
}
