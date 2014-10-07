package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class music_place extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public music_place()
   {
      DOMAIN = "music-palace.ru";
//      isUTF8 = false;
      ENCODING = ENU_ENCODE_NONE;
      TITLE_NAME_BGN = "author=";
      TITLE_NAME_END = "strong>";
      TITLE_BGN = " - ";
      TITLE_END = "</";
      AUTHOR_BGN = "\">";
      AUTHOR_END = "</a>";
      TEXT_BGN = "<pre style=\"font-size:12px\">";
      TEXT_END = "</pre>";      
   }
   
   @Override
   protected void parseSong(String sResponse)
   {
      super.parseSong(sResponse);
      
      sSong = sSong.replaceAll("<font color=\"#[0-9A-F]+\">", "");
      sSong = sSong.replaceAll("</font>", "");
   }
}
