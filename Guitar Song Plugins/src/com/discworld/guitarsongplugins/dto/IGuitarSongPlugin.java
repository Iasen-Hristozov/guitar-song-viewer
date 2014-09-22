package com.discworld.guitarsongplugins.dto;

public interface IGuitarSongPlugin
{
   public String getDomainName();
   public void getSongFromURL(String sURL);
   public String getTitle();
   public String getAuthor();
   public String getSong();
}
