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
      TITLE_NAME_BGN = "";
      TITLE_NAME_END = "";
//      sTitleBgn = "<span itemprop=\"name\">";
      TITLE_END = "</U></B><BR>";
      AUTHOR_BGN = "<td align=\"right\" valign=\"top\" rowspan=\"2\"><H2>";
      AUTHOR_END = "</H2>";
      TEXT_BGN = "<PRE>";
      TEXT_END = "</PRE>";      
   }
   
   @Override
   protected void parseSong(String sResponse)
   {
      TITLE_BGN = (iSongID < 10 ? "<B id=v0" : "<B id=v") + String.valueOf(iSongID) + "><U>";
      
      int iAthBgn = sResponse.indexOf(AUTHOR_BGN);
      int iAthEnd = sResponse.indexOf(AUTHOR_END, iAthBgn);
      sAuthor = sResponse.substring(iAthBgn + AUTHOR_BGN.length(), iAthEnd);
   
      AUTHOR_BGN = AUTHOR_END = "";
      
      int iSongBgn = sResponse.indexOf(TITLE_BGN);
      int iSongEnd = sResponse.indexOf(TEXT_END, iSongBgn) + TEXT_END.length();
      
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
