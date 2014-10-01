package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class dix extends CGuitarSongPlugin
{
//   private int iSongID;
   
   public dix()
   {
      DOMAIN = "dix.ru";
      isUTF8 = false;
      sTitleNameBgn = "";
      sTitleNameEnd = "";
      sTitleBgn = "<meta name=\"title\" content=\"";
      sTitleEnd = "\">";
      sAuthorBgn = "<title>";
      sAuthorEnd = "</title>";
      sTextBgn = "<pre>";
      sTextEnd = "</pre>";      
   }
   
   @Override
   protected void parseSong(String sResponse)
   {
      super.parseSong(sResponse);
      
      String[] sTitleElements = sAuthor.split(" • ");
      sAuthor = sTitleElements[sTitleElements.length - 1];
      sAuthor = toTitleCase(sAuthor);
   }
   
//   @Override
//   public void getSongFromURL(String sURL)
//   {
//      iSongID = Integer.valueOf(sURL.substring(sURL.length() - 2));
//      
//      super.getSongFromURL(sURL);
//      
//      sSong = sSong.replaceFirst("\\(.*\\)\n", "");
//   }
//
//   
   public static String toTitleCase(String input) 
   {
      StringBuilder titleCase = new StringBuilder();
      boolean nextTitleCase = true;

      for (char c : input.toCharArray()) 
      {
          if (Character.isSpaceChar(c)) 
          {
              nextTitleCase = true;
          } 
          else if (nextTitleCase) 
          {
              c = Character.toTitleCase(c);
              nextTitleCase = false;
          }

          titleCase.append(c);
      }

      return titleCase.toString();
  }
}
