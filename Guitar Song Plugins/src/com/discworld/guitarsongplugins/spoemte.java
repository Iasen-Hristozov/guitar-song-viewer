package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class spoemte extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public spoemte()
   {
      DOMAIN = "spoemte.ru";
//      isUTF8 = false;
      ENCODING = ENU_ENCODE_NONE;
      TITLE_NAME_BGN = "<h2";
      TITLE_NAME_END = "/h2>";
      TITLE_BGN = " — ";
//      sTitleBgn = " \u0097 ";
      TITLE_END = "<";
      AUTHOR_BGN = ">";
      AUTHOR_END = " — ";
      TEXT_BGN = "<span id=song_text><pre>";
      TEXT_END = "</pre>";      
   }
   
//   @Override
//   protected void parseSong(String sResponse)
//   {
//      super.parseSong(sResponse);
//      
//      sSong = sSong.replaceAll("<font color=\"#[0-9A-F]+\">", "");
//      sSong = sSong.replaceAll("</font>", "");
//   }
}
