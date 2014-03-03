package com.discworld.guitarsongviewer.dto;

import com.discworld.guitarsonglib.CChordsVerse;

public interface IDataExchange
{
   public void setLinesNbr(int iLinesNbr);
   public int getLinesNbr();
   public CVerseSet getPage(int iNdx);
   public int getTextSize();
   public int getEnuDisplayChords();
   public CChordsVerse getChordsVerse(String sID);
}
