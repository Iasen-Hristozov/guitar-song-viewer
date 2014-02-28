package com.discworld.guitarsongviewer.dto;

import java.util.ArrayList;

import com.discworld.guitarsonglib.CChordsTextPairVerse;
import com.discworld.guitarsonglib.CChordsVerse;
import com.discworld.guitarsonglib.CTextVerse;

public interface IDataExchange
{
   public void setLinesNbr(int iLinesNbr);
   public int getLinesNbr();
   public ArrayList<CTextVerse> getPage(int iNdx);
   public ArrayList<CChordsTextPairVerse> getPage1(int iNdx);
   public CVerseSet getPage2(int iNdx);
   public int getTextSize();
   public int getEnuDisplayChords();
   public CChordsVerse getChordsVerse(String sID);
}
