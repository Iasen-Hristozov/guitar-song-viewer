package com.discworld.guitarsongplugins.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CGuitarSongPlugin
{
   
   protected boolean isUTF8 = true;
   
   protected String DOMAIN = "falshivim-vmeste.ru",
                    PATTERN = "",
                    TITLE_NAME_BGN = "",
                    TITLE_NAME_END = "",
                    TITLE_BGN = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ",
                    TITLE_END = " (>",
                    AUTHOR_BGN = " (",
                    AUTHOR_END = "</div>",
                    TEXT_BGN = "<pre class=textsong>",
                    TEXT_END = "</pre>";
   
//   protected String DOMAIN,
//            sTitleNameBgn,
//            sTitleNameEnd,
//            sTitleBgn,
//            sTitleEnd,
//            sAuthorBgn,
//            sAuthorEnd,
//            sTextBgn,
//            sTextEnd;   

   protected String sSong,
                    sTitle,
                    sAuthor;   

//   abstract protected void onCreate();
   
//   public CGuitarSongPlugin()
//   {
//      DOMAIN = "falshivim-vmeste.ru";
//      sTitleNameBgn = "<h1>";
//      sTitleNameEnd = "</h1>";
////      sTitleBgn = "Àêêîðäû ïåñíè ",
////      sTitleBgn = "Аккорды песни ",
//      sTitleBgn = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ";
//      sTitleEnd = sAuthorBgn = " (";
//      sAuthorEnd = ")";
//      sTextBgn = "<pre class=textsong>";
//      sTextEnd = "</pre>";      
//   }
   
   public String getDomainName()
   {
      return DOMAIN;
   }
   
   public void getSongFromURL(String sURL)
   {
      final String USER_AGENT = "Mozilla/5.0";
      
      String sResponse;
      
      URL          oURL;
    
      BufferedReader   in; 
    
      HttpURLConnection oHTTPConn;

      try
      {
         oURL = new URL(sURL);
         oHTTPConn = (HttpURLConnection) oURL.openConnection();
    
         // optional default is GET
         oHTTPConn.setRequestMethod("GET");
     
         // add reuqest header
         oHTTPConn.setRequestProperty("User-Agent", USER_AGENT);
     
         if(oHTTPConn.getResponseCode() == 200)
         {
            in = new BufferedReader(isUTF8 ? new InputStreamReader(oHTTPConn.getInputStream(), "UTF-8") : new InputStreamReader(oHTTPConn.getInputStream()));
//            in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream()));
        
            String inputLine;
            StringBuffer sbResponse = new StringBuffer();
    
            while ((inputLine = in.readLine()) != null) 
               sbResponse.append(inputLine + "\n");
            in.close();
        
            sResponse = sbResponse.toString();
    
            parseSong(sResponse);
            
//            // Get song title and author
//            String sTtlNm;
//            if(!sTitleNameBgn.isEmpty() && !sTitleNameBgn.isEmpty())
//            {
//               int iTtlNmBgn = sResponse.indexOf(sTitleNameBgn);
//               int iTtlNmEnd = sResponse.indexOf(sTitleNameEnd, iTtlNmBgn);
//               sTtlNm = sResponse.substring(iTtlNmBgn + sTitleNameBgn.length(), iTtlNmEnd);
//            }
//            else
//               sTtlNm = sResponse;
//            
//            // Get and set song title
//            int iTtlBgn = sTtlNm.indexOf(sTitleBgn);
//            int iTtlEnd = sTtlNm.indexOf(sTitleEnd, iTtlBgn);
//            sTitle = sTtlNm.substring(iTtlBgn + sTitleBgn.length(), iTtlEnd);
//            
//            // Get and set song author
//            int iAthBgn = sTtlNm.indexOf(sAuthorBgn);
//            int iAthEnd = sTtlNm.indexOf(sAuthorEnd, iAthBgn);
//            sAuthor = sTtlNm.substring(iAthBgn + sAuthorBgn.length(), iAthEnd);
//            
//            // Get and set song text
//            int iTxtBgn =  sResponse.indexOf(sTextBgn);
//            int iTxtEnd =  sResponse.indexOf(sTextEnd, iTxtBgn);
//            sSong = sResponse.substring(iTxtBgn + sTextBgn.length(), iTxtEnd);
         }
       } 
       catch(MalformedURLException e)
       {
          e.printStackTrace();
       } 
       catch(ProtocolException e)
       {
          // TODO Auto-generated catch block
          e.printStackTrace();
       } 
       catch(IOException e)
       {
          // TODO Auto-generated catch block
          e.printStackTrace();
       }         
   }
   
   protected void parseSong(String sResponse)
   {
      // Get song title and author
      String sTtlNm;
      if(!TITLE_NAME_BGN.isEmpty() && !TITLE_NAME_BGN.isEmpty())
      {
         int iTtlNmBgn = sResponse.indexOf(TITLE_NAME_BGN);
         int iTtlNmEnd = sResponse.indexOf(TITLE_NAME_END, iTtlNmBgn);
         sTtlNm = sResponse.substring(iTtlNmBgn + TITLE_NAME_BGN.length(), iTtlNmEnd);
      }
      else
         sTtlNm = sResponse;
      
      // Get and set song title
      int iTtlBgn = sTtlNm.indexOf(TITLE_BGN);
      int iTtlEnd = sTtlNm.indexOf(TITLE_END, iTtlBgn);
      sTitle = sTtlNm.substring(iTtlBgn + TITLE_BGN.length(), iTtlEnd);
      
      // Get and set song author
      if(!AUTHOR_BGN.isEmpty() && !AUTHOR_END.isEmpty())
      {
         int iAthBgn = sTtlNm.indexOf(AUTHOR_BGN);
         int iAthEnd = sTtlNm.indexOf(AUTHOR_END, iAthBgn);
         sAuthor = sTtlNm.substring(iAthBgn + AUTHOR_BGN.length(), iAthEnd);
      }
      
      // Get and set song text
      int iTxtBgn =  sResponse.indexOf(TEXT_BGN);
      int iTxtEnd =  sResponse.indexOf(TEXT_END, iTxtBgn);
      sSong = sResponse.substring(iTxtBgn + TEXT_BGN.length(), iTxtEnd);      
   }
   
   public String getTitle()
   {
      return sTitle;
   }
   public String getAuthor()
   {
      return sAuthor;
   }
   public String getSong()
   {
      return sSong;
   }
}
