package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class Pesni extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public Pesni()
   {
      DOMAIN = "pesni.ru";
      isUTF8 = false;
      sTitleNameBgn = "";
      sTitleNameEnd = "";
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
