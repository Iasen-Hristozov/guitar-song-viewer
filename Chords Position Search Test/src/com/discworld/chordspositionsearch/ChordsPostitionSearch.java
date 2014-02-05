package com.discworld.chordspositionsearch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.davidashen.text.Hyphenator;
import net.davidashen.util.ErrorHandler;

import com.discworld.guitarsonglib.CChord;
import com.discworld.guitarsonglib.CSong;
import com.discworld.guitarsonglib.CChordsLine;

class CChordsTextPair
{
   public String sChordsLine;
   public String sTextLine;
   
   public void CChordsTextPair()
   {
      sChordsLine = "";
      sTextLine = "";
   }
};

class LyricHyphenator 
{
   private static final String sURL = "http://www.juiciobrennan.com/syllables/",
                               USER_AGENT = "Mozilla/5.0",
                               sTextBgn = "name=\"inputText\">",
                               sTextEnd = "</textarea>";
   private String sTextRes;
   
   private String sUrlParameters = "inputText=";
   
   private URL oURL;
   
   private HttpURLConnection oHTTPConn;
   
   LyricHyphenator() throws Exception
   {
      oURL = new URL(sURL);
      oHTTPConn = (HttpURLConnection) oURL.openConnection();
      
      //add reuqest header
      oHTTPConn.setRequestMethod("POST");
      oHTTPConn.setRequestProperty("User-Agent", USER_AGENT);
      oHTTPConn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
      
      // Send post request
      oHTTPConn.setDoOutput(true);
   }
   
   public String getResult(String sText) throws Exception
   {
      DataOutputStream wr = new DataOutputStream(oHTTPConn.getOutputStream());
      wr.writeBytes(sUrlParameters + sText);
      wr.flush();
      wr.close();
 
      if(oHTTPConn.getResponseCode() != 200)
         return sText;
      
      BufferedReader in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream()));
      
      String inputLine;
      StringBuffer response = new StringBuffer();
  
      while ((inputLine = in.readLine()) != null) 
         response.append(inputLine);

      in.close();                  

      
      sTextRes = response.toString().substring(response.toString().indexOf(sTextBgn) + sTextBgn.length(), response.toString().indexOf(sTextEnd));
         
      return sTextRes;
   }
};

public class ChordsPostitionSearch
{
   final static int iEnuLanguage = CSong.ENU_LNG_RU;

   final static Pattern ptrText = Pattern.compile("[^ A-Hmoldurs#1-9]"),
                        ptrChord = Pattern.compile("[A-H]([moldurs#1-9]{0,6})"),
                        ptrSylablesBG = Pattern.compile("[ÀÚÎÓÅÈÞßÜàúîóåèþÿü]"), 
                        ptrSylablesRU = Pattern.compile("[ÀÚÎÓÅÈÞßÜÝÛàúîóåèþÿüûý¸]"),
                        ptrEngWord = Pattern.compile("\\w+\\W+"),      
                        ptrEngWord1 = Pattern.compile("\\w+");
   final static int ENU_HPN_UKN = 0,
                    ENU_HPN_LCL = 1,
                    ENU_HPN_INT_LRC_HPN = 2;
   
