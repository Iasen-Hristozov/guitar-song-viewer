package com.discworld.dto;

import java.util.ArrayList;

public class CSong
{
   public String sTitle;
   public String sAuthor;
   public int iEnuLanguage;
   public ArrayList<CChordsVerse> alChords;
//   public ArrayList<CTextVerse> alText;
   public CTextVersesSet oText;
   
   public CSong()
   {
      sTitle = "";
      sAuthor = "";
      iEnuLanguage = 0;
      alChords = new ArrayList<CChordsVerse>();
//      alText = new ArrayList<CTextVerse>();
      oText = new CTextVersesSet();
   }
}
