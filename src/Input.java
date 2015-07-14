/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wei0000
 */
import au.com.bytecode.opencsv.CSVWriter;
import java.io.*;
import java.util.*;

public class Input {
 
   private static final ReadExcel re = new ReadExcel();
   private static final List<String> LangList = new ArrayList<>();
   //private static String[][] matrix = new String[row][column];
   
   
//   public static String[][] readFile(String fileName) throws Exception{
//       
//        FileReader fileReader = new FileReader(new File(fileName));
//        BufferedReader br = new BufferedReader(fileReader);
//        
//        String tempLine;
//        int j = 0;
//        String[][] matrix = new String[row][column];
//        
//        while((tempLine = br.readLine()) != null){
//            matrix[j] = tempLine.split(",");
//            j++;
//        }
//       
//        return matrix;
//    } 
   
//    public static void LineNum(String FileName) throws Exception{
//        
//        FileReader fileReader = new FileReader(new File(FileName));
//        BufferedReader br = new BufferedReader(fileReader);
//        String line;
//         
//              while ((line = br.readLine())!= null){
//                  row++;
//                 // System.out.println(line);
//            }
//    }
    
     public static void selectLang(String[][] tempCsv, String language, String filename) throws Exception{
        String[] temp = new String[tempCsv[0].length]; 
        List<String[]> mylist = new ArrayList<>();
        
//        int newRow = 0;
//        for(int i=0; i<row; i++){
//            if(tempCsv[i][2].toLowerCase().contains(language.toLowerCase())){
//               // tempCsv[i][2] = tempCsv[i][2].replace(tempCsv[i][2], language);
//                newRow++;
//                //System.out.println(temp[i][2]);
//            }
//        }
        
        for(int i=0; i<tempCsv.length; i++){
            
         if(tempCsv[i][3].contains(language)){
             temp = Arrays.copyOf(tempCsv[i],tempCsv[0].length);
             temp[3] = temp[3].replace(temp[3],language);
             mylist.add(temp);
          }
        }
        
//          for(int k = 0; k<mylist.size(); k++){
//              System.out.println(Arrays.toString(mylist.get(k)) + " ");
//            }
        
        language = language.toUpperCase();
        
        
        File dir = new File(filename.substring(0,filename.lastIndexOf("\\"))+"\\src_input");
                
        if(!dir.exists()){
            dir.mkdir();
        }        
       //private static final String filename = "C:\\Users\\wei0000\\Desktop\\PubList_HO03.xlsx"; 
        String outputFile = new StringBuilder(dir.toString()+ "\\" + filename.substring(filename.lastIndexOf("\\")+1,filename.lastIndexOf(".xlsx"))+"_").
                append(language).append(".csv").toString();
       try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
           writer.writeAll(mylist);
       }
        re.log("Input files created successfully");
        System.out.println(language + " source file created successfully!");
    }
     
//     public static void csvReader() throws Exception{
//      
//        CSVReader csvReader = new CSVReader(new FileReader(csvFile));
//        String[] row1 = null;
//        
//        while((row1 = csvReader.readNext()) != null){
//            System.out.println(row1[0] + " " + row1[1] + " " + row1[2] + " " + row1[3]);
//            
//        }
//        csvReader.close();
//        
//        String outputFile = "D:\\output.csv";
//        
//        CSVWriter writer = new CSVWriter(new FileWriter(outputFile));
//    }
     
     
        public static void addLang(String[][] tempCsv){
            
            for(int i=0; i<tempCsv.length; i++){
                String[] temp = tempCsv[i][3].split(",");
                
                for(int j=0; j<temp.length; j++){
                    if(!LangList.contains(temp[j].trim())){
                        LangList.add(temp[j].trim());
                    }
                }
                
            }
//            for(int k = 0; k<LangList.size(); k++){
//              System.out.print(LangList.get(k) + " ");
//            }
//            System.out.println();
        }
        
        public static void searchFile(String filename) throws Exception{
            
            String[][] info = re.readFile(filename);
            addLang(info);
            
            for(int i=0; i<LangList.size(); i++){
               String lang = LangList.get(i);
               System.out.println(lang);
               
            try{
               if(!lang.isEmpty()){
               selectLang(info, lang, filename);
               }
            }catch(Exception e){
                
            }
         }
        }
       
        
//        public static void main(String[] args) throws Exception {
////            ReadExcel re = new ReadExcel();
////            String[][] csv = re.readFile(filename);
//           // re.writeTempFile(csv,filename);
////        for(int i=0; i < csv.length; i++){
////            for(int j=0; j<csv[0].length; j++){
////                System.out.print(csv[i][j] + " ");
////            }
////            System.out.println();
////        }
//            
//            //searchFile(filename);
//    }
    
    
}
