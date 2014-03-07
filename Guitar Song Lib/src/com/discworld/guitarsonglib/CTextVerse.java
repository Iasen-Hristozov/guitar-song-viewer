package com.discworld.guitarsonglib;

import java.util.ArrayList;

public class CTextVerse extends CVerse
{
   public String sChordsVerseID;
   public ArrayList<CTextLine> alTextLines;
   
   public CTextVerse()
   {
      sChordsVerseID = "";
      alTextLines = new ArrayList<CTextLine>();
   }
   
   public CTextVerse(String sChordsCoupletID,
                     ArrayList<CTextLine> alTextLines)
   {
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
         sTextVerse += (sTextVerse.isEmpty() ? "" : "\n") + oTextLine.toString();
      }
      return sTextVerse;
   }

   @Override
   public int size()
   {
      return alTextLines.size();
   }
}
