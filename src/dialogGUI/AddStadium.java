package dialogGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.toedter.calendar.JYearChooser;

import data.DataManager;
import gui.StadiumPanel;
public class AddStadium extends JDialog {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StadiumPanel stadiumPanel;
	
	private DataManager data;
	
	private JPanel centerPanel,southPanel;
	private JButton actionBtn;
	
	private JLabel nameLabel,cityLabel,yearLabel,seatsLabel;
	private JTextField nameTextField,cityTextField;
	private JYearChooser yearFoundation;
	private JSpinner seatsSpinner;
	
	public AddStadium(DataManager data, StadiumPanel stadiumPanel) {
		this.stadiumPanel=stadiumPanel;
		this.data=data;
		
		setTitle("New stadium");
		setLocation(150,50);
		setSize(250,150);
		setLayout(new BorderLayout());
		
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		
		setResizable(false);
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setVisible(true);
	}

	private Component addCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(4,2));
		
		nameLabel=new JLabel("Name");
		cityLabel=new JLabel("City");
		seatsLabel=new JLabel("Seats");
		yearLabel=new JLabel("Year");
		
		nameTextField=new JTextField();
		cityTextField=new JTextField();
		seatsSpinner=new JSpinner();
		yearFoundation=new JYearChooser();
		
		centerPanel.add(nameLabel);
		centerPanel.add(seatsLabel);
		centerPanel.add(nameTextField);
		centerPanel.add(seatsSpinner);
		centerPanel.add(cityLabel);
		centerPanel.add(yearLabel);
		centerPanel.add(cityTextField);
		centerPanel.add(yearFoundation);
		
		
		return centerPanel;
	}

	private Component addSouthPanel() {
		southPanel=new JPanel();
		actionBtn=new JButton("Insert a new stadium");
		
		actionBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				try {
					data.insertStadium(nameTextField.getText(),cityTextField.getText(),
							(int)seatsSpinner.getValue(),yearFoundation.getYear());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
						    "Error during the insertion of the stadium.",
						    "Insert warning",
						    JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				}
				
				stadiumPanel.refreshWindow();
				setVisible(false);
				dispose();
			}
		});
		
		southPanel.add(actionBtn);
		
		return southPanel;
	}
	
}
