package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class ABCDisk extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public ABCDisk()
   {
      DOMAIN = "abcdisk.ru";
//      isUTF8 = true;
      ENCODING = ENU_ENCODE_WIN1251;
      TITLE_NAME_BGN = "<TD valign=\"top\"><B>";
      TITLE_NAME_END = "</h1>";
      TITLE_BGN = " - ";
      TITLE_END = "</TD>";
      AUTHOR_BGN = "<h1>";
      AUTHOR_END = " - ";
      TEXT_BGN = "<pre style=\"font-size: 12pt;\">";
      TEXT_END = "</pre>";      
   }
   
//   @Override
//   protected void parseSong(String sResponse)
//   {
//      super.parseSong(sResponse);
//      
//      String[] sTitleElements = sAuthor.split(" • ");
//      sAuthor = sTitleElements[sTitleElements.length - 1];
//      sAuthor = toTitleCase(sAuthor);
//   }
}
