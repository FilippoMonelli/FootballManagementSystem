package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.DataManager;
import footballManagement.Ticket;
import userClasses.Admin;
import userClasses.Person;
import utilsGUI.SpreadsheetManager;
import utilsGUI.UtilsPanel;

public class TicketPanel extends UtilsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DataManager data;
	
	private ArrayList<Ticket> ticketList;
	
	private Person user;
	
	private String[] columns = {"Ticket id","Ticket code","Match id","Owner","Date"};
	
	private String[] row;
	
	private String[][] allTickets;

	private JTable ticketTable;

	private DefaultTableModel model;
	
	public TicketPanel(DataManager data,Person user) {
		super();
		this.data=data;
		this.user=user;
		this.ticketList=new ArrayList<Ticket>();
		setLayout(new BorderLayout());
		
		
	
		super.add(addTicketTablePanel(),BorderLayout.CENTER);
		super.add(super.addSouthPanel(),BorderLayout.SOUTH);

		
		super.addBtn.setEnabled(false);
		super.deleteBtn.setEnabled(false);
		super.updateBtn.setEnabled(false);
		super.addBtn.setVisible(false);
		super.deleteBtn.setVisible(false);
		super.updateBtn.setVisible(false);
		
		super.reportBtn.setToolTipText("Create a spreadsheet of table");
		
		
		super.reportBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SpreadsheetManager((String[]) columns,allTickets);
			}
			
		});
		
	}

	
	private JScrollPane addTicketTablePanel() {
		ticketTable= new JTable();
		
		
		
		if(user instanceof Admin) {
			data.refreshTicket();
			this.ticketList=data.getAllTicketList();
		}else {
			data.refreshUserTicket(user);
			this.ticketList=data.getUserTicketList(user);
		}
		
		model = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		
		model.setColumnIdentifiers(columns);
	
		
		ticketTable.setModel(model);

		JScrollPane pane = new JScrollPane(ticketTable);
		
		int numberOfColumns=5;
		
		row=new String[numberOfColumns];
		
		allTickets= new String[ticketList.size()][numberOfColumns];
		
		int i=0;
		
		for(Ticket p: ticketList) {
		
			row[0]=Integer.toString(p.getTicketId());
			row[1]=p.getCode();
			row[2]=Integer.toString(p.getMatchId());
			row[3]=p.getOwner();
			row[4]=p.getDate();
			model.addRow(row);
			
			for(int j=0;j<numberOfColumns;++j) {
				allTickets[i][j]=row[j];
			}
			++i;
		}
		return pane;
	}
}
