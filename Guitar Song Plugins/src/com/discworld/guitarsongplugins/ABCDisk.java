package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class ABCDisk extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public ABCDisk()
   {
      DOMAIN = "abcdisk.ru";
      isUTF8 = false;
      sTitleNameBgn = "<TD valign=\"top\"><B>";
      sTitleNameEnd = "</h1>";
      sTitleBgn = " - ";
      sTitleEnd = "</TD>";
      sAuthorBgn = "<h1>";
      sAuthorEnd = " - ";
      sTextBgn = "<pre style=\"font-size: 12pt;\">";
      sTextEnd = "</pre>";      
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
