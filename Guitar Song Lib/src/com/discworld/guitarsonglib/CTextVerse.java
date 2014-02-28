package com.discworld.guitarsonglib;

import java.util.ArrayList;

public class CTextVerse extends CVerse
{
   public boolean bIsChorus;
   public String sChordsVerseID;
   public ArrayList<CTextLine> alTextLines;
   
   public CTextVerse()
   {
      bIsChorus = false;
      sChordsVerseID = "";
      alTextLines = new ArrayList<CTextLine>();
   }
   
   public CTextVerse(String sChordsCoupletID,
                     ArrayList<CTextLine> alTextLines,
                     boolean bIsChorus)
   {
      this.bIsChorus = bIsChorus;
      this.sChordsVerseID = sChordsCoupletID;
      this.alTextLines = alTextLines;
   }
   
   public void addTextLine(CTextLine oTextLine)
   {
      alTextLines.add(oTextLine);
   }
   
   @Override
   public String toString()
   {
      String sTextVerse = "";
      for(CTextLine oTextLine: alTextLines)
      {
         sTextVerse += (sTextVerse.equals("") ? "" : "\n") + oTextLine.toString();
      }
      return sTextVerse;
   }

   @Override
   public int size()
   {
      return alTextLines.size();
   }
}
