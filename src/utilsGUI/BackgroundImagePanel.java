package utilsGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BackgroundImagePanel extends JPanel {


	private static final long serialVersionUID = 1L;
	private Image background;
	private String path;
	
//
//	public BackgroundImagePanel(String path, Dimension d) {
//		this.path = path;
//		try {
//			this.background = ImageIO.read(new File(path));
//		}catch(IOException e) {
//			this.background=null;
//		}
//		this.setPreferredSize(d);
//	}
//	
//	/*esempio studenti per gestionale*/
//	/*esempip mirino*/
//	
//	@Override
//	public void paint(Graphics g) {
//		if(background != null) {
//			super.printComponent(g);
//			g.drawImage(background, 0, 0, this);
//		}else {
//			super.paintComponent(g);
//			setBackground(Color.BLACK);
//		}
//	}
//
	

	
	
	public BackgroundImagePanel(String path, Dimension d) {
		this.setPath(path);
		try {
			this.background = ImageIO.read(new File(path));
		} catch (IOException e) {
			this.background=null;
		}
		this.setPreferredSize(d);
	}
	
	
	protected void paintComponent(Graphics g) {
		if(background!=null) {
			super.paintComponent(g);
	    	int height = (int)this.getSize().getHeight();
	    	int width = (int)this.getSize().getWidth();
	    	Image img = this.background.getScaledInstance(width, height, Image.SCALE_DEFAULT);
	    	g.drawImage(img, 0, 0, this);
		}else {
			super.paintComponent(g);
			setBackground(Color.BLACK);
			
		}
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

}
