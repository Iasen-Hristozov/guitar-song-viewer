package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class AmDm extends CGuitarSongPlugin
{
   public AmDm()
   {
      DOMAIN = "amdm.ru";
      sTitleNameBgn = "";
      sTitleNameEnd = "";
      sTitleBgn = "<span itemprop=\"name\">";
      sTitleEnd = "</span>";
      sAuthorBgn = "<span itemprop=\"byArtist\">";
      sAuthorEnd = "</span>";
      sTextBgn = "<pre itemprop=\"chordsBlock\">";
      sTextEnd = "</pre>";      
   }
   
   @Override
   public void getSongFromURL(String sURL)
   {
      super.getSongFromURL(sURL);
      
      sSong = sSong.replaceAll("</?b>", "");
   }
}
