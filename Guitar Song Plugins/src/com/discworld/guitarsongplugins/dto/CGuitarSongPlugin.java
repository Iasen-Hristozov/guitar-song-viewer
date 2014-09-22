package com.discworld.guitarsongplugins.dto;

public abstract class CGuitarSongPlugin
{
   protected String sSong,
                    sTitle,
                    sAuthor;   
   
   abstract public String getDomainName();
   abstract public void getSongFromURL(String sURL);
   public String getTitle()
   {
      return sTitle;
   }
   public String getAuthor()
   {
      return sAuthor;
   }
   public String getSong()
   {
      return sSong;
   }
}
