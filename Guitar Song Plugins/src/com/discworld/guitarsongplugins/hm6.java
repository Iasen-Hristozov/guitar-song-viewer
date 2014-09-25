package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class hm6 extends CGuitarSongPlugin
{
   public hm6()
   {
      DOMAIN = "hm6.ru";
      sTitleNameBgn = "<span itemprop=\"title\">";
      sTitleNameEnd = "</span>";
      sTitleBgn = " -  ";
      sTitleEnd = sAuthorBgn = " (";
      sAuthorEnd = ")";
      sTextBgn = "<pre class=\"w-words__text\" itemprop=\"chordsBlock\">";
      sTextEnd = "</pre>";      
   }
   
   @Override
   public void getSongFromURL(String sURL)
   {
      super.getSongFromURL(sURL);
      
      sSong.replaceAll("</a>", "");
      sSong.replaceAll("</span>", "");
      sSong.replaceAll("<span .*\" >", "");
   }
}
