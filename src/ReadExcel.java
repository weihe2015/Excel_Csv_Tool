/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wei0000
 */
import java.io.BufferedInputStream; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.InputStream;
import java.util.Iterator; 
import au.com.bytecode.opencsv.CSVWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
    
    public static String userDir = System.getProperty("user.dir");
    private static int rowNumber = 0;
    private static int columnNumber = 5;
    
    
        public static synchronized void log(String msg){
          
        DataOutputStream dos = null;
        try{
            dos = new DataOutputStream(new FileOutputStream(userDir + "\\log.txt", true));
            dos.writeBytes(msg + System.getProperty("line.separator"));
            dos.close();
        } catch (FileNotFoundException ex){
         
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    
    
    public static String[][] readFile(String filename) throws Exception{
        
       try
        {
            FileInputStream file = new FileInputStream(new File(filename));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> row1 = sheet.iterator();
            Row rowIter1 = row1.next();
            Iterator<Cell> cellIter = rowIter1.cellIterator();
            int ExcelColumn = 0;
            while(cellIter.hasNext()){
                ExcelColumn++;
                Cell cellCount = cellIter.next();
            }
            while(row1.hasNext()){
                rowIter1 = row1.next();
                rowNumber++;
            }
            
             log("The total row number is " + rowNumber);
             System.out.println(rowNumber);
           //rowNumber = rowNumber-1;
            
            String[][] csv = new String[rowNumber][columnNumber];
            int LangColumn = ExcelColumn - 3;
            String[] Lang = new String[LangColumn];
            
            Iterator<Row> rowIterator = sheet.iterator();
            
            Row rowIter2 = rowIterator.next();
            
            Iterator<Cell> cellIterator1 = rowIter2.cellIterator();
            Cell cell1;
            for(int i=0; i<3; i++){
                 cell1 = cellIterator1.next();
            }
            for(int i=0; i<LangColumn; i++){
                 cell1 = cellIterator1.next();
                 Lang[i] = cell1.getStringCellValue().toLowerCase();
            }
            
            System.out.println(Arrays.toString(Lang));
            log("The language set is " + Arrays.toString(Lang));
            
            String tempLang = "";
            int row = 0;
            int column = 0;
            while (rowIterator.hasNext()) 
            {
                Row rowIter = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = rowIter.cellIterator();
                column = 0;
                while (cellIterator.hasNext()) 
                {   
                    Cell cell = cellIterator.next();
                    
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            if(cell.getNumericCellValue() == 0){
                                csv[row][1] = " ";
                            }
                            else{
                            csv[row][1] = String.valueOf(cell.getNumericCellValue());
                            }
                            //System.out.print(csv[row][1] + " ");
                            //System.out.print(cell.getNumericCellValue() + " ");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            // Add on July 17th to solve the problem that the version number are too long, such as 4.1.2.1.2
                            if(column == 2){
                              csv[row][1]= cell.getStringCellValue().trim();
                              //System.out.println(csv[row][1] + " ");
                            }
                            // Revised on July 17th
                            else if(column == 0){
                                csv[row][4] = cell.getStringCellValue().trim();
                                //System.out.print(csv[row][4] + " ");
                            }
                            else if(column == 1){
                                csv[row][0] = cell.getStringCellValue().trim();
                               //System.out.print(csv[row][0] + " ");
                            }
                            else {
                                  if(cell.getStringCellValue().equalsIgnoreCase(Lang[column-3])|| cell.getStringCellValue().equalsIgnoreCase("Y")){
                                          if(cell.getStringCellValue().equalsIgnoreCase("zh-CN") || (column == 8 && cell.getStringCellValue().equalsIgnoreCase("Y"))){
                                              //tempLang = tempLang + "&" + Lang[column-3].trim();
                                              tempLang = tempLang + "zh-CN, ";
                                          }
                                          else if(cell.getStringCellValue().equalsIgnoreCase("pt-br") || (column == 11 && cell.getStringCellValue().equalsIgnoreCase("Y"))){
                                              tempLang = tempLang + "pt-BR, ";
                                          }
                                          else if(cell.getStringCellValue().equalsIgnoreCase("pt-pt") || (column == 19 && cell.getStringCellValue().equalsIgnoreCase("Y"))){
                                              tempLang = tempLang + "pt-PT, ";
                                          }
                                          else{
                                            //tempLang = tempLang + "&" + Lang[column-3].toLowerCase().trim();
                                              tempLang = tempLang + Lang[column-3].toLowerCase().trim() + ", ";
                                          }
                                       }

                            } 
                            //System.out.print(cell.getStringCellValue() + " ");
                            break;
                    }
                    column++;
                }
//                if(!tempLang.isEmpty()){   
//                tempLang = tempLang.substring(0,tempLang.lastIndexOf("&"));
//                //System.out.println(tempLang);
//                }
                
                csv[row][3] = tempLang.substring(0,tempLang.length()-2);
                csv[row][2] = "Web";
                //System.out.println(csv[row][3]);
                //System.out.println("");
                tempLang = "";
                row++;
                
            }
            //System.out.println(row);
            file.close();
            
//            for(int i=0; i<rowNumber; i++){
//                for(int j=0; j<columnNumber; j++){
//                    try{
//                    System.out.print(csv[i][j] + " ");
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println();
//            }
            
          writeTempFile(csv, filename); 
          return csv; 
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
       
       return null;
      }
    
    public static void writeTempFile(String[][] file, String filename) throws IOException{
        
        File dir = new File(filename.substring(0,filename.lastIndexOf("\\")));
                
        if(!dir.exists()){
            dir.mkdir();
        }        
        try{
        String outputFile = new StringBuilder(dir.toString()+ "\\" + filename.substring(filename.lastIndexOf("\\")+1,filename.lastIndexOf(".xlsx"))+"_converted")
              .append(".csv").toString();
        CSVWriter writer = new CSVWriter(new FileWriter(outputFile));
          
        for (int i=0; i<file.length; i++){ 
           writer.writeNext(file[i]);
        }
        writer.close();
        log("source file created successfully!");
        System.out.println("source file created successfully!");
        JOptionPane.showMessageDialog(null, "Source file created successfully!");
        }
        catch(Exception e){
            
        }
        
    }
    
    
//    public static void main(String args[]){
//        //ArcGIS 10.3_CMS_PubList_Master_20140717
//        String filename = "C:\\Users\\wei0000\\Desktop\\ArcGIS10.3_CMS_PubList_HO03_20140718.xlsx";
//        try{
//         // readFile(filename);
//        }catch(Exception e){
//            
//        }
//    }
    
}
