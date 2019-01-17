package dialogGUI;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JYearChooser;

import data.DataManager;
import footballManagement.Country;
import footballManagement.Stadium;
import gui.TeamPanel;


public class AddTeam extends JDialog {

	private static final long serialVersionUID = 1L;

	private DataManager data;
	private ArrayList<Country> countryList;
	private ArrayList<Stadium> stadiumList;
	
	final String[] teamStatus= {"professional","amateur"};
	
	final String[] numbers= {"0","1","2","3","4","5"};
	
	protected JYearChooser yearFoundation;
	protected JLabel nameLabel,countryLabel,statusLabel,valueLabel,stadiumLabel,yearLabel,managementLabel,stabilityLabel,supportLabel;
	protected JTextField name,value;
	protected JComboBox<String> countryComboBox,statusComboBox,stadiumComboBox;
	protected JComboBox<String>managementComboBox,stabilityComboBox,supportComboBox;
	protected JButton registerBtn;
	
	private TeamPanel teamPanel;
	
	public AddTeam(DataManager dataManager,TeamPanel rootPanel) {
		super();
		this.data=dataManager;
		this.teamPanel=rootPanel;
		countryList=new ArrayList<Country>();
		stadiumList=new ArrayList<Stadium>();
		initializeArray();
		setTitle("Insert a new team");
		setSize(400,300);
		setLayout(new BorderLayout());
		
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}


	private void initializeArray() {
		this.countryList=data.getCountryList();
		this.stadiumList=data.getStadiumList();
	}
	
	protected Component addCenterPanel() {
		JPanel panel=new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		panel.add(addWestPanel(),BorderLayout.WEST);
		panel.add(addEastPanel(), BorderLayout.EAST);
		
		return panel;
	}

	private Component addWestPanel() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(10,1));
		nameLabel=new JLabel("Team name");
		countryLabel= new JLabel("Country");
		statusLabel= new JLabel("Status");
		valueLabel= new JLabel("Value (in Million)");
		stadiumLabel = new JLabel("Stadium");
		name=new JTextField();
		value=new JTextField();
		
		countryComboBox=new JComboBox<String>();
		stadiumComboBox=new JComboBox<String>();
		statusComboBox=new JComboBox<String>(teamStatus);

		for(Country c:countryList) {
			countryComboBox.addItem(c.getCountry_name());
		}
		
		for(Stadium t: stadiumList) {
			stadiumComboBox.addItem(t.getName());
		}
		
		panel.add(nameLabel);
		panel.add(name);
		panel.add(countryLabel);
		panel.add(countryComboBox);
		panel.add(statusLabel);
		panel.add(statusComboBox);
		panel.add(valueLabel);
		panel.add(value);
		panel.add(stadiumLabel);
		panel.add(stadiumComboBox);
		return panel;
	}


	private Component addEastPanel() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(8,1));
		yearLabel = new JLabel("Year of foundation");
		yearFoundation = new JYearChooser();
		managementLabel = new JLabel("Management");
		managementComboBox= new JComboBox<String>(numbers);
		stabilityLabel = new JLabel("Stability");
		stabilityComboBox = new JComboBox<String>(numbers);
		supportLabel=new JLabel("Support");
		supportComboBox= new JComboBox<String>(numbers);
		
		panel.add(yearLabel);
		panel.add(yearFoundation);
		panel.add(managementLabel);
		panel.add(managementComboBox);
		panel.add(stabilityLabel);
		panel.add(stabilityComboBox);
		panel.add(supportLabel);
		panel.add(supportComboBox);
		
		return panel;
	}


	private Component addSouthPanel() {
		JPanel panel=new JPanel();
		registerBtn = new JButton("Register a new team");
		
		registerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Country c=null;
				for(Country a:countryList) {
					if(a.getCountry_name().equals(countryComboBox.getSelectedItem().toString())) {
						c=a;
					}
				}
				
				Stadium s=null;
				for(Stadium st: stadiumList) {
					if(st.getName().equals(stadiumComboBox.getSelectedItem().toString())) {
						s=st;
					}
				}
				
				String valueString=value.getText();
				valueString.replace(",", ".");
				
				try {
					data.insertTeam(name.getText(),c.getCountry_id(),
							statusComboBox.getSelectedItem().toString(),valueString,
							s.getStadiumId(),yearFoundation.getYear(),
							Integer.parseInt((String) managementComboBox.getSelectedItem()),
							Integer.parseInt((String) stabilityComboBox.getSelectedItem()),
							Integer.parseInt((String) supportComboBox.getSelectedItem()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				teamPanel.refreshWindow();
				setVisible(false);
				dispose();
			}
			
		});
		
		panel.add(registerBtn);
		return panel;
	}
	
}