   /**
    * @param args
    * @throws Exception 
    */
   public static void main(String[] args) throws Exception
   {
      CChordsLine oChordsLine;

      CChordsTextPair oChordsTextPair2 = new CChordsTextPair();
      
//      oChordsTextPair2.sChordsLine = "     Em       C";
//      oChordsTextPair2.sChordsLine = "Em       C";
      oChordsTextPair2.sChordsLine = "     EmA      C        EmA";      
      oChordsTextPair2.sTextLine = "Àíòîíèíà îáåðíóëàñü,";

//      oChordsTextPair2.sChordsLine = "G   C           D          G";      
//      oChordsTextPair2.sTextLine = "How many roads must a man walk down";
//      oChordsTextPair2.sChordsLine = "G      C             D  G";      
//      oChordsTextPair2.sTextLine = "Yes, `n` how many times must the cannon balls fly";
//      oChordsTextPair2.sChordsLine = "C  G";      
//      oChordsTextPair2.sChordsLine = "C  G                               EmA";      
//      oChordsTextPair2.sTextLine = "Before they`re forever banned?";
//      oChordsTextPair2.sChordsLine = "    C   D       G           Em";      
//      oChordsTextPair2.sTextLine = "The answer, my friend, is blowin` in the wind,  ";
      
      if(iEnuLanguage == CSong.ENU_LNG_EN)
         oChordsLine = getChordsLineEn(oChordsTextPair2, ENU_HPN_INT_LRC_HPN);
      else
         oChordsLine = getChordsLineCyr(oChordsTextPair2);
      
//      switch(iEnuLanguage)
//      {
//         case CSong.ENU_LNG_BG:
//            mtcText = ptrSylablesBG.matcher(oChordsTextPair2.sTextLine);      
//         break;
//         
//         case CSong.ENU_LNG_RU:
//            mtcText = ptrSylablesRU.matcher(oChordsTextPair2.sTextLine);      
//         break;
//      }
//      
//      int iSlbNdx = 0;
////      mtcChords = ptrChordBgn.matcher(oChordsTextPair2.sChordsLine);
//      mtcChords = ptrChord.matcher(oChordsTextPair2.sChordsLine);
//      int iCrdBgn = 0;
//      int iCrdEnd = 0;
//      iSlbNdx = 0;
//      while(mtcChords.find(iCrdEnd))
//      {
//         iCrdBgn = mtcChords.start();
////         mtcChords = ptrChordEnd.matcher(oChordsTextPair2.sChordsLine);
////         boolean b =mtcChords.find(iCrdBgn+1);
////         iCrdEnd = mtcChords.start();
//         iCrdEnd = mtcChords.end();
//         
//         
//         while(mtcText.find())
//         {
//            if(mtcText.start() >= iCrdBgn)
//            {
//               oChord = new CChord(oChordsTextPair2.sChordsLine.substring(iCrdBgn, iCrdEnd));
//               oChord.iPosition = iSlbNdx++;
////               mtcChords = ptrChordBgn.matcher(oChordsTextPair2.sChordsLine);
//               break;
//            }
//            iSlbNdx ++;
//         }
//      }      
      
//      System.out.println(name+"!");    

   }
   
