package dialogGUI;

import java.awt.Dialog;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel title,version,built;
	
	public AboutDialog() {
		super();
		setTitle("About FSM");
		setLocation(600,300);
		setSize(200,200);
		add(addText());
		super.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
		setVisible(true);
	}

	private JPanel addText() {
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(3,1));
		title = new JLabel();
		title.setText("Football Management System");
		version=new JLabel();
		version.setText("Version: 2019-01(0.0.1)");
		built=new JLabel();
		built.setText("Built by: Filippo Monelli");
		panel.add(title);
		panel.add(version);
		panel.add(built);
		return panel;
	}
	
}

	
