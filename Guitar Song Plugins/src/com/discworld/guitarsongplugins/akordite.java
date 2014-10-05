package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class akordite extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public akordite()
   {
      DOMAIN = "akordite.com";
      isUTF8 = true;
      TITLE_NAME_BGN = "<div id=\"SongHeader\">";
      TITLE_NAME_END = "</div>";
      TITLE_BGN = "<h2 style=\"margin-bottom: 5px;border-bottom: 1px #cccccc solid;\">";
      TITLE_END = "</h2>";
      AUTHOR_BGN = "Изпълнител:";
      AUTHOR_END = "<br />";
      TEXT_BGN = "<div class=\"Song\"><pre>";
      TEXT_END = "</pre></div>";      
   }
   
   @Override
   protected void parseSong(String sResponse)
   {
      super.parseSong(sResponse);
      
      sTitle = sTitle.trim();
      sAuthor = sAuthor.trim();
      sSong = sSong.replaceAll("<span class=\"(SongWords|Chord)\">", "");
//      sSong = sSong.replaceAll("<span class=\"Chord\">", "");
      sSong = sSong.replaceAll("</span>", "");
   }
}
