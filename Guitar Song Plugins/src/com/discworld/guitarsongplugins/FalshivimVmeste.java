package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;
//import com.discworld.guitarsongplugins.dto.IGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class FalshivimVmeste extends CGuitarSongPlugin
{
//   private final static String DOMAIN = "falshivim-vmeste.ru";

//   @Override
   public FalshivimVmeste()
   {
      DOMAIN = "falshivim-vmeste.ru";
      TITLE_NAME_BGN = "<h1>";
      TITLE_NAME_END = "</h1>";
//      sTitleBgn = "Àêêîðäû ïåñíè ",
//      sTitleBgn = "Аккорды песни ",
      TITLE_BGN = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ";
      TITLE_END = AUTHOR_BGN = " (";
      AUTHOR_END = ")";
      TEXT_BGN = "<pre class=textsong>";
      TEXT_END = "</pre>";      
   }
   
   
//   @Override
//   public String getDomainName()
//   {
//      return DOMAIN;
//   }

//   @Override
//   public void getSongFromURL(String sURL)
//   {
//      final String USER_AGENT = "Mozilla/5.0",
//               sTitleNameBgn = "<h1>",
//               sTitleNameEnd = "</h1>",
////               sTitleBgn = "Àêêîðäû ïåñíè ",
////               sTitleBgn = "Аккорды песни ",
//               sTitleBgn = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ",
//               sAuthorBgn = " (",
//               sAuthorEnd = ")",
//               sTextBgn = "<pre class=textsong>",
//               sTextEnd = "</pre>";
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
//           int iTtlNmEnd = sResponse.indexOf(sTitleNameEnd);
//           String sTtlNm = sResponse.substring(iTtlNmBgn + sTitleNameBgn.length(), iTtlNmEnd);
//           
//           // Get and set song title
//           int iTtlBgn = sTtlNm.indexOf(sTitleBgn);
//           int iTtlEnd = sTtlNm.indexOf(sAuthorBgn);
//           sTitle = sTtlNm.substring(iTtlBgn + sTitleBgn.length(), iTtlEnd);
//           
//           // Get and set song author
//           int iAthEnd = sTtlNm.indexOf(sAuthorEnd);
//           sAuthor = sTtlNm.substring(iTtlEnd + sAuthorBgn.length(), iAthEnd);
//           
//           // Get and set song text
//           int iTxtBgn =  sResponse.indexOf(sTextBgn);
//           int iTxtEnd =  sResponse.indexOf(sTextEnd);
//           sSong = sResponse.substring(iTxtBgn + sTextBgn.length(), iTxtEnd);
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
//   }

   
}
