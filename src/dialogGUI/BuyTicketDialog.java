package dialogGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.DataManager;
import footballManagement.Match;
import userClasses.Person;
import utils.CreateTicket;

public class BuyTicketDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Person user;
	private Match match;
	private DataManager data;
	
	private JLabel ticketLabel;
	private JButton buyBtn;
	private JSpinner ticketSpinner;
	private JCheckBox responsability;
	
	private JFileChooser save;

	public BuyTicketDialog(Match match, Person user, DataManager data) {
		
		this.data=data;
		this.user=user;
		this.match=match;
		
		setTitle("Buy a ticket");
		setLocation(100,50);
		setSize(450,150);
		setLayout(new BorderLayout());
		
		add(addCenterPanel(),BorderLayout.CENTER);
		add(addSouthPanel(),BorderLayout.SOUTH);
		
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}

	private Component addCenterPanel() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		//default value,lower bound,upper bound,increment by
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 4, 1); 
		
		ticketLabel= new JLabel("How many tickets do you want to buy?");
		
		ticketSpinner = new JSpinner(sm);
		ticketSpinner.setValue(1);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,3));
		p.add(new JPanel());
		p.add(ticketSpinner);
		panel.add(ticketLabel);
		panel.add(p);
		return panel;
	}

	private Component addSouthPanel() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(2,1));
		responsability=new JCheckBox("I declare to take up my resposability by clicking the \"BUY\" button");
		buyBtn=new JButton("BUY");
		JPanel p=new JPanel();
		p.add(buyBtn);
		panel.add(responsability);
		panel.add(p);
		
		buyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(responsability.isSelected()) {
					if(((int)ticketSpinner.getValue() < 5) && ((int)ticketSpinner.getValue() >= 1)) {
						save = new JFileChooser(); 
						save.setDialogTitle("Save your tickets in PDF document");
						FileFilter pdf=new FileNameExtensionFilter("PDF document ( *.pdf )", "pdf");
						save.addChoosableFileFilter(pdf);
						save.setFileFilter(pdf);
						int option = save.showSaveDialog(save); 
						FileNameExtensionFilter extensionFile=null;
						try {
							extensionFile = (FileNameExtensionFilter) save.getFileFilter();
							if (option == JFileChooser.APPROVE_OPTION) {
								String path=save.getSelectedFile().getPath()+"."+extensionFile.getExtensions()[0];
								System.out.println(path);
								new CreateTicket(match,user,(int)ticketSpinner.getValue(),path,data);
							}
						}catch(ClassCastException e) {
							JOptionPane.showMessageDialog(null,
									"Please, select the correct extension.",
									"Extensions error",
									JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null,
								"Please, select a number of tickets between 1 and 4. ",
								"Tickets number error",
								JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null,
                		    "Please, select resposability ",
                		    "Responsability error",
                		    JOptionPane.WARNING_MESSAGE);
				}
				
//		        System.out.println(currentDate);
				
		        
				setVisible(false);
				dispose();
					
			}
		
		});
		
		return panel;
	}

	

	

}
