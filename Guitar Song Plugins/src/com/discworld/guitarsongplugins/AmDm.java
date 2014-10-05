package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

//public class FalshivimVmeste implements IGuitarSongPlugin
public class AmDm extends CGuitarSongPlugin
{
   public AmDm()
   {
      DOMAIN = "amdm.ru";
      TITLE_NAME_BGN = "";
      TITLE_NAME_END = "";
      TITLE_BGN = "<span itemprop=\"name\">";
      TITLE_END = "</span>";
      AUTHOR_BGN = "<span itemprop=\"byArtist\">";
      AUTHOR_END = "</span>";
      TEXT_BGN = "<pre itemprop=\"chordsBlock\">";
      TEXT_END = "</pre>";      
   }
   
   @Override
   public void getSongFromURL(String sURL)
   {
      super.getSongFromURL(sURL);
      
      sSong = sSong.replaceAll("</?b>", "");
   }
}
