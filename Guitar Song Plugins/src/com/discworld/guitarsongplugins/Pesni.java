package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class Pesni extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public Pesni()
   {
      DOMAIN = "pesni.ru";
//      isUTF8 = false;
      ENCODING = ENU_ENCODE_NONE;
      TITLE_NAME_BGN = "";
      TITLE_NAME_END = "";
      TITLE_BGN = "<h1 itemprop=\"name\">";
      TITLE_END = "</h1>";
      AUTHOR_BGN = "<span><a href=\"/abc/artist";
      AUTHOR_END = "</a>";
      TEXT_BGN = "<li itemprop=\"chordsBlock\" style=\"white-space: pre; font-family: courier\">";
      TEXT_END = "</li>";      
   }
   
   @Override
   protected void parseSong(String sResponse)
   {
      super.parseSong(sResponse);
      
      sAuthor = sAuthor.replaceAll("/\\d+/\">", "");
      sSong = sSong.replaceAll("<br />", "");
   }
}
