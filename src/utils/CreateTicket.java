package utils;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import data.DataManager;
import footballManagement.Match;
import footballManagement.Ticket;
import userClasses.Person;


public class CreateTicket extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String ticketImage = "./images/ticket.png";
	
	private BufferedImage image;

	private ArrayList<BufferedImage> images= new ArrayList<BufferedImage>();
	
	int N;
	private String nameFile,home,away,code,name,stadium,data;
	private Match match;
	private Person user;
	private DataManager dataManager;
    public CreateTicket(Match match,Person user,int numTickets,String nameFile,DataManager data) {
    	
    	N=numTickets;
    	this.nameFile=nameFile;
    	this.match=match;
    	this.user=user;
    	new ArrayList<Ticket>();
    	this.dataManager=data;
    	
        try {
            for(int i=0;i<N;++i) {
            	image=ImageIO.read(new File(ticketImage));
            	image = process(image);
            	images.add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        try {
			createPDF(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
  
    }

    private BufferedImage process(BufferedImage old) {
    	int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, w, h, this);
        g2d.setPaint(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 17));
        home = match.getHomeString_team();
        /*random code*/
        code=UUID.randomUUID().toString();
        /*position of code*/
        g2d.drawString(code, 160, 25);

        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        int x=20;
        int y=120;
        g2d.drawString(home, x, y);
        g2d.drawString("VS", 195, 155);
        away= match.getAwayString_team();
        g2d.drawString(away, 270, y);
        
        
        name = String.format("%s %s", user.getName(),user.getSurname());
        stadium = match.getStadiumString();
        data =match.getDate();

        g2d.setPaint(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(name, 150, 220);
        g2d.drawString(data, 410, 220);
        g2d.drawString(stadium, 330, 255);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        g2d.drawString("1", 155, 253);
        
        GregorianCalendar gc = new GregorianCalendar();
        String currentDate=gc.get(Calendar.YEAR)+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.DAY_OF_MONTH);
        
        try {
			dataManager.insertTicket(code,match.getMatch_id(),currentDate,user.getUsername(),user.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        g2d.dispose();
        return img;
    }

    public void createPDF(BufferedImage image) throws DocumentException, MalformedURLException, IOException {
    	
    	Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nameFile));
            document.open();
            for(BufferedImage img:images) {
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	ImageIO.write(img, "png", baos);
            	Image iTextImage = Image.getInstance(baos.toByteArray());
            	document.add(iTextImage);
            }
        	
        	
            document.close();
            System.out.println("Done");
        }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}

