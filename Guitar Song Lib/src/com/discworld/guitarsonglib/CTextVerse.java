package com.discworld.guitarsonglib;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "text-verse")
public class CTextVerse extends CVerse
{
   @Attribute(name = "id-chords-verse", required = true)
   public String sChordsVerseID = "";
   
   @ElementList(inline=true)
   public ArrayList<CTextLine> alTextLines = new ArrayList<CTextLine>();
   
   public CTextVerse()
   {
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
