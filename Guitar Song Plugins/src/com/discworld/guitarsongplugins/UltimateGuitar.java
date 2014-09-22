package com.discworld.guitarsongplugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;
//import com.discworld.guitarsongplugins.dto.IGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class UltimateGuitar extends CGuitarSongPlugin
{
   private final static String DOMAIN = "ultimate-guitar.com";

   @Override
   public String getDomainName()
   {
      return DOMAIN;
   }

   @Override
   public void getSongFromURL(String sURL)
   {
      final String USER_AGENT = "Mozilla/5.0",
               sTitleNameBgn = "<div class=\"t_title  \">",
               sTitleNameEnd = "</div>",
               sTitleBgn = "<h1>",
               sTitleEnd = "</h1>",
               sAuthorBgn = ".htm\">",
               sAuthorEnd = "</a>",
               sTextBgn = "<pre><i></i>",
               sTextEnd = "</pre>";

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
   //        in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream()));
       
           String inputLine;
           StringBuffer sbResponse = new StringBuffer();
   
           while ((inputLine = in.readLine()) != null) 
              sbResponse.append(inputLine + "\n");
   //        int inputChar;
   //        while ((inputChar = in.read()) != -1)
   //           sbResponse.append((char)inputChar);
   //        sResponse = sbResponse.toString();
   //        txtSong.setText(sResponse);            
           in.close();
       
           sResponse = sbResponse.toString();
   
           // Get song title and author
           int iTtlNmBgn = sResponse.indexOf(sTitleNameBgn);
           int iTtlNmEnd = sResponse.indexOf(sTitleNameEnd, iTtlNmBgn);
           String sTtlNm = sResponse.substring(iTtlNmBgn, iTtlNmEnd);
           
           // Get and set song title
           int iTtlBgn = sTtlNm.indexOf(sTitleBgn);
           int iTtlEnd = sTtlNm.indexOf(sTitleEnd);
           sTitle = sTtlNm.substring(iTtlBgn + sTitleBgn.length(), iTtlEnd);
           
           // Get and set song author
           int iAthBgn = sTtlNm.indexOf(sAuthorBgn);
           int iAthEnd = sTtlNm.indexOf(sAuthorEnd);
           sAuthor = sTtlNm.substring(iAthBgn + sAuthorBgn.length(), iAthEnd);
           
           // Get and set song text
           int iTxtBgn =  sResponse.indexOf(sTextBgn);
           int iTxtEnd =  sResponse.indexOf(sTextEnd, iTxtBgn);
           sSong = sResponse.substring(iTxtBgn + sTextBgn.length(), iTxtEnd);
           sSong = sSong.replaceAll("<span>", "");
           sSong = sSong.replaceAll("</span>", "");
           sSong = sSong.replaceAll("&#039;", "'");
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
}
