/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wei0000
 */

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class LoginTrisoft {
    
    private static final String connection = "http://trisoftcms6/infoshareauthor/login.asp";
    //private static final String username = "l10n";
    //private static final String password = "l10n";

    
    public static void Login(String[][] csvFile) throws FailingHttpStatusCodeException, IOException{
        String[] guid = new String[csvFile.length];
        String[] LangList = new String[csvFile.length];
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        
//        for(int i=0; i < csvFile.length; i++){
//            for(int j=0; j<csvFile[0].length; j++){
//                if(j == 0){
//                  guid[i] = csvFile[i][j];
//                }
//                if(j == 2){
//                   LangList[i] = csvFile[i][j];  
//                }
//                System.out.print(csvFile[i][j] + " ");
//            }
//            System.out.println();
//        }
             WebClient webClient = new WebClient();
            //WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
               // configure WebClient based on your desired
	    webClient.getOptions().setPrintContentOnFailingStatusCode(false);
            webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
            
        try{

            
            HtmlPage page = (HtmlPage)webClient.getPage(connection);
            
            //System.out.println(page.asXml());
            
            HtmlForm form = page.getFormByName("frmLogin");
            
            //HtmlForm form = page.getForms().get(1);
            
//            form.getInputByName("username").setValueAttribute("l10n");
//            form.getInputByName("userpassword").setValueAttribute("l10n");
            
             HtmlTextInput username = form.getInputByName("username");
            
//            HtmlTextInput username = form.getInputByName("username");
            username.setValueAttribute("l10n");
            
            HtmlPasswordInput password = form.getInputByName("userpassword");
//            HtmlTextInput password = form.getInputByName("password"); 
            password.setValueAttribute("l10n");
            
            HtmlSubmitInput button = form.getInputByName("signinbutton");
            
            HtmlPage page2 = button.click();
            HtmlPage page3 = (HtmlPage) button.click().getEnclosingWindow().getTopWindow().getEnclosedPage();
            webClient.waitForBackgroundJavaScript(1000);
            //System.out.println("**************************");
            page3 = (HtmlPage) page3.getFrameByName("MenuBar").getEnclosedPage();
            page3 = (HtmlPage)webClient.getPage("http://trisoftcms6/InfoShareAuthor/InfoShareAuthor.asp");
            System.out.println(page3.asXml());
                
//            form = page3.getFormByName("frm");
//            
//            HtmlTextInput ComponentID = form.getInputByName("LocateId");
//          
//            ComponentID.setValueAttribute("GUID-0C66CCF9-EEF4-402F-B517-8650BE193878");
//            
//            button = form.getInputByName("LocateButton");
//            
//            page3 = button.click();
//            
//            System.out.println(page3.asXml());
            
             List<FrameWindow> window = page2.getFrames();
             try{
             HtmlPage MenuBar = (HtmlPage)window.get(0).getEnclosedPage();
            // search= (HtmlPage)search.getFrameByName("MenuBar").getEnclosedPage();
             //System.out.println(search.asXml());
             }
             catch(Exception e){
                 e.printStackTrace();
             }
            
            
            
//             List<FrameWindow> searchWindow = search.getFrames();
//             HtmlPage searchLeft = (HtmlPage)searchWindow.get(0).getEnclosedPage();
//             
//             searchLeft = (HtmlPage)searchLeft.getFrameByName("MenuBar").getEnclosedPage();
//             //searchLeft = (HtmlPage)searchLeft.getFrameByName("")
//             
//             System.out.println(searchLeft.asXml());
            
//             HtmlElement searchElement = (HtmlElement)searchLeft.getHtmlElementById("treeButton3");
//             HtmlPage page4 = (HtmlPage)searchElement.click();
//             System.out.println(page4.asXml());
//            InetAddress ipaddress = InetAddress.getByName("www.esri.com");
//            System.out.println("IP address: "+ipaddress.getHostAddress());
//           
//            URL Trisoft = new URL(connection);
//            HttpURLConnection TrisoftConnection = (HttpURLConnection)Trisoft.openConnection();
//            TrisoftConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");;
//            TrisoftConnection.setDoOutput(true);
//            TrisoftConnection.connect();
//           
//           // TrisoftConnection.setRequestProperty("User-Agent", USER-AGENT);
//            int responseCode = TrisoftConnection.getResponseCode();
//            System.out.println("Response Code : " + responseCode);
//            
//            BufferedReader br = new BufferedReader(new InputStreamReader(TrisoftConnection.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line).append(System.getProperty("line.separator"));
//              }
//            br.close();
//            System.out.println(sb.toString());
         webClient.closeAllWindows();
                  
        }
         catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("An exception occurred. " + ex.getMessage());
            System.exit(1);
        }

        try{
            
        }
        catch(Exception e){
            
        }
        
        
       
    }
    
    
    public static void main(String args[]) throws Exception{
        String filename = "C:\\Users\\wei0000\\Desktop\\ArcGIS10.3_CMS_PubList_HO03_20140718.xlsx";
        ReadExcel excel = new ReadExcel();
        String[][] csv = ReadExcel.readFile(filename);
        Login(csv);
        
    }
}
