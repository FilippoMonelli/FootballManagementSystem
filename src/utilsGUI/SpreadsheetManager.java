package utilsGUI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetManager {
	
	public SpreadsheetManager(String[] firstrow, String[][] allElements){
	XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();
    

    int rowNum = 0;
    System.out.println("Creating excel");
    
    JFileChooser save = new JFileChooser(); 
    FileFilter xlsx = new FileNameExtensionFilter("Xlsx document ( *.xlsx )", "xlsx");
    FileFilter xls= new FileNameExtensionFilter("Xls document ( *.xls )", "xls");
    save.addChoosableFileFilter(xlsx);
    save.addChoosableFileFilter(xls);
    save.setFileFilter(xlsx);
    save.setFileFilter(xls);
    
	int option = save.showSaveDialog(save); 
	if (option == JFileChooser.APPROVE_OPTION) {

		// Create a FirstRow
	    Row headerRow = sheet.createRow(rowNum++);

	    for (int i = 0; i < firstrow.length; i++) {
	      Cell cell = headerRow.createCell(i);
	      cell.setCellValue(firstrow[i]);
//	      cell.setCellStyle(headerCellStyle);
	    }
	    
	    
	    /*create other rows*/
	    for (String[] riga: allElements) {
	    	int j=0;
	    	Row row = sheet.createRow(rowNum++);
	    	for(int i=0;i<riga.length;++i) {
	    		row.createCell(j).setCellValue(riga[i]);
	    		++j;
	    	}
	        
	    }
		
	 // Resize all columns to fit the content size
	    for (int i = 0; i < firstrow.length; i++) {
	      sheet.autoSizeColumn(i);
	    }
	    
		        try {
//		        	System.out.println(save.getSelectedFile().getPath()+extensionFile.getExtensions()[0]);
		        	FileNameExtensionFilter extensionFile = (FileNameExtensionFilter) save.getFileFilter();
		        	FileOutputStream outputStream = new FileOutputStream(save.getSelectedFile().getPath()+"."+extensionFile.getExtensions()[0]);
		            workbook.write(outputStream);
		            workbook.close();
		            JOptionPane.showMessageDialog(null,
							"Export of information has been successful.",
							"Export information",
							JOptionPane.INFORMATION_MESSAGE);
		        } catch(ClassCastException e) {
		        	JOptionPane.showMessageDialog(null,
							"Please, select a valid extension.",
							"Extensions error",
							JOptionPane.WARNING_MESSAGE);
		        }
		        catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        System.out.println("Done");
	}

	}
	
}
