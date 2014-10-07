package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class hm6 extends CGuitarSongPlugin
{
   public hm6()
   {
      DOMAIN = "hm6.ru";
      
//      isUTF8 = false;
      ENCODING = ENU_ENCODE_NONE;
//      sTitleNameBgn = "<h1 class=\"b-title\">";
      TITLE_NAME_BGN = "<h1 class=";
//      sTitleNameEnd = "</h1>";
      TITLE_NAME_END = "h1>";
      TITLE_BGN = "\"b-title\">";;
      TITLE_END = AUTHOR_BGN = " - ";
      AUTHOR_END = "</";
      TEXT_BGN = "<pre class=\"w-words__text\" itemprop=\"chordsBlock\">";
      TEXT_END = "</pre>";      
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
