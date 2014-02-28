package com.discworld.englishhyphenator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import net.davidashen.text.Hyphenator;
import net.davidashen.util.ErrorHandler;

public class CEnglishHyphenator
{
   public final static int ENU_HPN_UKN = 0,
                           ENU_HPN_LCL = 1,
                           ENU_HPN_LCL_ITEXT = 2,
                           ENU_HPN_INT_LRC_HPN = 3;
   
   private int iEnuHyphenator;
   
   private Object oHyphenator;
   
   public CEnglishHyphenator(int iEnuHyphenator)
   {
      this.iEnuHyphenator = iEnuHyphenator;
      
      if(iEnuHyphenator == ENU_HPN_LCL)
         oHyphenator = initHyphenator();
      else if(iEnuHyphenator == ENU_HPN_LCL_ITEXT)
         oHyphenator = new com.itextpdf.text.pdf.hyphenation.Hyphenator("en", "US", 2, 1);
   }
   
   private static Hyphenator initHyphenator()
   {
      Hyphenator oHyphenator = new Hyphenator();
      
      oHyphenator.setErrorHandler(new ErrorHandler() 
      {
         public void debug(String guard,String s) 
         {}
         public void info(String s)
         {
            System.err.println(s);
         }
         public void warning(String s)
         {
            System.err.println("WARNING: "+s);
         }
         public void error(String s)
         {
            System.err.println("ERROR: "+s);
         }
         public void exception(String s, Exception e)
         {
            System.err.println("ERROR: "+s); e.printStackTrace(); 
         }
         public boolean isDebugged(String guard)
         {
            return false;
         }
      });
      
      try
      {
         
         oHyphenator.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyphen.tex")));
//         oHyphenator.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyph-en-us.tex")));
//         oHyphenator.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyph-en-gb.tex")));
//         oHyphenator.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyph-bg.tex")));
         
      } 
      catch(FileNotFoundException e)
      {
         e.printStackTrace();
      } 
      catch(IOException e)
      {
         e.printStackTrace();
      }
      
      return oHyphenator;
   }
   
   private static String sLyricHyphenatorRequest(String sText)
   {
      final String sURL = "http://www.juiciobrennan.com/syllables/",      
                   USER_AGENT = "Mozilla/5.0",
                   sTextBgn = "name=\"inputText\">",
                   sTextEnd = "</textarea>",
                   sUrlParameters = "inputText=";
      
      String       sResponse, 
                   sHyphenatedText = null;
      
      URL          oURL;
      
      DataOutputStream wr;
      
      BufferedReader   in; 
      
      HttpURLConnection oHTTPConn;
      
      try
      {
         oURL = new URL(sURL);
         oHTTPConn = (HttpURLConnection) oURL.openConnection();
         
         //add reuqest header
         oHTTPConn.setRequestMethod("POST");
         oHTTPConn.setRequestProperty("User-Agent", USER_AGENT);
         oHTTPConn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
         
         // Send post request
         oHTTPConn.setDoOutput(true);
         wr = new DataOutputStream(oHTTPConn.getOutputStream());
         wr.writeBytes(sUrlParameters + sText);
         wr.flush();
         wr.close();
    
         if(oHTTPConn.getResponseCode() == 200)
         {
            in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream()));
            
            String inputLine;
            StringBuffer sbResponse = new StringBuffer();
        
            while ((inputLine = in.readLine()) != null) 
               sbResponse.append(inputLine);
            in.close();
            
            sResponse = sbResponse.toString();
            
            sHyphenatedText = sResponse.substring(sResponse.indexOf(sTextBgn) + sTextBgn.length(), sResponse.indexOf(sTextEnd));
            if(sHyphenatedText.isEmpty())
               sHyphenatedText = sText;
         }
      } 
      catch(MalformedURLException e)
      {
         e.printStackTrace();
      } catch(ProtocolException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return sHyphenatedText;
   }
   
   public String hyphenate(String sWord)
   {
      String sHyphenatedWord;
      switch(iEnuHyphenator)
      {
         case ENU_HPN_UKN:
         case ENU_HPN_LCL:
         default:
            sHyphenatedWord = ((Hyphenator) oHyphenator).hyphenate(sWord);
            sHyphenatedWord = sHyphenatedWord.replaceAll("­", "-");
         break;
         
         case ENU_HPN_LCL_ITEXT:
            sHyphenatedWord = ((com.itextpdf.text.pdf.hyphenation.Hyphenator) oHyphenator).hyphenate(sWord).toString();
            
         break;
         
         case ENU_HPN_INT_LRC_HPN:
            sHyphenatedWord = sLyricHyphenatorRequest(sWord);
         break;
      }      
      
      return sHyphenatedWord;
   }

//   private static Hyphenator initHyphenator(String sHyphen)
//   {
//      Hyphenator oHyphenator = new Hyphenator();
//      
//      oHyphenator.setErrorHandler(new ErrorHandler() 
//      {
//         public void debug(String guard,String s) 
//         {}
//         public void info(String s)
//         {
//            System.err.println(s);
//         }
//         public void warning(String s)
//         {
//            System.err.println("WARNING: "+s);
//         }
//         public void error(String s)
//         {
//            System.err.println("ERROR: "+s);
//         }
//         public void exception(String s, Exception e)
//         {
//            System.err.println("ERROR: "+s); e.printStackTrace(); 
//         }
//         public boolean isDebugged(String guard)
//         {
//            return false;
//         }
//      });
//      
//      try
//      {
//         oHyphenator.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/" + sHyphen)));
//      } 
//      catch(FileNotFoundException e)
//      {
//         e.printStackTrace();
//      } 
//      catch(IOException e)
//      {
//         e.printStackTrace();
//      }
//      
//      return oHyphenator;
//   }   
}
