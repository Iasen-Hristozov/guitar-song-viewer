package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class Accords extends CGuitarSongPlugin
{
   private int iSongID;
   
   public Accords()
   {
      DOMAIN = "accords.cu.cc";
      isUTF8 = false;
      sTitleNameBgn = "";
      sTitleNameEnd = "";
//      sTitleBgn = "<span itemprop=\"name\">";
      sTitleEnd = "</U></B><BR>";
      sAuthorBgn = "<td align=\"right\" valign=\"top\" rowspan=\"2\"><H2>";
      sAuthorEnd = "</H2>";
      sTextBgn = "<PRE>";
      sTextEnd = "</PRE>";      
   }
   
   @Override
   protected void parseSong(String sResponse)
   {
      sTitleBgn = (iSongID < 10 ? "<B id=v0" : "<B id=v") + String.valueOf(iSongID) + "><U>";
      
      int iAthBgn = sResponse.indexOf(sAuthorBgn);
      int iAthEnd = sResponse.indexOf(sAuthorEnd, iAthBgn);
      sAuthor = sResponse.substring(iAthBgn + sAuthorBgn.length(), iAthEnd);
   
      sAuthorBgn = sAuthorEnd = "";
      
      int iSongBgn = sResponse.indexOf(sTitleBgn);
      int iSongEnd = sResponse.indexOf(sTextEnd, iSongBgn) + sTextEnd.length();
      
      sResponse = sResponse.substring(iSongBgn, iSongEnd);
      
      super.parseSong(sResponse);
   }
   
   @Override
   public void getSongFromURL(String sURL)
   {
      iSongID = Integer.valueOf(sURL.substring(sURL.length() - 2));
      
      super.getSongFromURL(sURL);
      
      sSong = sSong.replaceFirst("\\(.*\\)\n", "");
   }

   

}
