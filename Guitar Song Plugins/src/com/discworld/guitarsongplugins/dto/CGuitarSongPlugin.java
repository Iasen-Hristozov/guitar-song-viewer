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
//   protected String DOMAIN = "falshivim-vmeste.ru",
//                    sTitleNameBgn = "<h1>",
//                    sTitleNameEnd = "</h1>",
//                    sTitleBgn = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ",
//                    sTitleEnd = " (>",
//                    sAuthorBgn = " (",
//                    sAuthorEnd = "</div>",
//                    sTextBgn = "<pre class=textsong>",
//                    sTextEnd = "</pre>";
   
   protected String DOMAIN,
            sTitleNameBgn,
            sTitleNameEnd,
            sTitleBgn,
            sTitleEnd,
            sAuthorBgn,
            sAuthorEnd,
            sTextBgn,
            sTextEnd;   

   protected String sSong,
                    sTitle,
                    sAuthor;   
   
//   protected void onCreate()
//   {
//      // TODO Auto-generated method stub
//      
//   }
   
//   abstract protected void onCreate();
   
   public CGuitarSongPlugin()
   {
      DOMAIN = "falshivim-vmeste.ru";
      sTitleNameBgn = "<h1>";
      sTitleNameEnd = "</h1>";
//      sTitleBgn = "Àêêîðäû ïåñíè ",
//      sTitleBgn = "Аккорды песни ",
      sTitleBgn = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ";
      sTitleEnd = sAuthorBgn = " (";
      sAuthorEnd = ")";
      sTextBgn = "<pre class=textsong>";
      sTextEnd = "</pre>";      
   }
   
   public String getDomainName()
   {
      return DOMAIN;
   }
   
   public void getSongFromURL(String sURL)
   {
      final String USER_AGENT = "Mozilla/5.0";
      
      String       sResponse;
      
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
            in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream(), "UTF-8"));
        
            String inputLine;
            StringBuffer sbResponse = new StringBuffer();
    
            while ((inputLine = in.readLine()) != null) 
               sbResponse.append(inputLine + "\n");
            in.close();
        
            sResponse = sbResponse.toString();
    
            // Get song title and author
            String sTtlNm;
            if(!sTitleNameBgn.isEmpty() && !sTitleNameBgn.isEmpty())
            {
               int iTtlNmBgn = sResponse.indexOf(sTitleNameBgn);
               int iTtlNmEnd = sResponse.indexOf(sTitleNameEnd, iTtlNmBgn);
               sTtlNm = sResponse.substring(iTtlNmBgn + sTitleNameBgn.length(), iTtlNmEnd);
            }
            else
               sTtlNm = sResponse;
            
            // Get and set song title
            int iTtlBgn = sTtlNm.indexOf(sTitleBgn);
            int iTtlEnd = sTtlNm.indexOf(sTitleEnd, iTtlBgn);
            sTitle = sTtlNm.substring(iTtlBgn + sTitleBgn.length(), iTtlEnd);
            
            // Get and set song author
            int iAthBgn = sTtlNm.indexOf(sAuthorBgn);
            int iAthEnd = sTtlNm.indexOf(sAuthorEnd, iAthBgn);
            sAuthor = sTtlNm.substring(iAthBgn + sAuthorBgn.length(), iAthEnd);
            
            // Get and set song text
            int iTxtBgn =  sResponse.indexOf(sTextBgn);
            int iTxtEnd =  sResponse.indexOf(sTextEnd, iTxtBgn);
            sSong = sResponse.substring(iTxtBgn + sTextBgn.length(), iTxtEnd);
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
