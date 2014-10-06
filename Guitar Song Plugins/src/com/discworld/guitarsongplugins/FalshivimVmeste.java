package com.discworld.guitarsongplugins;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

public class FalshivimVmeste extends CGuitarSongPlugin
{
   public FalshivimVmeste()
   {
      DOMAIN = "falshivim-vmeste.ru";
      PATTERN = "http://?www\\.?falshivim-vmeste\\.ru/songs/(\\d*)\\.html";
      TITLE_NAME_BGN = "<h1>";
      TITLE_NAME_END = "</h1>";
//      sTitleBgn = "Àêêîðäû ïåñíè ",
//      sTitleBgn = "Аккорды песни ",
      TITLE_BGN = "\u0410\u043a\u043a\u043e\u0440\u0434\u044b \u043f\u0435\u0441\u043d\u0438 ";
      TITLE_END = AUTHOR_BGN = " (";
      AUTHOR_END = ")";
      TEXT_BGN = "<pre class=textsong>";
      TEXT_END = "</pre>";      
   }
}
