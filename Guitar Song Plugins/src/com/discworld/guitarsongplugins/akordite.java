package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class akordite extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public akordite()
   {
      DOMAIN = "akordite.com";
//      isUTF8 = true;
      ENCODING = ENU_ENCODE_UTF8;
      TITLE_NAME_BGN = "<div id=\"SongHeader\">";
      TITLE_NAME_END = "</div>";
      TITLE_BGN = "<h2 style=\"margin-bottom: 5px;border-bottom: 1px #cccccc solid;\">";
      TITLE_END = "</h2>";

      AUTHOR_BGN = "\u0418\u0437\u043f\u044a\u043b\u043d\u0438\u0442\u0435\u043b:";
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
