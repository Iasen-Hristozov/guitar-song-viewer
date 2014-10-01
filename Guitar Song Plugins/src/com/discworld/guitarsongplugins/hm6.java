package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class hm6 extends CGuitarSongPlugin
{
   public hm6()
   {
      DOMAIN = "hm6.ru";
      
      isUTF8 = false;
      
//      sTitleNameBgn = "<h1 class=\"b-title\">";
      sTitleNameBgn = "<h1 class=";
//      sTitleNameEnd = "</h1>";
      sTitleNameEnd = "h1>";
      sTitleBgn = "\"b-title\">";;
      sTitleEnd = sAuthorBgn = " - ";
      sAuthorEnd = "</";
      sTextBgn = "<pre class=\"w-words__text\" itemprop=\"chordsBlock\">";
      sTextEnd = "</pre>";      
   }
   
   @Override
   public void getSongFromURL(String sURL)
   {
      super.getSongFromURL(sURL);
      
      sTitle = sTitle.trim();
      sAuthor = sAuthor.trim();
      
      String sTmp = sTitle;
      sTitle = sAuthor;
      sAuthor = sTmp;
      
//      sSong = sSong.replaceAll("</a>", "");
//      sSong = sSong.replaceAll("</span>", "");
//      sSong = sSong.replaceAll("<span .*\" >", "");
      sSong = sSong.replaceAll("<[^<]*<?[^<]*>?[^<]*>", "");
      sSong = sSong.replaceAll("&ndash;", "-");
   }
}
