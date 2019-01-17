package rootClassesGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import data.Data;
import data.DataManager;
import dialogGUI.AdminRegistration;
import dialogGUI.ClientRegistration;
import dialogGUI.ManagerRegistration;
import userClasses.Admin;
import userClasses.Client;
import userClasses.Manager;
import userClasses.Person;
import utils.DBManager;
import utilsGUI.BackgroundImagePanel;


/**
 * Collega le varie funzionalità della grafica.
 */
public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private final String homeImage="./images/firstPage.jpg";
	
	private JPanel rootPanel;
	private JPanel myPanel;

	private JTextField textFieldLogin;
	private JPasswordField textFieldPassword;
	

	/*northPanel*/
	private JLabel labelWelcome; 
	private JPanel northPanel;
	
	/*centrePanel*/
	private JPanel centrePanel;
	private JLabel labelLogin;
	private JButton btnLogin;
	private JLabel labelPassword;
	
	private GridBagConstraints gbc;
	
	/*southPanel*/
	private JPanel southPanel;
	private ButtonGroup groupButton;
	private JRadioButton radioBtnClient;
	private JRadioButton radioBtnManager;
	private JRadioButton radioBtnAdmin;
	private JButton btnRegister;
	
	private String passwordSystem = "OOP";
	private String passwordManager= "newManager";
	private String passwordInput="";
	
	private DataManager data;
	private DBManager dbManager;
	private Data database;
	
	public LoginFrame() {
		setTitle("Football Management System");
		setLocation(350,150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rootPanel=new BackgroundImagePanel(homeImage,new Dimension(700,400));
		
		
		gbc = new GridBagConstraints();
		rootPanel.setLayout(new GridBagLayout());
		
		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
		
		myPanel.add(createNorthPanel(), BorderLayout.NORTH);
		myPanel.add(createCentrePanel(), BorderLayout.CENTER);
		myPanel.add(createSouthPanel(), BorderLayout.SOUTH);
		
		try {
			System.out.println("connessione di Data(database)");
			setDatabase(new Data());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("connessione di dbmanager");
			dbManager= new DBManager(DBManager.JDBCDriverMySQL,DBManager.JDBCURLMySQL);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data=new DataManager(dbManager);
		
		
		gbc.gridx=10;
		gbc.gridy=0;
		gbc.ipadx=50;
		gbc.ipady=10;

		rootPanel.add(myPanel,gbc);
		add(rootPanel);
		setResizable(false);
		pack();
		setVisible(true);
		requestFocus();    // "this" JFrame requests focus to receive KeyEvent
	}


	public JPanel createNorthPanel() {
		labelWelcome = new JLabel("Welcome to Football Management System");
		northPanel = new JPanel();
		northPanel.add(labelWelcome);
		return northPanel;
	}

	public void setVisibility(boolean flag) {
		setVisible(flag);
	}

	public JPanel createCentrePanel() {
		labelLogin = new JLabel("Login");
		textFieldLogin = new JTextField(10);
//		textFieldLogin.setText("");
		labelPassword = new JLabel("Password");
		textFieldPassword = new JPasswordField(10);
//		textFieldPassword.setText("");
		btnLogin = new JButton("Login");
		
		
		textFieldLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("LOGIN CON INVIO");
					loginAction();
				}
			}
		});
		
		textFieldPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("LOGIN CON INVIO");
					loginAction();
				}
			}
		});
		
		
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loginAction();
				requestFocus(); // change the focus to JFrame to receive KeyEvent
			}			
		});
		
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("LOGIN CON INVIO");
					loginAction();
				}
			}
			
		});
		
		centrePanel = new JPanel();
		centrePanel.setLayout(new GridLayout(3, 2));
		centrePanel.add(labelLogin);
		centrePanel.add(textFieldLogin);
		centrePanel.add(labelPassword);
		centrePanel.add(textFieldPassword);
		centrePanel.add(btnLogin);
		//centrePanel.add(pswVisibility);
		return centrePanel;
	}

	private void loginAction() {
		Person user = data.selectLogin(textFieldLogin.getText(), new String(textFieldPassword.getPassword()));
		if ( user instanceof Client){
			new HomeWindow((Client)user,data);
			setVisible(false);
			dispose();
		} else if(user instanceof Admin){ 
			new HomeWindow((Admin)user,data);
			setVisible(false);
			dispose();
		}else if(user instanceof Manager){
			new HomeWindow((Manager)user,data);
			setVisible(false);
			dispose();		
		}
	}
	
	
	public JPanel createSouthPanel() {
		southPanel = new JPanel();
		southPanel.setBorder(new TitledBorder(new EtchedBorder(), "User registration"));
		groupButton = new ButtonGroup();
		radioBtnClient = new JRadioButton("Client");
		radioBtnAdmin = new JRadioButton("Admin");
		radioBtnManager = new JRadioButton("Manager");
		groupButton.add(radioBtnClient);
		groupButton.add(radioBtnManager);
		groupButton.add(radioBtnAdmin);
		groupButton.setSelected(radioBtnClient.getModel(), true);

		btnRegister = new JButton("Sign in");

		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				registerAction();
			}
		});
		
		btnRegister.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("REGISTER CON INVIO");
					registerAction();
				}
			}
		});
		
		southPanel.add(radioBtnClient);
		southPanel.add(radioBtnManager);
		southPanel.add(radioBtnAdmin);
		southPanel.add(btnRegister);
		return southPanel;
	}


	public void registerAction() {
		if(radioBtnAdmin.isSelected()) {
			passwordInput = JOptionPane.showInputDialog("enter the identification password");
			try {
				if(passwordInput.equalsIgnoreCase(passwordSystem)) {
					new AdminRegistration(data,true,null);
					setVisible(false);
					dispose();
				}
				else {
					if(passwordInput.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Password is empty, insert a password");
					}
					else
						JOptionPane.showMessageDialog(null, "Password not correct");
				}
			}catch(NullPointerException e1) {
				
			}
		}
		if(radioBtnClient.isSelected()) {
			new ClientRegistration(data,true,null);
			setVisible(false);
			dispose();
		}
		if(radioBtnManager.isSelected()) {
			
			passwordInput = JOptionPane.showInputDialog("enter the identification password");
			try {
				if(passwordInput.equalsIgnoreCase(passwordManager)) {
					new ManagerRegistration(data,true,null);
					setVisible(false);
					dispose();
				}
				else {
					if(passwordInput.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Password is empty, insert a password");
					}
					else
						JOptionPane.showMessageDialog(null, "Password not correct");
				}
			}catch(NullPointerException e1) {
				
			}
		}
	}
	
	public Data getDatabase() {
		return database;
	}


	public void setDatabase(Data database) {
		this.database = database;
	}

}
