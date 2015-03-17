package com.discworld.guitarsongplugins;

//import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;
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
   
   @Override
   protected void parseSong(String sResponse)
   {
      super.parseSong(sResponse);
      
      int iNdx;
      int iSpcNbr;
      
      String [] tsLines = sSong.split("\\n");
      sSong = "";
      for(int i = 0; i < tsLines.length; i++)
      {
         iNdx = tsLines[i].indexOf('\t', 0);
         while(iNdx != -1)
         {
            iSpcNbr = 8 - (iNdx % 8);
            String s = new String(new char[iSpcNbr]).replace('\0', ' ');
            tsLines[i] = tsLines[i].substring(0, iNdx) + s + tsLines[i].substring(iNdx+1);
            iNdx = tsLines[i].indexOf('\t', iNdx);
         }
         sSong = sSong + tsLines[i] + '\n';
      }
      
//      sSong.indexOf(ch, fromIndex)
   }
}