   static CChordsLine getChordsLineEn(CChordsTextPair oChordsTextPair, final int iEnuHyphenator) throws Exception 
   {
      int      iSlbNdx = 0,
               iCrdBgn = 0,
               iCrdEnd = 0;
      
      String   tsSylables[];
      
      ArrayList<String> alSyllables = new ArrayList<>();
      
      Matcher   mtcText,
                mtcChords;
      
      CChord    oChord;
      
      CChordsLine oChordsLine = new CChordsLine();
      
      
//      LyricHyphenator oLyricHyphenator = new LyricHyphenator();
//      
//      String sRes = oLyricHyphenator.getResult("hyphenation");
//      
//      sRes = oLyricHyphenator.getResult("republic");

//      String sRes = sLyricHyphenatorRequest("hyphenation");
//      
//      sRes = sLyricHyphenatorRequest("republic");
      
      Hyphenator oHyphenator = initHyphenator();
      
      mtcText = ptrEngWord1.matcher(oChordsTextPair.sTextLine);
      
      String hyphenated_word,
             word;
//      String tsSylables[];
      
      iCrdEnd = 0;
      
      while(mtcText.find(iCrdEnd))
      {
         iCrdBgn = mtcText.start();
         if(iCrdBgn != iCrdEnd)
         {
            alSyllables.add(oChordsTextPair.sTextLine.substring(iCrdEnd, iCrdBgn));
         }
         iCrdEnd = mtcText.end();
         
         word = oChordsTextPair.sTextLine.substring(iCrdBgn, iCrdEnd);
         
         switch(iEnuHyphenator)
         {
            case ENU_HPN_UKN:
            case ENU_HPN_LCL:
            default:
               hyphenated_word = oHyphenator.hyphenate(word);
               tsSylables = hyphenated_word.split("­");
            break;
            
            case ENU_HPN_INT_LRC_HPN:
               hyphenated_word = sLyricHyphenatorRequest(word);
               tsSylables = hyphenated_word.split("-");
            break;
         }
         for(int i = 0; i < tsSylables.length; i++)
         {
            alSyllables.add(tsSylables[i]);
         }
         System.out.println(hyphenated_word); 
      }
      
      int i = 0,
          iPos = 0,
          iLstPos = 0;
      
      String sSyllable;
      iCrdEnd = 0;
      
      mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);
      while(mtcChords.find(iCrdEnd))
      {
         iCrdBgn = mtcChords.start();
         iCrdEnd = mtcChords.end();
         
         oChord = new CChord(oChordsTextPair.sChordsLine.substring(iCrdBgn, iCrdEnd));
         
         if(iCrdBgn < oChordsTextPair.sTextLine.length())
         {
            for(i = iLstPos; i < alSyllables.size(); i++)
            {
               sSyllable = alSyllables.get(i);
               if(sSyllable.matches("[\\W]"))
                  continue;
               else
               {
                  if(iPos >= iCrdBgn)
                  {
                     oChord.iPosition = iSlbNdx++;
                     oChordsLine.addChord(oChord);
                     iLstPos = i + 1;
                     break;
                  }
                  iSlbNdx++;
               }
               iPos += sSyllable.length();
            }
         }
         else
         {
            if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >=0)
               oChord.iPosition = -1;
            else 
               oChord.iPosition = oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition - 1;
            oChordsLine.addChord(oChord);
         }
      }      
      
      hyphenated_word = oHyphenator.hyphenate("they`re");
      System.out.println(hyphenated_word); 
      
      
      return null;
   }
   
   static Hyphenator initHyphenator()
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
   
   static Hyphenator initHyphenator(String sHyphen)
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
         oHyphenator.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/" + sHyphen)));
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
   
   static String sLyricHyphenatorRequest(String sText)
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


   static CChordsLine getChordsLineCyr(CChordsTextPair oChordsTextPair)
   {
      int iSlbNdx = 0,
          iCrdBgn = 0,
          iCrdEnd = 0;
      
      Matcher   mtcText,
                mtcChords;
      
      CChord oChord;
      
      CChordsLine oChordsLine = new CChordsLine();
      
      switch(iEnuLanguage)
      {
         case CSong.ENU_LNG_BG:
            mtcText = ptrSylablesBG.matcher(oChordsTextPair.sTextLine);      
         break;
         
         default:
         case CSong.ENU_LNG_RU:
            mtcText = ptrSylablesRU.matcher(oChordsTextPair.sTextLine);      
         break;
      }
      
      mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);
      while(mtcChords.find(iCrdEnd))
      {
         iCrdBgn = mtcChords.start();
         iCrdEnd = mtcChords.end();
         
         oChord = new CChord(oChordsTextPair.sChordsLine.substring(iCrdBgn, iCrdEnd));
         
         if(iCrdBgn < oChordsTextPair.sTextLine.length())
         
         {
            while(mtcText.find())
            {            
               if(mtcText.start() >= iCrdBgn)
               {
                  oChord.iPosition = iSlbNdx++;
                  oChordsLine.addChord(oChord);
                  break;
               }
               iSlbNdx++;
            }
         }
         else
         {
            if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >=0)
               oChord.iPosition = -1;
            else 
               oChord.iPosition = oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition - 1;
            oChordsLine.addChord(oChord);
         }
      }
      
      return oChordsLine;
   }
   

}
