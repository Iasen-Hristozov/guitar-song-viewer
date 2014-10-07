package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class Muzland extends CGuitarSongPlugin
{
//   private final static String DOMAIN = "http://muzland.ru/";

   public Muzland()
   {
      DOMAIN = "http://muzland.ru/";
      ENCODING = ENU_ENCODE_NONE;
      TITLE_NAME_BGN = "<h1 class=\"songname\">";
      TITLE_NAME_END = "</h1>";
      TITLE_BGN = "<div itemprop=\"name\">";
      TITLE_END = "</div>";
      AUTHOR_BGN = "<div itemprop=\"byArtist\">";
      AUTHOR_END = "</div>";
      TEXT_BGN = "<pre itemprop=\"chordsBlock\">";
      TEXT_END = "</pre>";
   }
   

   @Override
   public void getSongFromURL(String sURL)
   {
      super.getSongFromURL(sURL);
      
//      final String USER_AGENT = "Mozilla/5.0";
//
//     String       sResponse;
//   
//     URL          oURL;
//   
//     BufferedReader   in; 
//   
//     HttpURLConnection oHTTPConn;
//
//     try
//     {
//        oURL = new URL(sURL);
//        oHTTPConn = (HttpURLConnection) oURL.openConnection();
//   
//        // optional default is GET
//        oHTTPConn.setRequestMethod("GET");
//    
//        // add reuqest header
//        oHTTPConn.setRequestProperty("User-Agent", USER_AGENT);
//    
//        if(oHTTPConn.getResponseCode() == 200)
//        {
//           in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream(), "UTF-8"));
//   //        in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream()));
//       
//           String inputLine;
//           StringBuffer sbResponse = new StringBuffer();
//   
//           while ((inputLine = in.readLine()) != null) 
//              sbResponse.append(inputLine + "\n");
//   //        int inputChar;
//   //        while ((inputChar = in.read()) != -1)
//   //           sbResponse.append((char)inputChar);
//   //        sResponse = sbResponse.toString();
//   //        txtSong.setText(sResponse);            
//           in.close();
//       
//           sResponse = sbResponse.toString();
//   
//           // Get song title and author
//           int iTtlNmBgn = sResponse.indexOf(sTitleNameBgn);
//           int iTtlNmEnd = sResponse.indexOf(sTitleNameEnd, iTtlNmBgn);
//           String sTtlNm = sResponse.substring(iTtlNmBgn, iTtlNmEnd);
//           
//           // Get and set song title
//           int iTtlBgn = sTtlNm.indexOf(sTitleBgn);
//           int iTtlEnd = sTtlNm.indexOf(sTitleEnd, iTtlBgn);
//           sTitle = sTtlNm.substring(iTtlBgn + sTitleBgn.length(), iTtlEnd);
//           
//           // Get and set song author
//           int iAthBgn = sTtlNm.indexOf(sAuthorBgn);
//           int iAthEnd = sTtlNm.indexOf(sAuthorEnd, iAthBgn);
//           sAuthor = sTtlNm.substring(iAthBgn + sAuthorBgn.length(), iAthEnd);
//           
//           // Get and set song text
//           int iTxtBgn =  sResponse.indexOf(sTextBgn);
//           int iTxtEnd =  sResponse.indexOf(sTextEnd, iTxtBgn);
//           sSong = sResponse.substring(iTxtBgn + sTextBgn.length(), iTxtEnd);
//           sSong = sSong.replaceAll("<B>", "");
//           sSong = sSong.replaceAll("</B>", "");
//           sSong = sSong.replaceAll("<U>", "");
//           sSong = sSong.replaceAll("</U>", "");
//           sSong = sSong.replaceAll("<SUB>", "");
//           sSong = sSong.replaceAll("</SUB>", "");
//        }
//     } 
//     catch(MalformedURLException e)
//     {
//        e.printStackTrace();
//     } 
//     catch(ProtocolException e)
//     {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//     } 
//     catch(IOException e)
//     {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//     }
      sSong = sSong.replaceAll("<B>", "");
      sSong = sSong.replaceAll("</B>", "");
      sSong = sSong.replaceAll("<U>", "");
      sSong = sSong.replaceAll("</U>", "");
      sSong = sSong.replaceAll("<SUB>", "");
      sSong = sSong.replaceAll("</SUB>", "");      
   }
}
